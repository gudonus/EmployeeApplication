package org.sbrf.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {
    private Connection connection;

    public DbConnectionManager(String dbUrl, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(dbUrl);
    }

    public Connection getConnection() {
        return this.connection;
    }
}
