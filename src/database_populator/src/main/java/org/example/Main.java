package org.example;

public class Main {

    public static final int PROGRAM_TIMESTAMP = (int) (System.currentTimeMillis() / 1000);

    public static void main(String[] args) {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        //        AuthorPopulater authorPopulater = new AuthorPopulater();
        //        authorPopulater.populate();

        //        Long id = AuthorRepository.getAuthorIdByGoodReadsId(15340731);
        //        System.out.println(id);

        //        GenrePopulater genrePopulater = new GenrePopulater();
        //        genrePopulater.populate();

        //        GenreRepository.getAllGenres()
        //                .forEach(System.out::println);

        connector.closeConnection();
    }
}