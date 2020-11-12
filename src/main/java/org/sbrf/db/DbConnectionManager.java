package org.sbrf.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbConnectionManager {

    public static final String DATABASE_URL  = "jdbc:h2:file:C:\\Temp\\Projects\\EmployeeApplication\\src\\db\\employee";
    public static final String DATABASE_USER = "sysdba";
    public static final String DATABASE_PASS = "123";

    private Connection connection;

    public DbConnectionManager(String dbUrl, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");

        connection = DriverManager.getConnection(dbUrl, user, password);
        connection.setAutoCommit(true);
    }

    public Connection getConnection() {
        return this.connection;
    }
}
