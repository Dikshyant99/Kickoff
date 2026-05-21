package com.kickoff.controller;

import com.kickoff.dao.ContactDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.kickoff.model.Contact;

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
        request.getRequestDispatcher("/WEB-INF/Pages/Root/Contact.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        Contact contact = new Contact(
                firstName,
                lastName,
                email,
                phone,
                subject,
                message
        );

        ContactDAO dao = new ContactDAO();

        boolean result = dao.saveContact(contact);

        if (result) {
            request.getSession().setAttribute("successMsg", "Message sent successfully!");
        } else {
            request.getSession().setAttribute("errorMsg", "Failed to send message!");
        }

        response.sendRedirect(request.getContextPath() + "/contact");
    }
}
