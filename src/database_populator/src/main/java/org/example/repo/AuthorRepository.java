package org.example.repo;

import org.example.DatabaseConnector;
import org.example.model.Author;

public class AuthorRepository {


    public static long getAuthorIdByGoodReadsId(long goodReadsId) {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        String query = "SELECT id FROM author WHERE good_reads_author_id = ?";
        try (var statement = connector.getConnection()
                .prepareStatement(query)) {
            statement.setLong(1, goodReadsId);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void insertAuthor(Author author, boolean commit) {
        DatabaseConnector connector = DatabaseConnector.getInstance();
        //        columns = name, good_reads_author_id, created_at, updated_at
        String query = "INSERT INTO author (name, good_reads_author_id, created_at, updated_at) VALUES (?, ?, ?, ?)";
        try (var statement = connector.getConnection()
                .prepareStatement(query)) {
            statement.setString(1, author.getName());
            statement.setLong(2, author.getGoodReadsAuthorId());
            statement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
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
