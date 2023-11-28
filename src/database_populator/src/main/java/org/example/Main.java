package org.example;

public class Main {
    public static void main(String[] args) {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        //        AuthorPopulater authorPopulater = new AuthorPopulater();
        //        authorPopulater.populate();

        connector.closeConnection();
    }
}