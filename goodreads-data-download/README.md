# Goodreads Image Download

This is a simple script to download all the images for the books in the Goodreads dataset.

## Usage

1. Download the Goodreads dataset.
2. Run `pip install -r requirements.txt` to install the dependencies.
3. Copy `.env.default` to `.env` and fill in the values.

```dotenv
GOODREADS_DATASET_PATH=<path to the goodreads dataset>
```

> Note: The `GOODREADS_DATASET_PATH` should be the path to the `goodreads_books.json.gz` file.

> The `SAVE_PATH` should be the path to the directory where you want to save the images.

4. Run `python main.py` to download the images.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
