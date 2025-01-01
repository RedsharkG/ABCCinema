package com.cinema.booking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cinema.booking.model.ReservedSeat;
import com.cinema.booking.util.DBConnection;

public class ReservedSeatDAO {
    private Connection connection;

    public ReservedSeatDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public void reserveSeats(List<ReservedSeat> seats) throws SQLException {
        String sql = "INSERT INTO reserved_seats (booking_id, seat_number, status) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (ReservedSeat seat : seats) {
                stmt.setInt(1, seat.getBookingId());
                stmt.setString(2, seat.getSeatNumber());
                stmt.setString(3, seat.getStatus());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public List<String> getReservedSeats(int movieId, String showTime, Date bookingDate) throws SQLException {
        List<String> reservedSeats = new ArrayList<>();
        String sql = "SELECT rs.seat_number FROM reserved_seats rs " +
                "JOIN bookings b ON rs.booking_id = b.booking_id " +
                "WHERE b.movie_id = ? AND b.show_time = ? AND b.booking_date = ? " +
                "AND b.payment_status = 'COMPLETED'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            stmt.setString(2, showTime);
            stmt.setDate(3, bookingDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservedSeats.add(rs.getString("seat_number"));
            }
        }
        return reservedSeats;
    }

    public void updateSeatStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE reserved_seats SET status = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        }
    }

    public List<ReservedSeat> getSeatsByBookingId(int bookingId) throws SQLException {
        List<ReservedSeat> seats = new ArrayList<>();
        String sql = "SELECT * FROM reserved_seats WHERE booking_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ReservedSeat seat = new ReservedSeat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setBookingId(rs.getInt("booking_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setStatus(rs.getString("status"));
                seats.add(seat);
            }
        }
        return seats;
    }
}
