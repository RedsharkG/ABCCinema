package com.cinema.booking.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cinema.booking.dao.MovieDAO;
import com.cinema.booking.model.Movie;

@WebServlet("/user/movies")
public class MovieServlet extends HttpServlet {
    private MovieDAO movieDAO;

    @Override
    public void init() {
        movieDAO = new MovieDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("movies", movies);
            request.getRequestDispatcher("/user/movies.jsp").forward(request, response);
        } catch (RuntimeException e) {
            request.setAttribute("error", "Failed to load movies: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
