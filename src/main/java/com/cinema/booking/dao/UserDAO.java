package com.cinema.booking.dao;

import java.sql.*;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
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
<<<<<<< HEAD
        String sql = "INSERT INTO users (username, password, email, phone_number, nic, gender, role, status) VALUES (?, ?, ?, ?, ?, ?, 'USER', 'active')";
=======
        String sql = "INSERT INTO users (username, password, email, phone_number, nic, gender, role) VALUES (?, ?, ?, ?, ?, ?, 'USER')";
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef

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
<<<<<<< HEAD
        String sql = "SELECT * FROM users WHERE username = ? AND status = 'active'";
=======
        String sql = "SELECT * FROM users WHERE username = ?";
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
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
<<<<<<< HEAD
                user.setProfileImageUrl(rs.getString("profile_image_url"));
                user.setStatus(rs.getString("status"));
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
                return user;
            }
        }
        return null;
    }
<<<<<<< HEAD

    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setNic(rs.getString("nic"));
                user.setGender(rs.getString("gender"));
                user.setRole(rs.getString("role"));
                user.setProfileImageUrl(rs.getString("profile_image_url"));
                user.setStatus(rs.getString("status"));
                return user;
            }
        }
        return null;
    }

    public boolean updateUserProfile(User user) throws SQLException {
        String sql = "UPDATE users SET email = ?, phone_number = ?, nic = ?, gender = ?, profile_image_url = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPhoneNumber());
            stmt.setString(3, user.getNic());
            stmt.setString(4, user.getGender());
            stmt.setString(5, user.getProfileImageUrl());
            stmt.setInt(6, user.getUserId());

            return stmt.executeUpdate() > 0;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'USER' ORDER BY username";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setNic(rs.getString("nic"));
                user.setGender(rs.getString("gender"));
                user.setProfileImageUrl(rs.getString("profile_image_url"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        }
        return users;
    }

    public boolean toggleUserStatus(int userId) throws SQLException {
        String sql = "UPDATE users SET status = CASE WHEN status = 'active' THEN 'inactive' ELSE 'active' END WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
}
