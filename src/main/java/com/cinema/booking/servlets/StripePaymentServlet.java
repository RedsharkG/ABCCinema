package com.cinema.booking.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.cinema.booking.service.StripeService;
import com.cinema.booking.service.PaymentService;
import com.cinema.booking.dao.BookingDAO;
import com.cinema.booking.dao.ReservedSeatDAO;
import com.cinema.booking.model.ReservedSeat;
import com.cinema.booking.webhooks.StripeWebhookHandler;
import com.cinema.booking.config.StripeConfig;
import com.cinema.booking.util.ResponseHandler;
import com.cinema.booking.constants.PaymentStatus;

@WebServlet("/stripe/*")
public class StripePaymentServlet extends HttpServlet {
    private StripeService stripeService;
    private PaymentService paymentService;
    private BookingDAO bookingDAO;
    private StripeWebhookHandler webhookHandler;

    @Override
    public void init() {
        stripeService = new StripeService();
        paymentService = new PaymentService();
        bookingDAO = new BookingDAO();
        webhookHandler = new StripeWebhookHandler();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String publicKey = StripeConfig.getPublicKey();
        request.setAttribute("stripePublicKey", publicKey);
        request.getSession().setAttribute("stripePublicKey", publicKey);
        request.getRequestDispatcher("/user/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            switch (pathInfo) {
                case "/create-payment-intent":
                    createPaymentIntent(request, response);
                    break;
                case "/save-payment":
                    handlePaymentSuccess(request, response);
                    break;
                case "/payment-failed":
                    handlePaymentFailure(request, response);
                    break;
                case "/webhook":
                    handleWebhook(request, response);
                    break;
                case "/refund":
                    handleRefund(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (StripeException | SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject()
                    .put("error", e.getMessage()).toString());
        }
    }

    private void createPaymentIntent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, StripeException, SQLException {
        HttpSession session = request.getSession();
        System.out.println("Session userId: " + session.getAttribute("userId"));

        try {
            String requestData = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject jsonRequest = new JSONObject(requestData);
            String amountStr = jsonRequest.getString("amount");

            if (amountStr == null || amountStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Amount is required");
            }

            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }

            Long amountInCents = amount.multiply(new BigDecimal("100")).longValue();
            int bookingId = jsonRequest.getInt("bookingId");

            PaymentIntent intent = stripeService.createPaymentIntent(amountInCents, "lkr");
            bookingDAO.updatePaymentStatus(bookingId, PaymentStatus.PENDING, intent.getId());

            JSONObject jsonResponse = new JSONObject()
                    .put("clientSecret", intent.getClientSecret());

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            System.err.println("Error creating payment intent: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new JSONObject()
                    .put("error", e.getMessage()).toString());
        }
    }

    private void handlePaymentSuccess(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String requestData = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject jsonRequest = new JSONObject(requestData);

            if (!jsonRequest.has("bookingId") || !jsonRequest.has("userId") ||
                    !jsonRequest.has("amount") || !jsonRequest.has("paymentIntentId")) {
                throw new IllegalArgumentException("Missing required payment parameters");
            }

            int bookingId = jsonRequest.getInt("bookingId");
            int userId = jsonRequest.getInt("userId");
            BigDecimal amount = new BigDecimal(jsonRequest.getString("amount"));
            String paymentIntentId = jsonRequest.getString("paymentIntentId");

            paymentService.processPayment(bookingId, userId, amount, paymentIntentId);

            HttpSession session = request.getSession();
            String[] selectedSeats = ((String) session.getAttribute("selectedSeats")).split(",");

            List<ReservedSeat> seats = new ArrayList<>();
            for (String seatNumber : selectedSeats) {
                ReservedSeat seat = new ReservedSeat();
                seat.setBookingId(bookingId);
                seat.setSeatNumber(seatNumber.trim());
                seat.setStatus("CONFIRMED");
                seats.add(seat);
            }

            ReservedSeatDAO reservedSeatDAO = new ReservedSeatDAO();
            reservedSeatDAO.reserveSeats(seats);

            bookingDAO.updatePaymentStatus(bookingId, PaymentStatus.COMPLETED, paymentIntentId);

            ResponseHandler.sendSuccessResponse(response,
                    new JSONObject().put("message", "Payment processed successfully"));

        } catch (Exception e) {
            ResponseHandler.sendErrorResponse(response, "Failed to process payment: " + e.getMessage(),
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handlePaymentFailure(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            String paymentIntentId = request.getParameter("paymentIntentId");

            paymentService.handleFailedPayment(bookingId, paymentIntentId);
            ResponseHandler.sendSuccessResponse(response, "Payment failure recorded");
        } catch (SQLException e) {
            ResponseHandler.sendErrorResponse(response, "Failed to record payment failure",
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleWebhook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String payload = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        String sigHeader = request.getHeader("Stripe-Signature");

        try {
            webhookHandler.handleWebhookEvent(payload, sigHeader);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new JSONObject()
                    .put("error", e.getMessage()).toString());
        }
    }

    private void handleRefund(HttpServletRequest request, HttpServletResponse response)
            throws IOException, StripeException {
        String paymentIntentId = request.getParameter("paymentIntentId");
        Refund refund = stripeService.createRefund(paymentIntentId);

        JSONObject jsonResponse = new JSONObject()
                .put("refundId", refund.getId())
                .put("status", refund.getStatus());

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
