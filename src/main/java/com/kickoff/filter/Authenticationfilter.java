package com.kickoff.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/*

Authentication filter for all protected routes.
Admin routes require role = admin.
User routes require being logged in.*/
@WebFilter({
        "/admin",
        "/profile",
        "/editProfile",
        "/updateProfile",
        "/changePassword",
        "/myBookings",
        "/bookingForm",
        "/confirmBooking",
        "/notifications"
})
public class Authenticationfilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String path = httpRequest.getServletPath();

        boolean isLoggedIn = (session != null && session.getAttribute("email") != null);
        boolean isAdmin    = (isLoggedIn && "admin".equals(session.getAttribute("role")));

        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // Admin-only routes
        if (path.equals("/admin") && !isAdmin) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}