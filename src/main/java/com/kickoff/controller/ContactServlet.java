package com.kickoff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
// Mapping this servlet to the "/contact" URL
@WebServlet(asyncSupported = true, urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {
    // Serial version UID for serialization
    private static final long serialVersionUID = 1L;
    // Handles GET requests from the client
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forwarding the request to Contact.jsp page
        request.getRequestDispatcher("/Pages/Root/Contact.jsp").forward(request, response);
    }
}
