package com.cinema.booking.util;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminDataInitializer {
    public static void initializeAdminData() {
        String sql = "INSERT INTO admins (username, password, email, full_name, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Admin 1
            stmt.setString(1, "admin");
            stmt.setString(2, PasswordHasher.hash("admin123"));
            stmt.setString(3, "admin@cinema.com");
            stmt.setString(4, "System Administrator");
            stmt.setString(5, "active");
            stmt.executeUpdate();
            
            // Admin 2
            stmt.setString(1, "manager");
            stmt.setString(2, PasswordHasher.hash("manager123"));
            stmt.setString(3, "manager@cinema.com");
            stmt.setString(4, "Theater Manager");
            stmt.setString(5, "active");
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
