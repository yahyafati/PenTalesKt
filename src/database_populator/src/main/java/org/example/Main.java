package org.example;

import org.example.populater.AuthorPopulater;
import org.example.populater.BookPopulater;
import org.example.populater.GenrePopulater;
import org.example.repo.GenreRepository;

public class Main {

    public static final int PROGRAM_TIMESTAMP = (int) (System.currentTimeMillis() / 1000);

    public static void main(String[] args) {
        DatabaseConnector connector = DatabaseConnector.getInstance();

        AuthorPopulater authorPopulater = new AuthorPopulater();
        authorPopulater.populate();


        GenrePopulater genrePopulater = new GenrePopulater();
        genrePopulater.populate();
        GenreRepository.getAllGenres(true);

        BookPopulater bookPopulater = new BookPopulater();
        bookPopulater.populate();
        connector.close();

    }
}