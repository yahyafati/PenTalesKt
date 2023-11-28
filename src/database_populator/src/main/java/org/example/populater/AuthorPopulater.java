package org.example.populater;

import com.opencsv.CSVReader;
import org.example.Configuration;
import org.example.model.Author;
import org.example.repo.AuthorRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AuthorPopulater implements IPopulater {

    private final Logger logger = Logger.getLogger(AuthorPopulater.class.getName());

    public AuthorPopulater() {
        setupLogger();
    }

    private void setupLogger() {
        try {
            String logDirectory = "logs/populate/author";
            Files.createDirectories(Path.of(logDirectory));
            String fileName = "/application-" + System.currentTimeMillis() + ".log";
            FileHandler fileHandler = new FileHandler(logDirectory + fileName);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populate() {
        final int BATCH_SIZE = 1000;

        String filePath = (String) Configuration.getInstance()
                .get("author-dataset-file-path");
        Author author = null;
        String[] line = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));

             CSVReader reader = new CSVReader(br);) {
            int count = 0;
            while ((line = reader.readNext()) != null) {
                count++;
                if (count == 1) {
                    // Skip the first line
                    continue;
                }

                author = new Author(0L, line[1], Long.parseLong(line[0]));
                AuthorRepository.insertAuthor(author, count % BATCH_SIZE == 0);

                if (count % BATCH_SIZE == 0) {
                    logger.info("Inserted " + count + " records");
                }
            }
            logger.info("Populated Authors Table Successfully!");
        } catch (Exception e) {
            logger.severe(e.getMessage());
            logger.severe("Author: " + (author != null ? author.toString() : null));
            logger.severe("Line: " + (line != null ? Arrays.toString(line) : null));
        }
    }
}
