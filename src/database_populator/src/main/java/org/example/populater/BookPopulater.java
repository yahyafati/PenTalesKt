package org.example.populater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Configuration;
import org.example.Main;
import org.example.model.Author;
import org.example.model.Book;
import org.example.repo.BookRepository;
import org.example.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.GZIPInputStream;

public class BookPopulater implements IPopulater {

    private static final Logger logger = Logger.getLogger(BookPopulater.class.getName());

    public BookPopulater() {
        setupLogger();
    }

    private void setupLogger() {
        try {
            String logDirectory = "logs/populate/" + Main.PROGRAM_TIMESTAMP;
            Files.createDirectories(Path.of(logDirectory));
            String fileName = this.getClass()
                    .getName() + ".log";
            FileHandler fileHandler = new FileHandler(logDirectory + fileName);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Book parseBook(String line) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Book book = new Book();
        Map<String, Object> map = gson.fromJson(line, type);

        for (Map.Entry<String, Object> entries : map.entrySet()) {
            String key = entries.getKey();
            Object value = entries.getValue();

            switch (key) {
                case "book_id":
                    book.setGoodreadsId(Long.parseLong((String) value));
                    break;
                case "title":
                    book.setTitle((String) value);
                    break;
                case "description":
                    book.setDescription((String) value);
                    break;
                case "isbn":
                    book.setIsbn((String) value);
                    break;
                case "isbn13":
                    book.setIsbn13((String) value);
                    break;
                case "language_code":
                    book.setLanguageCode((String) value);
                    break;
                case "num_pages":
                    book.setPageCount(StringUtils.safeConvertOrDefault((String) value, 0));
                    break;
                case "publication_year":
                    book.setPublicationYear(StringUtils.safeConvertOrDefault((String) value, 0));
                    break;
                case "image_url":
                    book.setCoverImage((String) value);
                    break;
                case "link":
                    book.setGoodreadsLink((String) value);
                    break;
                case "publisher":
                    book.setPublisher((String) value);
                    break;
                case "authors":
                    List<Map<String, String>> authors = (List<Map<String, String>>) value;
                    book.setAuthors(authors.stream()
                            .map(author -> {
                                String authorId = author.get("author_id");
                                return new Author(0L, "", Long.parseLong(authorId));
                            })
                            .toList());
                    break;
                case "popular_shelves":
                    List<Map<String, String>> popularShelves = (List<Map<String, String>>) value;
                    book.setGenres(popularShelves.stream()
                            .map(shelf -> shelf.get("name"))
                            .toList());
                    break;
                default:
                    break;
            }
        }


        return book;
    }

    @Override
    public void populate() {
        try (FileInputStream fis = new FileInputStream(String.valueOf(Configuration.getInstance()
                .get("goodreads-dataset-file-path")));

             GZIPInputStream gzis = new GZIPInputStream(fis);

             InputStreamReader reader = new InputStreamReader(gzis);

             BufferedReader br = new BufferedReader(reader);) {
            String line;
            Book[] books = new Book[2];
            int count = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                Book book = parseBook(line);
                books[count % books.length] = book;
                System.out.println(book);
                count++;
                if (count % books.length == 0) {
                    BookRepository.insertBookBatch(books);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
