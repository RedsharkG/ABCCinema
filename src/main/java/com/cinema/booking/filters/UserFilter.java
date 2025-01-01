package com.cinema.booking.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/user/*")
public class UserFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        boolean isHomePage = requestURI.endsWith("/home.jsp") || requestURI.endsWith("/home2.jsp");
        boolean isLoginPage = requestURI.endsWith("login.jsp");
        boolean isRegisterPage = requestURI.endsWith("register.jsp");
        boolean isAuthServlet = requestURI.endsWith("/auth");
<<<<<<< HEAD
        boolean isMoviesPage = requestURI.endsWith("/movies");
        boolean isPaymentPage = requestURI.contains("/payment");
        boolean isBookingsPage = requestURI.endsWith("/bookings");
        boolean isFeedbackPage = requestURI.endsWith("/feedback") || requestURI.endsWith("feedback.jsp");
        boolean isProfilePage = requestURI.endsWith("/profile.jsp");

        if (isPaymentPage && (session == null || session.getAttribute("user") == null)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login.jsp");
            return;
        }

        if (isHomePage || isLoginPage || isRegisterPage || isAuthServlet || isMoviesPage ||
                isBookingsPage || isFeedbackPage || isProfilePage ||
                (session != null && session.getAttribute("user") != null)) {
=======

        if (isHomePage || isLoginPage || isRegisterPage || isAuthServlet
                || (session != null && session.getAttribute("user") != null)) {
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login.jsp");
        }
    }
<<<<<<< HEAD

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
=======
>>>>>>> b8917e8b3efed89b7a22033da6c1b26eb2efe4ef
}
