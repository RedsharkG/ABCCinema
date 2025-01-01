package com.cinema.booking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cinema.booking.model.Feedback;
import com.cinema.booking.util.DBConnection;

public class FeedbackDAO {
    private Connection connection;

    public FeedbackDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public void addFeedback(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO feedback (user_id, message, rating) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getUserId());
            stmt.setString(2, feedback.getMessage());
            stmt.setInt(3, feedback.getRating());
            stmt.executeUpdate();
        }
    }

    public List<Feedback> getAllFeedbackWithUsernames() throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, u.username FROM feedback f JOIN users u ON f.user_id = u.user_id ORDER BY f.created_at DESC";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setUserId(rs.getInt("user_id"));
                feedback.setMessage(rs.getString("message"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setCreatedAt(rs.getTimestamp("created_at"));
                feedback.setUsername(rs.getString("username"));
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public Feedback getFeedbackById(int feedbackId) throws SQLException {
        String sql = "SELECT f.*, u.username FROM feedback f JOIN users u ON f.user_id = u.user_id WHERE f.feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setUserId(rs.getInt("user_id"));
                feedback.setMessage(rs.getString("message"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setCreatedAt(rs.getTimestamp("created_at"));
                feedback.setUsername(rs.getString("username"));
                return feedback;
            }
        }
        return null;
    }

    public boolean updateFeedback(Feedback feedback) throws SQLException {
        String sql = "UPDATE feedback SET message = ?, rating = ? WHERE feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, feedback.getMessage());
            stmt.setInt(2, feedback.getRating());
            stmt.setInt(3, feedback.getFeedbackId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteFeedback(int feedbackId) throws SQLException {
        String sql = "DELETE FROM feedback WHERE feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            return stmt.executeUpdate() > 0;
        }
    }
}
