package com.kickoff.controller;

import com.kickoff.util.CookiesUtil;
import com.kickoff.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Destroy session
        SessionUtil.invalidateSession(request);
        // Delete rememberMe cookies
        CookiesUtil.deleteCookie(response, "userEmail");
        CookiesUtil.deleteCookie(response, "rememberMe");

        // Redirect to login page after logout
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}