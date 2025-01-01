package com.cinema.booking.dao;

import java.sql.*;
import com.cinema.booking.model.Admin;
import com.cinema.booking.util.DBConnection;

public class AdminDAO {
    private Connection connection;

    public AdminDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public Admin authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username = ? AND status = 'active'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    Admin admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setUsername(rs.getString("username"));
                    admin.setEmail(rs.getString("email"));
                    admin.setFullName(rs.getString("full_name"));
                    admin.setStatus(rs.getString("status"));
                    updateLastLogin(admin.getAdminId());
                    return admin;
                }
            }
        }
        return null;
    }

    public boolean registerAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO admins (username, password, email, full_name, status) VALUES (?, ?, ?, ?, 'active')";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getFullName());
            return stmt.executeUpdate() > 0;
        }
    }

    private void updateLastLogin(int adminId) throws SQLException {
        String sql = "UPDATE admins SET last_login = CURRENT_TIMESTAMP WHERE admin_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            stmt.executeUpdate();
        }
    }

    public boolean checkUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM admins WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing database connection", e);
        }
    }
}
