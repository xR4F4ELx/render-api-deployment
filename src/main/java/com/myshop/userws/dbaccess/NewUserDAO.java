package com.myshop.userws.dbaccess;

import java.sql.*;
import java.util.ArrayList;

/**
 * This is a utility Bean for extracting user info from the DB and populate the
 * value Bean
 */
public class NewUserDAO {
    // Existing methods with improved error handling
    public static User getUserDetails(String userid) throws SQLException {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM user_details WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setUserid(rs.getString("user_id"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
            }
        } catch (SQLException e) {
            System.out.println("Error in getUserDetails: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return user;
    }

    private int getMaxUserId() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxId = 0;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT MAX(user_id) FROM user_details";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return maxId;
    }

    // Modify the insertUser method
    public int insertUser(int age, String gender) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int newUserId = getMaxUserId() + 1;  // Get next ID
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO user_details (user_id, age, gender) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newUserId);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return newUserId;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("Error in insertUser: " + e.getMessage());
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    // New update method
    public int updateUser(String userid, int age, String gender) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowsAffected = 0;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE user_details SET age = ?, gender = ? WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, age);
            pstmt.setString(2, gender);
            pstmt.setString(3, userid);
            rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("User with ID " + userid + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Error in updateUser: " + e.getMessage());
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return rowsAffected;
    }

    // New delete method
    public int deleteUser(String userid) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowsAffected = 0;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM user_details WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("User with ID " + userid + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Error in deleteUser: " + e.getMessage());
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return rowsAffected;
    }

    // Utility method to check if user exists
    public boolean userExists(String userid) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT 1 FROM user_details WHERE userid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error in userExists: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }
    
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT user_id, age, gender FROM user_details";  // Match exact column names
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setUserid(rs.getString("user_id"));  // Make sure column names match
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                users.add(user);
                System.out.println("Found user: " + user.getUserid());  // Debug output
            }
            System.out.println("Total users found: " + users.size());  // Debug output
        } catch (SQLException e) {
            System.out.println("SQL Error in getAllUsers: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return users;
    }
}