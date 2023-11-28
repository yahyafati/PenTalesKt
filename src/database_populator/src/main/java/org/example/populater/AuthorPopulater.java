package org.example.populater;

import com.opencsv.CSVReader;
import org.example.Configuration;
import org.example.model.Author;
import org.example.repo.AuthorRepository;

import java.io.BufferedReader;
import java.io.FileReader;

public class AuthorPopulater implements IPopulator {

    @Override
    public void populate() {
        final int BATCH_SIZE = 1000;

        String filePath = (String) Configuration.getInstance()
                .get("author-dataset-file-path");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));

             CSVReader reader = new CSVReader(br);) {
            String[] line;
            int count = 0;
            while ((line = reader.readNext()) != null) {
                count++;
                if (count == 1) {
                    // Skip the first line
                    continue;
                }

                Author author = new Author(0L, line[1], Long.parseLong(line[0]));
                AuthorRepository.insertAuthor(author, count % BATCH_SIZE == 0);

                if (count % BATCH_SIZE == 0) {
                    System.out.println("Inserted " + count + " records");
                }

                if (count >= 10) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
