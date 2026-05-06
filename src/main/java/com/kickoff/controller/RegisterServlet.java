package com.kickoff.controller;

import com.kickoff.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

@WebServlet(asyncSupported=true,urlPatterns={"/RegisterServlet"})
@MultipartConfig
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Pages/Auth/Register.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // IMAGE upload
        Part filePart = request.getPart("image");
        String fileName = (filePart != null) ? filePart.getSubmittedFileName() : null;

        String imagePath;
        if (fileName != null && !fileName.isEmpty()) {
            imagePath = "uploads/" + fileName;
            String uploadDir = getServletContext().getRealPath("") + "/uploads/";
            java.io.File dir = new java.io.File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            filePart.write(uploadDir + fileName);
        } else {
            imagePath = "uploads/default.png"; // fallback
        }

        // Read all form fields
        String firstName       = request.getParameter("firstName");
        String lastName        = request.getParameter("lastName");
        String email           = request.getParameter("email");
        String phone           = request.getParameter("phone");
        String sport           = request.getParameter("sport");
        String skillLevel      = request.getParameter("skillLevel");
        String password        = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Send to service for validation and saving
        String result = userService.registerUser(
                firstName, lastName, email, phone,
                sport, skillLevel, password, confirmPassword,
                imagePath   // <-- added
        );

        if (result.equals("success")) {
            // Registration worked - go to login with success message
            response.sendRedirect(request.getContextPath()
                    + "/Pages/Auth/login.jsp?registered=true");
        } else {
            // Validation failed: send error back to register page
            request.setAttribute("errorMsg", result);

            // Send form values back so fields stay filled
            request.setAttribute("firstName",  firstName);
            request.setAttribute("lastName",   lastName);
            request.setAttribute("email",      email);
            request.setAttribute("phone",      phone);
            request.setAttribute("sport",      sport);
            request.setAttribute("skillLevel", skillLevel);
            request.getRequestDispatcher("/Pages/Auth/Register.jsp")
                    .forward(request, response);
        }
    }
}