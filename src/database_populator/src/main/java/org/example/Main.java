package org.example;

import org.example.populater.AuthorPopulater;

public class Main {
    public static void main(String[] args) {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        AuthorPopulater authorPopulator = new AuthorPopulater();
        authorPopulator.populate();

        connector.closeConnection();
    }
}