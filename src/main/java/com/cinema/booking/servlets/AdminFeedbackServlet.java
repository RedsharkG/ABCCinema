package com.cinema.booking.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cinema.booking.dao.FeedbackDAO;
import com.cinema.booking.model.Feedback;
import com.cinema.booking.util.ResponseHandler;

@WebServlet("/admin/feedback")
public class AdminFeedbackServlet extends HttpServlet {
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        feedbackDAO = new FeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("get".equals(action)) {
            int feedbackId = Integer.parseInt(request.getParameter("id"));
            try {
                Feedback feedback = feedbackDAO.getFeedbackById(feedbackId);
                ResponseHandler.sendJsonResponse(response, feedback);
            } catch (SQLException e) {
                ResponseHandler.sendErrorResponse(response, e.getMessage(),
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            try {
                List<Feedback> feedbacks = feedbackDAO.getAllFeedbackWithUsernames();
                request.setAttribute("feedbacks", feedbacks);
                request.getRequestDispatcher("/admin/feedback-management.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error loading feedback", e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            boolean success = false;
            switch (action) {
                case "update":
                    Feedback feedback = new Feedback();
                    feedback.setFeedbackId(Integer.parseInt(request.getParameter("feedbackId")));
                    feedback.setRating(Integer.parseInt(request.getParameter("rating")));
                    feedback.setMessage(request.getParameter("message"));
                    success = feedbackDAO.updateFeedback(feedback);
                    break;
                case "delete":
                    int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
                    success = feedbackDAO.deleteFeedback(feedbackId);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    return;
            }
            ResponseHandler.sendJsonResponse(response, Map.of("success", success));
        } catch (SQLException e) {
            ResponseHandler.sendErrorResponse(response, e.getMessage(),
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        // Cleanup resources if needed
    }
}
