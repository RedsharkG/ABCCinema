package com.cinema.booking.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.cinema.booking.dao.MovieDAO;
import com.cinema.booking.model.Movie;
import com.cinema.booking.service.CloudinaryService;
import com.cinema.booking.util.ResponseHandler;
import java.sql.Date;
import java.math.BigDecimal;

@WebServlet("/admin/movies/*")
@MultipartConfig
public class AdminMovieServlet extends HttpServlet {
    private MovieDAO movieDAO;
    private CloudinaryService cloudinaryService;

    @Override
    public void init() {
        movieDAO = new MovieDAO();
        cloudinaryService = new CloudinaryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            int movieId = Integer.parseInt(pathInfo.substring(1));
            Movie movie = movieDAO.getMovieById(movieId);
            ResponseHandler.sendJsonResponse(response, movie);
        } else {
            request.setAttribute("movies", movieDAO.getAllMovies());
            request.getRequestDispatcher("/admin/movies.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if ("update".equals(action)) {
                updateMovie(request, response);
            } else if ("add".equals(action)) {
                addMovie(request, response);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ResponseHandler.sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            int movieId = Integer.parseInt(pathInfo.substring(1));
            movieDAO.deleteMovie(movieId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ResponseHandler.sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addMovie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Movie movie = new Movie();
        movie.setTitle(request.getParameter("title"));
        movie.setDescription(request.getParameter("description"));
        movie.setDuration(Integer.parseInt(request.getParameter("duration")));
        movie.setReleaseDate(Date.valueOf(request.getParameter("releaseDate")));
        movie.setRating(new BigDecimal(request.getParameter("rating")));
        movie.setStatus(request.getParameter("status"));

        Part posterPart = request.getPart("poster");
        if (posterPart != null && posterPart.getSize() > 0) {
            String posterUrl = cloudinaryService.uploadFile(posterPart, "movie_posters");
            movie.setPosterUrl(posterUrl);
        }

        movieDAO.addMovie(movie);
        response.sendRedirect(request.getContextPath() + "/admin/movies");
    }

    private void updateMovie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Movie movie = new Movie();
        movie.setMovieId(Integer.parseInt(request.getParameter("movieId")));
        movie.setTitle(request.getParameter("title"));
        movie.setDescription(request.getParameter("description"));
        movie.setDuration(Integer.parseInt(request.getParameter("duration")));
        movie.setReleaseDate(Date.valueOf(request.getParameter("releaseDate")));
        movie.setRating(new BigDecimal(request.getParameter("rating")));
        movie.setStatus(request.getParameter("status"));

        Part posterPart = request.getPart("poster");
        if (posterPart != null && posterPart.getSize() > 0) {
            String posterUrl = cloudinaryService.uploadFile(posterPart, "movie_posters");
            movie.setPosterUrl(posterUrl);
        }

        movieDAO.updateMovie(movie);
        response.sendRedirect(request.getContextPath() + "/admin/movies");
    }
}
