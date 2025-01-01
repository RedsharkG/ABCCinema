package com.cinema.booking.dao;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.cinema.booking.util.DBConnection;
import com.cinema.booking.model.Payment;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public void createPayment(int bookingId, int userId, BigDecimal amount, String paymentIntentId)
            throws SQLException {
        String sql = "INSERT INTO payments (booking_id, user_id, amount, payment_intent_id, status) " +
                "VALUES (?, ?, ?, ?, 'COMPLETED')";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.setInt(2, userId);
            stmt.setBigDecimal(3, amount);
            stmt.setString(4, paymentIntentId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Payment creation failed");
            }
        }
    }

    public Payment getPaymentByBookingId(int bookingId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPayment(rs);
            }
        }
        return null;
    }

    public List<Payment> getUserPayments(int userId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE user_id = ? ORDER BY created_at DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        }
        return payments;
    }

    public void updatePaymentStatus(String paymentIntentId, String status) throws SQLException {
        String sql = "UPDATE payments SET status = ? WHERE payment_intent_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, paymentIntentId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Payment status update failed");
            }
        }
    }

    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setBookingId(rs.getInt("booking_id"));
        payment.setUserId(rs.getInt("user_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setPaymentIntentId(rs.getString("payment_intent_id"));
        payment.setStatus(rs.getString("status"));
        payment.setCreatedAt(rs.getTimestamp("created_at"));
        return payment;
    }
}
