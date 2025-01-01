package com.cinema.booking.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cinema.booking.dao.TicketDAO;
import com.cinema.booking.dao.MovieDAO;
// import com.cinema.booking.dao.ShowDAO;
import com.cinema.booking.model.Movie;
// import com.cinema.booking.model.Show;
import com.cinema.booking.model.Ticket;

@WebServlet("/admin/tickets/*")
public class TicketServlet extends HttpServlet {
    private TicketDAO ticketDAO;
    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        ticketDAO = new TicketDAO();
        movieDAO = new MovieDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Ticket> tickets = ticketDAO.getAllTickets();
            List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("tickets", tickets);
            request.setAttribute("movies", movies);
            request.getRequestDispatcher("/admin/tickets.jsp").forward(request, response);
        } catch (RuntimeException e) {
            request.setAttribute("error", "Failed to load tickets: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addTicket(request, response);
                    break;
                case "update":
                    updateTicket(request, response);
                    break;
                case "delete":
                    deleteTicket(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/tickets");
            }
        } catch (RuntimeException e) {
            request.setAttribute("error", "Operation failed: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void addTicket(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Ticket ticket = new Ticket();
        ticket.setMovieId(Integer.parseInt(request.getParameter("movieId")));
        ticket.setPrice(new BigDecimal(request.getParameter("price")));
        ticket.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        ticket.setStatus(request.getParameter("status"));

        ticketDAO.addTicket(ticket);
        response.sendRedirect(request.getContextPath() + "/admin/tickets");
    }

    private void updateTicket(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(Integer.parseInt(request.getParameter("ticketId")));
        ticket.setPrice(new BigDecimal(request.getParameter("price")));
        ticket.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        ticket.setStatus(request.getParameter("status"));

        ticketDAO.updateTicket(ticket);
        response.sendRedirect(request.getContextPath() + "/admin/tickets");
    }

    private void deleteTicket(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        ticketDAO.deleteTicket(ticketId);
        response.sendRedirect(request.getContextPath() + "/admin/tickets");
    }
}
