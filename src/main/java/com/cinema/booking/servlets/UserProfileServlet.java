package com.cinema.booking.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import com.cinema.booking.dao.UserDAO;
import com.cinema.booking.model.User;
import com.cinema.booking.service.CloudinaryService;

@WebServlet("/user/profile/update")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class UserProfileServlet extends HttpServlet {
    private UserDAO userDAO;
    private CloudinaryService cloudinaryService;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        cloudinaryService = new CloudinaryService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        try {
            Part filePart = request.getPart("profileImage");
            if (filePart != null && filePart.getSize() > 0) {
                String imageUrl = cloudinaryService.uploadFile(filePart, "user-profiles");
                currentUser.setProfileImageUrl(imageUrl);
            }

            currentUser.setEmail(request.getParameter("email"));
            currentUser.setPhoneNumber(request.getParameter("phoneNumber"));
            currentUser.setNic(request.getParameter("nic"));
            currentUser.setGender(request.getParameter("gender"));

            if (userDAO.updateUserProfile(currentUser)) {
                session.setAttribute("user", currentUser);
                response.sendRedirect(request.getContextPath() + "/user/profile.jsp?updated=true");
            } else {
                throw new ServletException("Update failed");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
    }
}
