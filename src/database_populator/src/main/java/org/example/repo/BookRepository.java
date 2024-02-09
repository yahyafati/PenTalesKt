package org.example.repo;

import org.example.DatabaseConnector;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class BookRepository {


    private static PreparedStatement createInsertBookStatement() throws SQLException {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        String sql = "INSERT INTO book (" +

                "goodreads_id, title, description, isbn, isbn13, language_code, page_count, " +

                "cover_image, publication_year, publisher, goodreads_link, created_at, updated_at) " +

                "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return connector.getConnection()
                .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    private static PreparedStatement createInsertBookAuthorStatement() throws SQLException {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        // column names: book_id, author_id, sort_order
        String sql = "INSERT INTO book_author (" +

                "book_id, author_id, sort_order) " +

                "VALUES " + "(?, ?, ?)";

        return connector.getConnection()
                .prepareStatement(sql);
    }

    private static PreparedStatement createInsertBookGenreStatement() throws SQLException {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        // column names: book_id, genre_id
        String sql = "INSERT INTO book_genre (" +

                "book_id, genre_id, sort_order) " +

                "VALUES " + "(?, ?, ?)";

        return connector.getConnection()
                .prepareStatement(sql);
    }

    public static void insertBookBatch(Book[] books) throws SQLException {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        try (PreparedStatement statement = createInsertBookStatement()) {
            for (Book book : books) {
                addBookToBatch(book, statement);
            }
            statement.executeBatch();


            try (ResultSet generatedKeys = statement.getGeneratedKeys();

                 PreparedStatement bookAuthorStatement = createInsertBookAuthorStatement();

                 PreparedStatement bookGenreStatement = createInsertBookGenreStatement()) {
                int index = 0;
                int notFoundAuthors = 0;
                while (generatedKeys.next()) {
                    long bookId = generatedKeys.getLong(1);
                    int addedAuthors = 0;
                    HashSet<Long> authorIds = new HashSet<>();

                    for (Author author : books[index].getAuthors()) {
                        long newAuthorId = AuthorRepository.getAuthorIdByGoodReadsId(author.getGoodReadsAuthorId());
                        if (newAuthorId == 0 || authorIds.contains(newAuthorId)) {
                            notFoundAuthors++;
                            continue;
                        }
                        addBookAuthorToBatch(bookAuthorStatement, bookId, newAuthorId, index - addedAuthors);
                        authorIds.add(newAuthorId);
                        addedAuthors++;
                    }

                    for (Genre genre : books[index].getUniqueGenres()) {
                        long genreId = genre.getId();
                        if (genreId == 0) {
                            System.err.println("Genre not found: " + genre);
                            continue;
                        }
                        addBookGenreToBatch(bookGenreStatement, bookId, genreId, index - addedAuthors);
                        addedAuthors++;
                    }

                    index++;
                }

                if (notFoundAuthors > 0) {
                    System.err.println("[e]: " + notFoundAuthors + " authors not found.");
                }
                bookGenreStatement.executeBatch();
                bookAuthorStatement.executeBatch();
            }
        }
        connector.commit();
    }

    private static void addBookToBatch(Book book, PreparedStatement statement) throws SQLException {
        statement.setLong(1, book.getGoodreadsId());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getDescription());
        statement.setString(4, book.getIsbn());
        statement.setString(5, book.getIsbn13());
        statement.setString(6, book.getLanguageCode());
        statement.setInt(7, book.getPageCount());
        statement.setString(8, book.getCoverImage());
        statement.setInt(9, book.getPublicationYear());
        statement.setString(10, book.getPublisher());
        statement.setString(11, book.getGoodreadsLink());
        statement.setTimestamp(12, new java.sql.Timestamp(System.currentTimeMillis()));
        statement.setTimestamp(13, new java.sql.Timestamp(System.currentTimeMillis()));
        statement.addBatch();
    }

    //    public static void insertBook(Book book) throws SQLException {
    //        DatabaseConnector connector = DatabaseConnector.getInstance();
    //        try (PreparedStatement statement = createInsertBookStatement()) {
    //            addBookToBatch(book, statement);
    //            statement.executeBatch();
    //            ResultSet generatedKeys = statement.getGeneratedKeys();
    //            if (generatedKeys.next()) {
    //                long bookId = generatedKeys.getLong(1);
    //                try (PreparedStatement bookAuthorStatement = createInsertBookAuthorStatement()) {
    //                    for (Author author : book.getAuthors()) {
    //                        long newAuthorId = AuthorRepository.getAuthorIdByGoodReadsId(author.getGoodReadsAuthorId());
    //                        if (newAuthorId == 0) {
    //                            System.err.println("Author not found: " + author.getGoodReadsAuthorId());
    //                            continue;
    //                        }
    //                        addBookAuthorToBatch(bookAuthorStatement, bookId, newAuthorId, 0);
    //                    }
    //                    bookAuthorStatement.executeBatch();
    //                }
    //            }
    //            connector.commit();
    //        }
    //    }

    public static void addBookAuthorToBatch(PreparedStatement statement, long bookId, long authorId, int sortOrder) throws SQLException {
        statement.setLong(1, bookId);
        statement.setLong(2, authorId);
        statement.setInt(3, sortOrder);
        statement.addBatch();
        statement.executeBatch();
    }

    public static void addBookGenreToBatch(PreparedStatement statement, long bookId, long genreId, int sortOrder) throws SQLException {
        statement.setLong(1, bookId);
        statement.setLong(2, genreId);
        statement.setInt(3, sortOrder);
        statement.addBatch();
    }

}
