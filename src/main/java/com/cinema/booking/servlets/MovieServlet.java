package com.cinema.booking.servlets;

import java.io.IOException;
<<<<<<< HEAD
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

=======
import java.util.List;
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cinema.booking.dao.MovieDAO;
<<<<<<< HEAD
import com.cinema.booking.dao.TicketDAO;
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
import com.cinema.booking.model.Movie;

@WebServlet("/user/movies")
public class MovieServlet extends HttpServlet {
    private MovieDAO movieDAO;
<<<<<<< HEAD
    private TicketDAO ticketDAO;
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef

    @Override
    public void init() {
        movieDAO = new MovieDAO();
<<<<<<< HEAD
        ticketDAO = new TicketDAO();
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
<<<<<<< HEAD
            Map<Integer, BigDecimal> moviePrices = ticketDAO.getMoviePrices();
            request.setAttribute("movies", movies);
            request.setAttribute("moviePrices", moviePrices);
=======
            request.setAttribute("movies", movies);
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
            request.getRequestDispatcher("/user/movies.jsp").forward(request, response);
        } catch (RuntimeException e) {
            request.setAttribute("error", "Failed to load movies: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
