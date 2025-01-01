package com.cinema.booking.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.cinema.booking.model.*;
import com.cinema.booking.dao.*;
import com.cinema.booking.config.StripeConfig;
import java.math.BigDecimal;
import java.sql.Date;

@WebServlet("/user/booking/*")
public class BookingServlet extends HttpServlet {
    private BookingDAO bookingDAO;
    private MovieDAO movieDAO;
    private TicketDAO ticketDAO;

    @Override
    public void init() {
        bookingDAO = new BookingDAO();
        movieDAO = new MovieDAO();
        ticketDAO = new TicketDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int movieId = Integer.parseInt(request.getParameter("movieId"));
        Movie movie = movieDAO.getMovieById(movieId);
        BigDecimal ticketPrice = ticketDAO.getMoviePrice(movieId);

        request.setAttribute("movie", movie);
        request.setAttribute("ticketPrice", ticketPrice);
        request.getRequestDispatcher("/user/booking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getUserId() == 0) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        response.setContentType("text/html");
        response.getWriter().write("<script>" +
                "window.onload = function() {" +
                "    sessionStorage.setItem('userId', '" + user.getUserId() + "');" +
                "    sessionStorage.setItem('userEmail', '" + user.getEmail() + "');" +
                "}</script>");

        session.setAttribute("stripePublicKey", StripeConfig.getPublicKey());
        session.setAttribute("userId", user.getUserId());

        Movie movie = movieDAO.getMovieById(Integer.parseInt(request.getParameter("movieId")));
        session.setAttribute("movieId", movie.getMovieId());
        session.setAttribute("selectedMovie", movie.getTitle());
        session.setAttribute("selectedShowTime", request.getParameter("showTime"));
        session.setAttribute("selectedDate", request.getParameter("bookingDate"));

        BigDecimal ticketPrice = ticketDAO.getMoviePrice(movie.getMovieId());
        int numTickets = Integer.parseInt(request.getParameter("numTickets"));
        BigDecimal serviceFee = new BigDecimal("0.00");
        BigDecimal totalAmount = ticketPrice.multiply(new BigDecimal(numTickets)).add(serviceFee);

        session.setAttribute("ticketPrice", ticketPrice);
        session.setAttribute("numTickets", numTickets);
        session.setAttribute("serviceFee", serviceFee);
        session.setAttribute("totalAmount", totalAmount);

        Booking booking = new Booking();
        booking.setUserId(user.getUserId());
        booking.setMovieId(Integer.parseInt(request.getParameter("movieId")));
        booking.setShowTime(request.getParameter("showTime"));
        booking.setBookingDate(Date.valueOf(request.getParameter("bookingDate")));
        booking.setNumTickets(Integer.parseInt(request.getParameter("numTickets")));
        booking.setTotalAmount(totalAmount);
        booking.setPaymentStatus("PENDING");

        try {
            int bookingId = bookingDAO.createBooking(booking);
            session.setAttribute("currentBookingId", bookingId);
            session.setAttribute("ticketPrice", ticketDAO.getMoviePrice(booking.getMovieId()));
            session.setAttribute("ticketCount", booking.getNumTickets());
            response.sendRedirect(request.getContextPath() + "/user/seat-selection.jsp");
        } catch (Exception e) {
            request.setAttribute("error", "Booking failed: " + e.getMessage());
            request.getRequestDispatcher("/user/booking.jsp").forward(request, response);
        }
    }
}
