package com.myshop.userws.dbaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Get database configuration from environment variables
    // with fallback to local development values
    private static final String DB_URL = System.getenv("MYSQL_URL") != null ? 
            System.getenv("MYSQL_URL");
    private static final String USER = System.getenv("MYSQL_USERNAME") != null ? 
            System.getenv("MYSQL_USERNAME");
    private static final String PASSWORD = System.getenv("MYSQL_PASSWORD") != null ? 
            System.getenv("MYSQL_PASSWORD");
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            System.out.println("Connecting to database: " + DB_URL);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connection established successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Database driver not found", e);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        if (connection == null) {
            System.err.println("Failed to establish database connection");
            throw new SQLException("Failed to establish database connection");
        }
        
        return connection;
    }
}
