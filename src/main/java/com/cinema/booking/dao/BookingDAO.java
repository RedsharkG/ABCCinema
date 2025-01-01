package com.cinema.booking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cinema.booking.model.Booking;
import com.cinema.booking.util.DBConnection;

public class BookingDAO {
    private Connection connection;

    public BookingDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public int createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, movie_id, show_time, booking_date, " +
                "num_tickets, total_amount, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getMovieId());
            stmt.setString(3, booking.getShowTime());
            stmt.setDate(4, booking.getBookingDate());
            stmt.setInt(5, booking.getNumTickets());
            stmt.setBigDecimal(6, booking.getTotalAmount());
            stmt.setString(7, booking.getPaymentStatus());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Creating booking failed, no ID obtained.");
        }
    }

    public Booking getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setMovieId(rs.getInt("movie_id"));
                booking.setShowTime(rs.getString("show_time"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setNumTickets(rs.getInt("num_tickets"));
                booking.setTotalAmount(rs.getBigDecimal("total_amount"));
                booking.setPaymentStatus(rs.getString("payment_status"));
                booking.setPaymentIntentId(rs.getString("payment_intent_id"));
                booking.setCreatedAt(rs.getTimestamp("created_at"));
                return booking;
            }
        }
        return null;
    }

    public void updatePaymentStatus(int bookingId, String status, String paymentIntentId)
            throws SQLException {
        String sql = "UPDATE bookings SET payment_status = ?, payment_intent_id = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, paymentIntentId);
            stmt.setInt(3, bookingId);
            stmt.executeUpdate();
        }
    }

    public List<Booking> getUserBookings(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ? ORDER BY created_at DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setMovieId(rs.getInt("movie_id"));
                booking.setShowTime(rs.getString("show_time"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setNumTickets(rs.getInt("num_tickets"));
                booking.setTotalAmount(rs.getBigDecimal("total_amount"));
                booking.setPaymentStatus(rs.getString("payment_status"));
                booking.setPaymentIntentId(rs.getString("payment_intent_id"));
                booking.setCreatedAt(rs.getTimestamp("created_at"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        }
    }
}
