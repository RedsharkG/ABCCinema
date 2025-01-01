package com.cinema.booking.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import com.cinema.booking.model.Booking;
import com.cinema.booking.model.User;

public class EmailService {
    private static final String FROM_EMAIL = "abccinema50@gmail.com";
    private static final String EMAIL_PASSWORD = "fnqytkdotfhjbudx";

    public void sendBookingConfirmation(User user, Booking booking, String movieTitle) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Booking Confirmation - ABC Cinema");

            String htmlContent = String.format(
                    """
                            <html>
                            <head>
                                <style>
                                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                                    .header { background: linear-gradient(45deg, #1a1a1a, #333); color: #FFD700; padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }
                                    .content { background: #f9f9f9; padding: 20px; border-radius: 0 0 10px 10px; }
                                    .booking-table { width: 100%%; border-collapse: collapse; margin: 20px 0; }
                                    .booking-table th, .booking-table td { padding: 12px; border: 1px solid #ddd; }
                                    .booking-table th { background: #1a1a1a; color: #FFD700; }
                                    .total { background: #f5f5f5; font-weight: bold; }
                                    .footer { text-align: center; margin-top: 20px; color: #666; }
                                    .button { display: inline-block; padding: 10px 20px; background: #FFD700; color: #000; text-decoration: none; border-radius: 5px; margin-top: 15px; }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <div class="header">
                                        <h1>ABC Cinema</h1>
                                        <p>Booking Confirmation</p>
                                    </div>
                                    <div class="content">
                                        <h2>Dear %s,</h2>
                                        <p>Thank you for choosing ABC Cinema. Your booking has been confirmed!</p>

                                        <table class="booking-table">
                                            <tr>
                                                <th colspan="2">Booking Details</th>
                                            </tr>
                                            <tr>
                                                <td><strong>Movie</strong></td>
                                                <td>%s</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Show Time</strong></td>
                                                <td>%s</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Date</strong></td>
                                                <td>%s</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Number of Tickets</strong></td>
                                                <td>%d</td>
                                            </tr>
                                            <tr class="total">
                                                <td><strong>Total Amount</strong></td>
                                                <td>%.2f LKR</td>
                                            </tr>
                                        </table>

                                        <p><strong>Booking ID:</strong> #%d</p>

                                        <div style="text-align: center;">
                                            <a href="http://localhost:8080/CinemaBookingAdminPanel/user/bookings" class="button">View My Bookings</a>
                                        </div>
                                    </div>
                                    <div class="footer">
                                        <p>This is an automated message, please do not reply.</p>
                                        <p>ABC Cinema | Contact: +94 123 456 789 | Email: abccinema50@gmail.com</p>
                                    </div>
                                </div>
                            </body>
                            </html>
                            """,
                    user.getUsername(),
                    movieTitle,
                    booking.getShowTime(),
                    booking.getBookingDate(),
                    booking.getNumTickets(),
                    booking.getTotalAmount(),
                    booking.getBookingId());

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
