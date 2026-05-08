package com.kickoff.controller;

import com.kickoff.dao.UserDAO;
import com.kickoff.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = {"/editProfile"})
public class EditProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = (String) request.getSession().getAttribute("email");

        // if not logged in, redirect to login
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/Pages/Auth/login.jsp");
            return;
        }

        User user = userDAO.getUserByEmail(email);

        // if user not found, redirect to login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Pages/Auth/login.jsp");
            return;
        }

        // changed: session instead of requestScope, redirect instead of forward
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/Pages/User/editprofile.jsp");
    }
}