package com.kickoff.controller;

import com.kickoff.dao.UserDAO;
import com.kickoff.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * EditProfileServlet is responsible for loading the user's profile
 * data into the edit profile page.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/User/editProfile"})
public class EditProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    // DAO used to fetch user details from database
    private UserDAO userDAO = new UserDAO();
    /**
     * Handles GET request to load the edit profile page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get logged-in user's email from session


        String email = (String) request.getSession().getAttribute("email");
// If no email in session, user is not logged in redirect to login
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
// Fetch user details from database using email
        User user = userDAO.getUserByEmail(email);
        // If user not found in DB, redirect to login (invalid session)
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
// Send user object to JSP to pre-fill the form fields
        request.setAttribute("user", user);
        // Forward to edit profile page
        request.getRequestDispatcher("/Pages/User/editProfile.jsp")
                .forward(request, response);
    }
    /**
     * POST request simply reloads the page (no update logic here)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
