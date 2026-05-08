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

        String email = (String) request.getSession().getAttribute("email");

        // if not logged in redirect to login
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = userDAO.getUserByEmail(email);

        // if user not found redirect to login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        //request attribute + forward
        request.setAttribute("user", user);
        request.getRequestDispatcher("/Pages/User/profile.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}