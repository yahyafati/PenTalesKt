package org.example;

import org.example.populater.AuthorPopulater;
import org.example.populater.BookPopulater;
import org.example.populater.GenrePopulater;
import org.example.repo.GenreRepository;

import java.util.Scanner;

public class Main {

    public static final int PROGRAM_TIMESTAMP = (int) (System.currentTimeMillis() / 1000);

    public static void main(String[] args) {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        Scanner scanner = new Scanner(System.in);
        int maxBooks = 50_000;
        System.out.print("How many books do you want to populate? (default 50,000): ");
        try {
            maxBooks = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default value of 50,000");
        }

        AuthorPopulater authorPopulater = new AuthorPopulater();
        authorPopulater.populate();


        GenrePopulater genrePopulater = new GenrePopulater();
        genrePopulater.populate();
        GenreRepository.getAllGenres(true);

        BookPopulater bookPopulater = new BookPopulater();
        bookPopulater.populate(maxBooks);
        connector.close();
        scanner.close();

    }
}