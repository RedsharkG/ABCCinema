package com.cinema.booking.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class AuthenticationFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    HttpSession session = httpRequest.getSession(false);
    
    String requestURI = httpRequest.getRequestURI();
    boolean isLoginPage = requestURI.endsWith("login.jsp");
    boolean isAuthServlet = requestURI.endsWith("/auth");
    boolean isStaticResource = requestURI.contains("/css/") || requestURI.contains("/js/") || requestURI.contains("/images/");
    
    if (isLoginPage || isAuthServlet || isStaticResource || (session != null && session.getAttribute("admin") != null)) {
        chain.doFilter(request, response);
    } else {
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/login.jsp");
    }
}
}
