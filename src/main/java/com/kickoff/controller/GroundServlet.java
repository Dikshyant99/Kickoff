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

@WebServlet(asyncSupported = true, urlPatterns = {"/grounds"})
public class GroundServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private GroundService groundService;

    @Override
    public void init() {
        groundService = new GroundService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<ground> grounds = groundService.getAllGrounds();
            request.setAttribute("grounds", grounds);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Database error: " + e.getMessage());
            throw new ServletException("Database error", e);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error: " + e.getMessage());
            throw new ServletException("Unexpected error", e);
        }

        request.getRequestDispatcher("/Pages/Root/Grounds.jsp")
                .forward(request, response);
    }
}