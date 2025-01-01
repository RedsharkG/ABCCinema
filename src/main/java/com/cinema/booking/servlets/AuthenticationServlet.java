package com.cinema.booking.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.cinema.booking.dao.AdminDAO;
import com.cinema.booking.model.Admin;

@WebServlet("/admin/auth")
public class AuthenticationServlet extends HttpServlet {
    private AdminDAO adminDAO;

    @Override
    public void init() {
        adminDAO = new AdminDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "login":
                    handleLogin(request, response);
                    break;
                case "register":
                    handleRegistration(request, response);
                    break;
                case "logout":
                    handleLogout(request, response);
                    break;
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException, SQLException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    try {
        Admin admin = adminDAO.authenticate(username, password);
        if (admin != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("admin", admin);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes session
            response.sendRedirect(request.getContextPath() + "/admin/navigation?page=dashboard");
        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        }
    } catch (SQLException e) {
        request.setAttribute("error", "Database error: " + e.getMessage());
        request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
    }
}


    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        Admin admin = new Admin();
        admin.setUsername(request.getParameter("username"));
        admin.setPassword(request.getParameter("password"));
        admin.setEmail(request.getParameter("email"));
        admin.setFullName(request.getParameter("fullName"));
        
        if (adminDAO.registerAdmin(admin)) {
            response.sendRedirect(request.getContextPath() + "/admin/login.jsp?registered=true");
        } else {
            request.setAttribute("error", "Registration failed");
            request.getRequestDispatcher("/admin/register.jsp").forward(request, response);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
    }

    @Override
    public void destroy() {
        if (adminDAO != null) {
            adminDAO.close();
        }
    }
}
