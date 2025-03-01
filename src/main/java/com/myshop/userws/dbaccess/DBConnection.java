package com.myshop.userws.dbaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Get database configuration from environment variables with fallback to provided values
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DRIVER = "org.postgresql.Driver";
    
    static {
        try {
            Class.forName(DRIVER);
            System.out.println("PostgreSQL JDBC Driver registered!");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            System.out.println("Connecting to database: " + DB_URL);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connection established successfully");
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
