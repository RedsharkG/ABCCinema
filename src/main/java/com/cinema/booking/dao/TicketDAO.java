package com.cinema.booking.dao;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cinema.booking.model.Ticket;
import com.cinema.booking.util.DBConnection;

public class TicketDAO {
    private Connection connection;

    public TicketDAO() {
        try {
            connection = DBConnection.getConnection();
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.*, m.title FROM tickets t " +
                "JOIN movies m ON t.movie_id = m.movie_id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tickets: " + e.getMessage());
        }
        return tickets;
    }

    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(rs.getInt("ticket_id"));
        ticket.setMovieId(rs.getInt("movie_id")); // Changed from show_id to movie_id
        ticket.setPrice(rs.getBigDecimal("price"));
        ticket.setQuantity(rs.getInt("quantity"));
        ticket.setStatus(rs.getString("status"));
        ticket.setMovieTitle(rs.getString("title"));
        return ticket;
    }

    public void addTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (movie_id, price, quantity, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticket.getMovieId());
            stmt.setBigDecimal(2, ticket.getPrice());
            stmt.setInt(3, ticket.getQuantity());
            stmt.setString(4, ticket.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding ticket: " + e.getMessage());
        }
    }

    public void updateTicket(Ticket ticket) {
        String sql = "UPDATE tickets SET price = ?, quantity = ?, status = ? WHERE ticket_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, ticket.getPrice());
            stmt.setInt(2, ticket.getQuantity());
            stmt.setString(3, ticket.getStatus());
            stmt.setInt(4, ticket.getTicketId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating ticket: " + e.getMessage());
        }
    }

    public boolean deleteTicket(int ticketId) {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting ticket: " + e.getMessage());
        }
    }

    public Ticket getTicketById(int ticketId) {
        String sql = "SELECT t.*, m.title FROM tickets t " +
                "JOIN movies m ON t.movie_id = m.movie_id " +
                "WHERE t.ticket_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTicket(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching ticket: " + e.getMessage());
        }
        return null;
    }

    public BigDecimal getMoviePrice(int movieId) {
        String sql = "SELECT MIN(price) as price FROM tickets WHERE movie_id = ? AND status = 'AVAILABLE'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("price");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching ticket price", e);
        }
        return BigDecimal.ZERO;
    }

    public Map<Integer, BigDecimal> getMoviePrices() {
        Map<Integer, BigDecimal> prices = new HashMap<>();
        String sql = "SELECT movie_id, MIN(price) as price FROM tickets WHERE status = 'AVAILABLE' GROUP BY movie_id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prices.put(rs.getInt("movie_id"), rs.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching movie prices: " + e.getMessage());
        }
        return prices;
    }

}
