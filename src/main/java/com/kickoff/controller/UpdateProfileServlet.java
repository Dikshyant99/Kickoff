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

@WebServlet(asyncSupported = true, urlPatterns = {"/updateProfile", "/changePassword"})
public class UpdateProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String sessionEmail = (String) session.getAttribute("email");
        String path = request.getServletPath();

        switch (path) {
            case "/changePassword":
                handleChangePassword(request, response, session, sessionEmail);
                break;
            case "/updateProfile":
            default:
                handleUpdateProfile(request, response, session, sessionEmail);
                break;
        }
    }

    // handling the profile update
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

        if (firstName == null || firstName.trim().isEmpty() ||
                newEmail == null || newEmail.trim().isEmpty()) {

            session.setAttribute("errorMsg", "First name and email are required.");
            User user = userDAO.getUserByEmail(sessionEmail);
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        User user = userDAO.getUserByEmail(sessionEmail);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        user.setFirstName(firstName.trim());
        user.setLastName(lastName     != null ? lastName.trim()   : "");
        user.setEmail(newEmail.trim());
        user.setPhone(phone           != null ? phone.trim()      : "");
        user.setSport(sport           != null ? sport.trim()      : "");
        user.setSkillLevel(skillLevel != null ? skillLevel.trim() : "");

        boolean updated = userDAO.updateUser(user);

        if (updated) {
            if (!sessionEmail.equals(newEmail.trim())) {
                session.setAttribute("email", newEmail.trim());
            }
            session.setAttribute("successMsg", "Profile updated successfully.");
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            session.setAttribute("errorMsg", "Failed to update. Please try again.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
        }
    }

    // handling the password change
    private void handleChangePassword(HttpServletRequest request,
                                      HttpServletResponse response,
                                      HttpSession session,
                                      String sessionEmail)
            throws ServletException, IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword     = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        User user = userDAO.getUserByEmail(sessionEmail);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (currentPassword == null || currentPassword.trim().isEmpty() ||
                newPassword     == null || newPassword.trim().isEmpty()     ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {

            session.setAttribute("errorMsg", "All password fields are required.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("errorMsg", "New passwords do not match.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        if (newPassword.length() < 6) {
            session.setAttribute("errorMsg", "Password must be at least 6 characters.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        //  call getPassword()
        if (!user.getPassword().equals(currentPassword)) {
            session.setAttribute("errorMsg", "Current password is incorrect.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        user.setPassword(newPassword);
        boolean updated = userDAO.updateUser(user);

        if (updated) {
            session.setAttribute("successMsg", "Password changed successfully.");
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            session.setAttribute("errorMsg", "Failed to update password. Please try again.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}