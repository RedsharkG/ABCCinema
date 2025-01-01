package com.cinema.booking.servlets;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/navigation")
public class NavigationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getParameter("page");

        switch (page) {
            case "tickets":
                response.sendRedirect(request.getContextPath() + "/admin/tickets");
                return;
            case "users":
                response.sendRedirect(request.getContextPath() + "/admin/users");
                return;
            case "feedback":
                response.sendRedirect(request.getContextPath() + "/admin/feedback");
                return;
            case "dashboard":
                request.setAttribute("currentPage", "dashboard");
                request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
                return;
            default:
                if (!isValidPage(page)) {
                    response.sendRedirect(request.getContextPath() + "/admin/navigation?page=dashboard");
                    return;
                }
        }

        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/admin/" + page + ".jsp").forward(request, response);
    }

    private boolean isValidPage(String page) {
        return Arrays.asList("dashboard", "tickets", "users", "bookings", "feedback").contains(page);
    }
}
