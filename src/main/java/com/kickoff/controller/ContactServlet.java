package com.kickoff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * ContactServlet handles requests for the Contact page.
 * It simply forwards the user to the Contact JSP view.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    /**
     * Handles GET requests to /contact
     * Forwards the request to the Contact.jsp page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward user to the Contact page view
        request.getRequestDispatcher("/Pages/Root/Contact.jsp").forward(request, response);
    }
}
