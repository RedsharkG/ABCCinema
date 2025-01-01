package com.cinema.booking.service;

import com.cinema.booking.dao.BookingDAO;
import com.cinema.booking.model.Booking;
import java.sql.SQLException;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    public List<Booking> getUserBookings(int userId) throws SQLException {
        return bookingDAO.getUserBookings(userId);
    }

    public void createBooking(Booking booking) throws SQLException {
        bookingDAO.createBooking(booking);
    }

    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        bookingDAO.updateBookingStatus(bookingId, status);
    }
}
