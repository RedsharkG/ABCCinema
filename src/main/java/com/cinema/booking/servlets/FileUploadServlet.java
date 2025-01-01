package com.cinema.booking.servlets;

import com.cinema.booking.service.CloudinaryService;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 15 // 15MB
)
public class FileUploadServlet extends HttpServlet {
    private CloudinaryService cloudinaryService;

    @Override
    public void init() {
        cloudinaryService = new CloudinaryService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String folder = request.getParameter("folder");

        try {
            String fileUrl = cloudinaryService.uploadFile(filePart, folder);
            response.setContentType("application/json");
            response.getWriter().write("{\"url\": \"" + fileUrl + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
