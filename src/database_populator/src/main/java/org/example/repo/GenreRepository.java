package org.example.repo;

import org.example.DatabaseConnector;
import org.example.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreRepository {

    private static List<Genre> ALL_GENRES = null;

    public static List<Genre> getAllGenres(boolean forceReload) {
        if (ALL_GENRES != null && !forceReload) {
            return ALL_GENRES;
        }
        List<Genre> genres = new ArrayList<>();
        DatabaseConnector connector = DatabaseConnector.getInstance();
        String query = "SELECT id, name FROM genre";
        try (var statement = connector.getConnection()
                .prepareStatement(query)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                genres.add(Genre.fromResultSet(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ALL_GENRES = genres;
        return genres;
    }

    public static List<Genre> getAllGenres() {
        return getAllGenres(false);
    }

    public static void insertGenre(String genre, boolean commit) {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        //        columns = name, created_at, updated_at
        String query = "INSERT INTO genre (name, created_at, updated_at) VALUES (?, ?, ?)";
        try (var statement = connector.getConnection()
                .prepareStatement(query)) {
            statement.setString(1, genre);
            statement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.addBatch();
            statement.executeBatch();

            if (commit) {
                connector.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
