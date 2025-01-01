package com.cinema.booking.service;

import com.cinema.booking.dao.PaymentDAO;
import com.cinema.booking.dao.BookingDAO;
import com.cinema.booking.dao.UserDAO;
import com.cinema.booking.dao.MovieDAO;
import com.cinema.booking.model.Payment;
import com.cinema.booking.model.Booking;
import com.cinema.booking.model.User;
import com.cinema.booking.constants.PaymentStatus;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private PaymentDAO paymentDAO;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    private MovieDAO movieDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
        this.bookingDAO = new BookingDAO();
        this.userDAO = new UserDAO();
        this.movieDAO = new MovieDAO();
    }

    public void processPayment(int bookingId, int userId, BigDecimal amount, String paymentIntentId)
            throws SQLException {
        try {
            paymentDAO.createPayment(bookingId, userId, amount, paymentIntentId);
            bookingDAO.updatePaymentStatus(bookingId, PaymentStatus.COMPLETED, paymentIntentId);

            // Send email confirmation
            Booking booking = bookingDAO.getBookingById(bookingId);
            User user = userDAO.getUserById(userId);
            String movieTitle = movieDAO.getMovieById(booking.getMovieId()).getTitle();

            EmailService emailService = new EmailService();
            emailService.sendBookingConfirmation(user, booking, movieTitle);
        } catch (Exception e) {
            throw new SQLException("Payment processing failed: " + e.getMessage());
        }
    }

    public void handleFailedPayment(int bookingId, String paymentIntentId) throws SQLException {
        bookingDAO.updatePaymentStatus(bookingId, PaymentStatus.FAILED, paymentIntentId);
    }

    public void handleRefund(String paymentIntentId) throws SQLException {
        paymentDAO.updatePaymentStatus(paymentIntentId, PaymentStatus.REFUNDED);
    }

    public Payment getPaymentByBookingId(int bookingId) throws SQLException {
        return paymentDAO.getPaymentByBookingId(bookingId);
    }

    public List<Payment> getUserPayments(int userId) throws SQLException {
        return paymentDAO.getUserPayments(userId);
    }

    public void updatePaymentStatus(String paymentIntentId, String status) throws SQLException {
        paymentDAO.updatePaymentStatus(paymentIntentId, status);
    }
}
