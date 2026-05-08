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

@WebServlet(asyncSupported = true, urlPatterns = {"/register"})
@MultipartConfig
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // changed: redirect instead of forward
        response.sendRedirect(request.getContextPath() + "/Pages/Auth/Register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // uploading the image
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
            imagePath = "uploads/default.png";
        }

        String firstName       = request.getParameter("firstName");
        String lastName        = request.getParameter("lastName");
        String email           = request.getParameter("email");
        String phone           = request.getParameter("phone");
        String sport           = request.getParameter("sport");
        String skillLevel      = request.getParameter("skillLevel");
        String password        = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        String result = userService.registerUser(
                firstName, lastName, email, phone,
                sport, skillLevel, password, confirmPassword,
                imagePath
        );

        if (result.equals("success")) {
            // registration worked, redirect to login with success param
            response.sendRedirect(request.getContextPath() + "/login?registered=true");
        } else {
            // changed: session instead of requestScope, redirect instead of forward
            request.getSession().setAttribute("errorMsg", result);
            request.getSession().setAttribute("firstName",  firstName);
            request.getSession().setAttribute("lastName",   lastName);
            request.getSession().setAttribute("email",      email);
            request.getSession().setAttribute("phone",      phone);
            request.getSession().setAttribute("sport",      sport);
            request.getSession().setAttribute("skillLevel", skillLevel);
            response.sendRedirect(request.getContextPath() + "/Pages/Auth/Register.jsp");
        }
    }
}