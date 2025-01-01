package com.cinema.booking.model;

import java.sql.Timestamp;

public class Feedback {
    private int feedbackId;
    private int userId;
    private String message;
    private int rating;
    private Timestamp createdAt;
    private String username;

    // Getters
    public int getFeedbackId() {
        return feedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public int getRating() {
        return rating;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    // Setters
    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
