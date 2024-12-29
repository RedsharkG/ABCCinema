package com.cinema.booking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cinema.booking.model.Movie;
import com.cinema.booking.util.DBConnection;

public class MovieDAO {
    private Connection connection;

    public MovieDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies ORDER BY release_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setDescription(rs.getString("description"));
                movie.setDuration(rs.getInt("duration"));
                movie.setReleaseDate(rs.getDate("release_date"));
                movie.setRating(rs.getBigDecimal("rating"));
                movie.setPosterUrl(rs.getString("poster_url"));
                movie.setStatus(rs.getString("status"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching movies", e);
        }
        return movies;
    }
}
