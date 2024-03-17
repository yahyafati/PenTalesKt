import gzip
import json
import os

import bs4
import requests

import config


def get_goodreads_url(id: str) -> str:
    return f"https://www.goodreads.com/book/show/{id}"


def main():
    size = int(input("Enter the size of the dataset: "))
    save_split_size = int(input("Enter the split size: "))

    goodreads_api_key = config.get_goodreads_dataset_path()
    save_path = "downloaded/"

    # Create a folder with the save_path name if it doesn't exist
    os.makedirs(save_path, exist_ok=True)

    headers = {
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }

    file_count = 1
    with gzip.open(goodreads_api_key, "rt", encoding="utf-8") as reader:
        path_to_save = os.path.join(save_path, f"goodreads_books_{file_count}.json.gz")
        writer = gzip.open(path_to_save, "wt", encoding="utf-8")

        count = 0
        for line in reader:
            json_line = json.loads(line)
            book_id = json_line["book_id"]
            url = get_goodreads_url(book_id)
            print(f"fetching: Book ID: {book_id}, URL: {url}")

            response = requests.get(url, headers=headers)
            soup = bs4.BeautifulSoup(response.text, 'html.parser')

            cover_image = soup.find("div", {"class": "BookCover__image"})
            if cover_image is None:
                print("No cover image found")
                continue
            img = cover_image.find("img")
            if img is None:
                print("No img tag found")
                continue
            img_url = img["src"]
            print(f"Image URL: {img_url}")
            json_line["hq_image_url"] = img_url
            writer.write(json.dumps(json_line) + "\n")

            if count % save_split_size == 0:
                writer.close()
                file_count += 1
                path_to_save = os.path.join(save_path, f"goodreads_books_{file_count}.json.gz")
                writer = gzip.open(path_to_save, "wt", encoding="utf-8")

            count += 1
            if count > size:
                break


if __name__ == "__main__":
    main()
