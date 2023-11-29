package org.example;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector implements Serializable, AutoCloseable {

    private static DatabaseConnector INSTANCE = null;

    private final String connectionString;
    private final String username;
    private final String password;

    private final String driver;

    private final Connection connection;

    private DatabaseConnector() {
        Configuration configuration = Configuration.getInstance();
        this.connectionString = (String) configuration.get("url");
        this.username = (String) configuration.get("username");
        this.password = (String) configuration.get("password");
        this.driver = (String) configuration.get("driver-class-name");

        this.connection = initializeConnection();
    }

    public static DatabaseConnector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConnector();
        }
        return INSTANCE;
    }

    private Connection initializeConnection() {
        try {
            Class.forName(this.driver);
            var connection = DriverManager.getConnection(this.connectionString, this.username, this.password);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            this.connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() {
        this.closeConnection();
    }
}
