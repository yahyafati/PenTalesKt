package org.example.repo;

import org.example.DatabaseConnector;
import org.example.model.Genre;
import org.example.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static Genre getGenreByName(String name) {
        List<Genre> genres = getAllGenres();
        for (Genre genre : genres) {
            List<String> possibleNames = genre.getPossibleNames();
            for (String possibleName : possibleNames) {
                if (StringUtils.isCleanedEqual(name, possibleName)) {
                    return genre;
                }
            }
        }
        return null;
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

    public static long getGenreByIdByName(String genre) {
        return Objects.requireNonNullElse(getGenreByName(genre), new Genre(0L, genre))
                .getId();
    }
}
