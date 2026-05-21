package com.kickoff.controller;

import com.kickoff.model.ground;
import com.kickoff.service.GroundService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
// Mapping this servlet to the "/grounds" URL
@WebServlet(asyncSupported = true, urlPatterns = {"/grounds"})
public class GroundServlet extends HttpServlet {
    // Serial version UID for serialization
    private static final long serialVersionUID = 1L;
    // Creating object for GroundService class
    private GroundService groundService;
    // Initializing GroundService when servlet starts
    @Override
    public void init() {
        groundService = new GroundService();
    }
    // Handles GET requests from client
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Getting sport and city values from request
        String sport = request.getParameter("sport");
        String city = request.getParameter("city");

        try {
            // List to store grounds data
            List<ground> grounds;
            // Checking if filters are applied
            if ((sport != null && !sport.isEmpty()) || (city != null && !city.isEmpty())) {
                // Fetch grounds based on sport or city filter
                grounds = groundService.getGroundsByFilter(sport, city);
            } else {
                // Fetch all grounds if no filter is applied
                grounds = groundService.getAllGrounds();
            }
// Sending grounds data to JSP page
            request.setAttribute("grounds", grounds);
        } catch (SQLException e) {
            // Handling database related errors
            e.printStackTrace();
            request.setAttribute("errorMsg", "Database error: " + e.getMessage());
        }
        // Handling other errors
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error: " + e.getMessage());
        }
        // Forwarding request to Grounds.jsp page
        request.getRequestDispatcher("/WEB-INF/Pages/Root/Grounds.jsp")
                .forward(request, response);
    }
}
