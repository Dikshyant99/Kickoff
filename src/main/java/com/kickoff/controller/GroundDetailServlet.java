package com.kickoff.controller;

import com.kickoff.model.ground;
import com.kickoff.service.GroundService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * GroundDetailServlet is responsible for showing detailed
 * information about a specific ground selected by the user.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/ground"})
public class GroundDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    // Service used to fetch ground data from database
    private GroundService groundService;
    /**
     * Initialize GroundService when servlet starts
     */
    @Override
    public void init() {
        groundService = new GroundService();
    }
    /**
     * Handles GET request to display ground details
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get ground ID from request parameter
        String idParam = request.getParameter("id");
// If ID is missing, redirect to grounds listing page
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/grounds");
            return;
        }

        try {
            // Convert ID to integer
            int groundId = Integer.parseInt(idParam);
            // Fetch ground details from database
            ground ground = groundService.getGroundById(groundId);
            // If no ground found, show 404 error page
            if (ground == null) {
                request.setAttribute("errorMsg", "Ground not found");
                request.getRequestDispatcher("/Pages/ErrorPage/404error.jsp").forward(request, response);
                return;
            }
            // Send ground details to JSP page
            request.setAttribute("ground", ground);
            request.getRequestDispatcher("/Pages/Root/ground-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // If ID is invalid number, redirect to grounds page
            response.sendRedirect(request.getContextPath() + "/grounds");
        } catch (Exception e) {
            // Handle any unexpected errors
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error: " + e.getMessage());
            request.getRequestDispatcher("/Pages/ErrorPage/404error.jsp").forward(request, response);
        }
    }
}
