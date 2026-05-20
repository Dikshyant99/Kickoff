package com.kickoff.controller;

import com.kickoff.dao.UserDAO;
import com.kickoff.model.User;
import com.kickoff.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = {"/updateProfile", "/changePassword"})
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
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

    // Image Upload
    private void handleUpdateProfile(HttpServletRequest request,
                                     HttpServletResponse response,
                                     HttpSession session,
                                     String sessionEmail)
            throws ServletException, IOException {

        // Get form parameters
        String firstName  = request.getParameter("firstName");
        String lastName   = request.getParameter("lastName");
        String newEmail   = request.getParameter("email");
        String phone      = request.getParameter("phone");
        String sport      = request.getParameter("sport");
        String skillLevel = request.getParameter("skillLevel");

        // Validate required fields
        if (firstName == null || firstName.trim().isEmpty() ||
                newEmail == null || newEmail.trim().isEmpty()) {

            session.setAttribute("errorMsg", "First name and email are required.");
            User user = userDAO.getUserByEmail(sessionEmail);
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        // Get current user
        User user = userDAO.getUserByEmail(sessionEmail);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ===== HANDLE IMAGE UPLOAD (Same pattern as RegisterServlet) =====
        Part filePart = request.getPart("profilePic");
        String fileName = (filePart != null) ? filePart.getSubmittedFileName() : null;

        String imagePath = null;  // Will remain null if no new image uploaded
        if (fileName != null && !fileName.isEmpty()) {
            // Generate unique filename: userId_timestamp_ originalfilename
            int userId = (int) session.getAttribute("userId");
            String uniqueFileName = userId + "_" + System.currentTimeMillis() + "_" + fileName;

            imagePath = "uploads/" + uniqueFileName;
            String uploadDir = getServletContext().getRealPath("") + "/uploads/";
            java.io.File dir = new java.io.File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            filePart.write(uploadDir + uniqueFileName);
        }
        // ===== END IMAGE UPLOAD =====

        // Update user object with form data
        user.setFirstName(firstName.trim());
        user.setLastName(lastName != null ? lastName.trim() : "");
        user.setEmail(newEmail.trim());
        user.setPhone(phone != null ? phone.trim() : "");
        user.setSport(sport != null ? sport.trim() : "");
        user.setSkillLevel(skillLevel != null ? skillLevel.trim() : "");

        // If new image was uploaded, update the image path
        if (imagePath != null) {
            user.setImage(imagePath);
        }

        // Update in database
        boolean updated = userDAO.updateUser(user);

        if (updated) {
            // Update session email if changed
            if (!sessionEmail.equals(newEmail.trim())) {
                session.setAttribute("email", newEmail.trim());
            }
            // Update session user object
            session.setAttribute("user", user);
            session.setAttribute("successMsg", "Profile updated successfully.");
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            session.setAttribute("errorMsg", "Failed to update profile. Please try again.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
        }
    }

    // ============================================================================
    // HANDLE PASSWORD CHANGE (ORIGINAL)
    // ============================================================================
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

        // Check all fields are filled
        if (currentPassword == null || currentPassword.trim().isEmpty() ||
                newPassword     == null || newPassword.trim().isEmpty()     ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {

            session.setAttribute("errorMsg", "All password fields are required.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        // New passwords must match
        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("errorMsg", "New passwords do not match.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        // Minimum length check
        if (newPassword.length() < 6) {
            session.setAttribute("errorMsg", "Password must be at least 6 characters.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        // Verify current password against stored hash
        if (!PasswordUtil.verify(currentPassword, user.getPassword())) {
            session.setAttribute("errorMsg", "Current password is incorrect.");
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/editProfile");
            return;
        }

        // Hash new password and save
        String hashedNew = PasswordUtil.hash(newPassword);
        boolean updated = userDAO.updatePassword(user.getUserId(), hashedNew);

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