package com.cinema.booking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cinema.booking.model.Show;
import com.cinema.booking.util.DBConnection;

public class ShowDAO {
    private Connection connection;

    public ShowDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
    }

    public List<Show> getAvailableShows() {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT s.*, m.title as movie_title, t.name as theater_name " +
                    "FROM shows s " +
                    "JOIN movies m ON s.movie_id = m.movie_id " +
                    "JOIN theaters t ON s.theater_id = t.theater_id " +
                    "WHERE s.available_seats > 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Show show = new Show();
                show.setShowId(rs.getInt("show_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setTheaterId(rs.getInt("theater_id"));
                show.setShowTime(rs.getTimestamp("show_time"));
                show.setPrice(rs.getBigDecimal("price"));
                show.setAvailableSeats(rs.getInt("available_seats"));
                show.setMovieTitle(rs.getString("movie_title"));
                show.setTheaterName(rs.getString("theater_name"));
                shows.add(show);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching shows: " + e.getMessage());
        }
        return shows;
    }
}
