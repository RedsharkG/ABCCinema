package com.cinema.booking.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinema.booking.model.Booking;
import com.cinema.booking.model.Movie;
import com.cinema.booking.model.User;
import com.cinema.booking.service.BookingService;
import com.cinema.booking.dao.MovieDAO;

@WebServlet("/user/bookings")
public class UserBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingService bookingService;
    private MovieDAO movieDAO;

    @Override
    public void init() {
        bookingService = new BookingService();
        movieDAO = new MovieDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        try {
            List<Booking> bookings = bookingService.getUserBookings(user.getUserId());

            // Enhance bookings with movie details
            for (Booking booking : bookings) {
                Movie movie = movieDAO.getMovieById(booking.getMovieId());
                booking.setMovieTitle(movie.getTitle());
            }

            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("/user/bookings.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to retrieve bookings");
            request.getRequestDispatcher("/user/bookings.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        bookingService = null;
        movieDAO = null;
    }
}
