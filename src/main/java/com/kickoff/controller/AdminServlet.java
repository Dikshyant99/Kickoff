package com.kickoff.controller;

import com.kickoff.service.AdminService;
import com.kickoff.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(asyncSupported = true, urlPatterns = {
        "/admin",
        "/listUsers", "/deleteUser", "/restoreUser",
        "/listGrounds", "/addGround", "/deleteGround",
        "/listTeams", "/deleteTeam",
        "/listBookings", "/approveBooking", "/rejectBooking"
})
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AdminService   adminService;
    private BookingService bookingService;

    @Override
    public void init() {
        adminService   = new AdminService();
        bookingService = new BookingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        try {
            switch (path) {

                case "/admin":
                    loadDashboard(request, response);
                    break;

                case "/listUsers":
                    request.setAttribute("users", adminService.getAllUsers());
                    request.getRequestDispatcher("/Pages/Admin/users.jsp")
                            .forward(request, response);
                    break;

                case "/listGrounds":
                    request.setAttribute("grounds", adminService.getAllGrounds());
                    request.getRequestDispatcher("/Pages/Admin/grounds.jsp")
                            .forward(request, response);
                    break;

                case "/listTeams":
                    request.setAttribute("teams", adminService.getAllTeams());
                    request.getRequestDispatcher("/Pages/Admin/teams.jsp")
                            .forward(request, response);
                    break;

                case "/listBookings":
                    request.setAttribute("bookings", bookingService.getAllBookings());
                    request.getRequestDispatcher("/Pages/Admin/bookings.jsp")
                            .forward(request, response);
                    break;

                // SOFT DELETE USER
                case "/deleteUser":
                    adminService.softDeleteUser(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "User deactivated successfully.");
                    response.sendRedirect(request.getContextPath() + "/listUsers");
                    break;

                // RESTORE USER
                case "/restoreUser":
                    adminService.restoreUser(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "User restored successfully.");
                    response.sendRedirect(request.getContextPath() + "/listUsers");
                    break;

                case "/deleteGround":
                    adminService.deleteGround(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "Ground deleted successfully.");
                    response.sendRedirect(request.getContextPath() + "/listGrounds");
                    break;

                case "/deleteTeam":
                    adminService.deleteTeam(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "Team deleted successfully.");
                    response.sendRedirect(request.getContextPath() + "/listTeams");
                    break;

                case "/approveBooking":
                    bookingService.approveBooking(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "Booking approved successfully.");
                    response.sendRedirect(request.getContextPath() + "/listBookings");
                    break;

                case "/rejectBooking":
                    bookingService.rejectBooking(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "Booking rejected successfully.");
                    response.sendRedirect(request.getContextPath() + "/listBookings");
                    break;

                default:
                    loadDashboard(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid ID: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        try {
            switch (path) {

                case "/addGround":
                    Object userIdObj = request.getSession().getAttribute("userId");
                    if (userIdObj == null) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }

                    int ownerId = Integer.parseInt(userIdObj.toString());

                    adminService.addGround(
                            ownerId,
                            request.getParameter("name"),
                            request.getParameter("location"),
                            request.getParameter("city"),
                            request.getParameter("sportTypes"),
                            request.getParameter("pricePerHour"),
                            request.getParameter("description")
                    );

                    request.getSession().setAttribute("successMsg",
                            "Ground added successfully.");
                    response.sendRedirect(request.getContextPath() + "/listGrounds");
                    break;

                default:
                    response.sendRedirect(request.getContextPath() + "/admin");
            }

        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number: " + e.getMessage(), e);
        }
    }

    // Loading the dashboard
    private void loadDashboard(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        request.setAttribute("totalUsers",    adminService.getCount("users"));
        request.setAttribute("totalTeams",    adminService.getCount("teams"));
        request.setAttribute("totalGrounds",  adminService.getCount("grounds"));
        request.setAttribute("totalBookings", adminService.getCount("bookings"));

        request.setAttribute("recentUsers",    adminService.getRecentUsers());
        request.setAttribute("recentBookings", adminService.getRecentBookings());
        request.setAttribute("recentTeams",    adminService.getRecentTeams());

        request.getRequestDispatcher("/Pages/Admin/dashboard.jsp")
                .forward(request, response);
    }
}