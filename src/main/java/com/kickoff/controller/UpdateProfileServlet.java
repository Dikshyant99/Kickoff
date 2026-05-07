package com.kickoff.controller;

import com.kickoff.dao.UserDAO;
import com.kickoff.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(asyncSupported=true,urlPatterns={"/UpdateProfileServlet"})
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect(request.getContextPath() + "/Pages/Auth/login.jsp");
            return;
        }

        String sessionEmail = (String) session.getAttribute("email");

        // Detect which form was submitted
        String action = request.getParameter("action");

        if ("changePassword".equals(action)) {
            handleChangePassword(request, response, session, sessionEmail);
        } else {
            handleUpdateProfile(request, response, session, sessionEmail);
        }
    }

    // Handle Profile Update
    private void handleUpdateProfile(HttpServletRequest request,
                                     HttpServletResponse response,
                                     HttpSession session,
                                     String sessionEmail)
            throws ServletException, IOException {

        String firstName  = request.getParameter("firstName");
        String lastName   = request.getParameter("lastName");
        String newEmail   = request.getParameter("email");
        String phone      = request.getParameter("phone");
        String sport      = request.getParameter("sport");
        String skillLevel = request.getParameter("skillLevel");

        // Validation
        if (firstName == null || firstName.trim().isEmpty() ||
                newEmail   == null || newEmail.trim().isEmpty()) {

            request.setAttribute("error", "First name and email are required.");
            User user = userDAO.getUserByEmail(sessionEmail);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Pages/User/editprofile.jsp")
                    .forward(request, response);
            return;
        }

        // Fetch existing user
        User user = userDAO.getUserByEmail(sessionEmail);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Pages/Auth/login.jsp");
            return;
        }

        // Apply updates
        user.setFirstName(firstName.trim());
        user.setLastName(lastName   != null ? lastName.trim()   : "");
        user.setEmail(newEmail.trim());
        user.setPhone(phone         != null ? phone.trim()      : "");
        user.setSport(sport         != null ? sport.trim()      : "");
        user.setSkillLevel(skillLevel != null ? skillLevel.trim() : "");

        // Saving updated data
        boolean updated = userDAO.updateUser(user);

        if (updated) {
            if (!sessionEmail.equals(newEmail.trim())) {
                session.setAttribute("email", newEmail.trim());
            }
            session.setAttribute("successMsg", "Profile updated successfully.");
            response.sendRedirect(request.getContextPath() + "/ProfileServlet");
            return;
        } else {
            request.setAttribute("error", "Failed to update. Please try again.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Pages/User/editprofile.jsp")
                    .forward(request, response);
        }
    }

    // Handle Change Password
    private void handleChangePassword(HttpServletRequest request,
                                      HttpServletResponse response,
                                      HttpSession session,
                                      String sessionEmail)
            throws ServletException, IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword     = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Fetch user first so we can pass back to form on error
        User user = userDAO.getUserByEmail(sessionEmail);

        // Validation
        if (currentPassword == null || currentPassword.trim().isEmpty() ||
                newPassword      == null || newPassword.trim().isEmpty()     ||
                confirmPassword  == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("error", "All password fields are required.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Pages/User/editprofile.jsp")
                    .forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Pages/User/editprofile.jsp")
                    .forward(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Pages/User/editprofile.jsp")
                    .forward(request, response);
            return;
        }

        if (user == null || !user.getPassword().equals(currentPassword)) {
            request.setAttribute("error", "Current password is incorrect.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Pages/User/editprofile.jsp")
                    .forward(request, response);
            return;
        }

        // Update password
        user.setPassword(newPassword);
        boolean updated = userDAO.updateUser(user);

        if (updated) {
            session.setAttribute("successMsg", "Password changed successfully.");
            response.sendRedirect(request.getContextPath() + "/ProfileServlet");
            return;
        } else {
            request.setAttribute("error", "Failed to update password. Please try again.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Pages/User/editprofile.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/ProfileServlet");
    }
}