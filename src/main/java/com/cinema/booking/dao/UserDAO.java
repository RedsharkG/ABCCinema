package com.cinema.booking.dao;

import java.sql.*;
import com.cinema.booking.model.User;
import com.cinema.booking.util.DBConnection;
import com.cinema.booking.util.PasswordHasher;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public boolean registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, phone_number, nic, gender, role) VALUES (?, ?, ?, ?, ?, ?, 'USER')";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, PasswordHasher.hash(user.getPassword()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getNic());
            stmt.setString(6, user.getGender());

            return stmt.executeUpdate() > 0;
        }
    }

    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && PasswordHasher.verify(password, rs.getString("password"))) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setNic(rs.getString("nic"));
                user.setGender(rs.getString("gender"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }
}
