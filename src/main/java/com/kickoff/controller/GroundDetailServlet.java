package com.kickoff.controller;

import com.kickoff.model.ground;
import com.kickoff.service.GroundService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = {"/ground"})
public class GroundDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private GroundService groundService;

    @Override
    public void init() {
        groundService = new GroundService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/grounds");
            return;
        }

        try {
            int groundId = Integer.parseInt(idParam);
            ground ground = groundService.getGroundById(groundId);

            if (ground == null) {
                request.setAttribute("errorMsg", "Ground not found");
                request.getRequestDispatcher("/Pages/ErrorPage/404error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("ground", ground);
            request.getRequestDispatcher("/Pages/Root/ground-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/grounds");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error: " + e.getMessage());
            request.getRequestDispatcher("/Pages/ErrorPage/404error.jsp").forward(request, response);
        }
    }
}
