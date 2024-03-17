package org.example.populater;

import org.example.Configuration;
import org.example.DatabaseConnector;
import org.example.Main;
import org.example.repo.GenreRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GenrePopulater implements IPopulater {

    private final Logger logger = Logger.getLogger(GenrePopulater.class.getName());

    public GenrePopulater() {
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

    @Override
    public void populate() {
        int BATCH_SIZE = 100;
        int count = 0;

        Map<String, List<String>> genres = Configuration.getInstance()
                .getGenres();
        for (String genre : genres.keySet()) {
            count++;
            GenreRepository.insertGenre(genre, count % BATCH_SIZE == 0);

            if (count % BATCH_SIZE == 0) {
                logger.info("Inserted " + count + " records");
            }
        }
        DatabaseConnector.getInstance()
                .commit();
        logger.info("Inserted " + count + " records");
    }
}
