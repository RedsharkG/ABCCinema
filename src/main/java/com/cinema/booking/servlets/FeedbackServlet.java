package com.cinema.booking.servlets;

import com.cinema.booking.dao.FeedbackDAO;
import com.cinema.booking.model.Feedback;
import com.cinema.booking.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/feedback")
public class FeedbackServlet extends HttpServlet {
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() {
        feedbackDAO = new FeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Feedback> feedbacks = feedbackDAO.getAllFeedbackWithUsernames();
            request.setAttribute("feedbacks", feedbacks);
            request.getRequestDispatcher("/user/feedback.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to load feedback");
            request.getRequestDispatcher("/user/feedback.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        try {
            Feedback feedback = new Feedback();
            feedback.setUserId(user.getUserId());
            feedback.setMessage(request.getParameter("message"));
            feedback.setRating(Integer.parseInt(request.getParameter("rating")));

            feedbackDAO.addFeedback(feedback);
            request.setAttribute("success", "Thank you for your feedback!");

            List<Feedback> feedbacks = feedbackDAO.getAllFeedbackWithUsernames();
            request.setAttribute("feedbacks", feedbacks);

            request.getRequestDispatcher("/user/feedback.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to submit feedback");
            request.getRequestDispatcher("/user/feedback.jsp").forward(request, response);
        }
    }
}
