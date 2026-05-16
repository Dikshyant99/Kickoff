package com.kickoff.controller;

import com.kickoff.dao.NotificationDAO;
import com.kickoff.dao.UserDAO;
import com.kickoff.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {

    private NotificationDAO notifDAO = new NotificationDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // get email from session — same as ProfileServlet
        String email = (session != null)
                ? (String) session.getAttribute("email")
                : null;

        if (email == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // get user from email
        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int userId = user.getUserId();
        String action = req.getParameter("action");

        if ("markAllRead".equals(action)) {
            notifDAO.markAllRead(userId);
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }

        if ("markOneRead".equals(action)) {
            notifDAO.markOneRead(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }

        if ("deleteOne".equals(action)) {
            notifDAO.deleteOne(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}