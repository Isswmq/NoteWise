package org.isswqm.notewise.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException {
        final String BD_URL = "jdbc:postgresql://localhost:5432/reminder";
        final String BD_USER = "postgres";
        final String BD_PASSWORD = "postgres";
        return DriverManager.getConnection(BD_URL, BD_USER, BD_PASSWORD);
    }

}
