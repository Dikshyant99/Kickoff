package com.kickoff.controller;

import com.kickoff.dao.UserDAO;
import com.kickoff.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get logged in user's email from session
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            // Not logged in - redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Fetch fresh user data from database
        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            // User not found in database
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Pass user object to JSP
        request.setAttribute("user", user);

        // Forward to profile page
        request.getRequestDispatcher("/Pages/User/profile.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}