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

    public void addMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, description, duration, release_date, rating, poster_url, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setInt(3, movie.getDuration());
            stmt.setDate(4, new java.sql.Date(movie.getReleaseDate().getTime()));
            stmt.setBigDecimal(5, movie.getRating());
            stmt.setString(6, movie.getPosterUrl());
            stmt.setString(7, movie.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding movie", e);
        }
    }

    public void updateMovie(Movie movie) {
        String sql = "UPDATE movies SET title=?, description=?, duration=?, release_date=?, " +
                "rating=?, status=? WHERE movie_id=?";

        if (movie.getPosterUrl() != null) {
            sql = "UPDATE movies SET title=?, description=?, duration=?, release_date=?, " +
                    "rating=?, status=?, poster_url=? WHERE movie_id=?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setInt(3, movie.getDuration());
            stmt.setDate(4, new java.sql.Date(movie.getReleaseDate().getTime()));
            stmt.setBigDecimal(5, movie.getRating());
            stmt.setString(6, movie.getStatus());

            if (movie.getPosterUrl() != null) {
                stmt.setString(7, movie.getPosterUrl());
                stmt.setInt(8, movie.getMovieId());
            } else {
                stmt.setInt(7, movie.getMovieId());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating movie", e);
        }
    }

    public void deleteMovie(int movieId) {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting movie", e);
        }
    }

    public Movie getMovieById(int movieId) {
        String sql = "SELECT * FROM movies WHERE movie_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setDescription(rs.getString("description"));
                movie.setDuration(rs.getInt("duration"));
                movie.setReleaseDate(rs.getDate("release_date"));
                movie.setRating(rs.getBigDecimal("rating"));
                movie.setPosterUrl(rs.getString("poster_url"));
                movie.setStatus(rs.getString("status"));
                return movie;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching movie", e);
        }
        return null;
    }
}
