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
/**
 * AdminServlet handles all admin-side operations such as
 * managing users,grounds, bookings and dashboard data.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AdminService   adminService;
    private BookingService bookingService;
    /**
     * Initialize services when servlet starts
     */
    @Override
    public void init() {
        adminService   = new AdminService();
        bookingService = new BookingService();
    }
    /**
     * Handles all GET requests from admin panel
     * Used for viewing pages, listing data, and performing simple actions
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get action parameter from URL (e.g., /admin?action=listUsers)
        String action = request.getParameter("action");
        if (action == null) action = "dashboard";

        try {
            switch (action) {
// Load admin dashboard
                case "dashboard":
                    loadDashboard(request, response);
                    break;
// Display all users
                case "listUsers":
                    request.setAttribute("users", adminService.getAllUsers());
                    request.getRequestDispatcher("/Pages/Admin/users.jsp")
                            .forward(request, response);
                    break;
// Display all grounds
                case "listGrounds":
                    request.setAttribute("grounds", adminService.getAllGrounds());
                    request.getRequestDispatcher("/Pages/Admin/grounds.jsp")
                            .forward(request, response);
                    break;
                // Display all bookings
                case "listBookings":
                    request.setAttribute("bookings", bookingService.getAllBookings());
                    request.getRequestDispatcher("/Pages/Admin/bookings.jsp")
                            .forward(request, response);
                    break;
                // Soft delete (deactivate) a user
                case "deleteUser":
                    adminService.softDeleteUser(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "User deactivated successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listUsers");
                    break;
// Restore previously deactivated user
                case "restoreUser":
                    adminService.restoreUser(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "User restored successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listUsers");
                    break;
                // Permanently delete a ground
                case "deleteGround":
                    adminService.deleteGround(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "Ground deleted successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listGrounds");
                    break;

// Approve a booking request
                case "approveBooking":
                    bookingService.approveBooking(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "Booking approved successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listBookings");
                    break;
                // Reject a booking request
                case "rejectBooking":
                    bookingService.rejectBooking(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg",
                            "Booking rejected successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listBookings");
                    break;
                // Default dashboard
                default:
                    loadDashboard(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid ID: " + e.getMessage(), e);
        }
    }
    /**
     * Handles POST requests from admin panel
     * Mainly used for form submissions (e.g., adding a ground)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                // Add a new ground to the system
                case "addGround":
                    Object userIdObj = request.getSession().getAttribute("userId");
                    if (userIdObj == null) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }

                    int ownerId = Integer.parseInt(userIdObj.toString());
// Pass all ground details to service layer
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
                    response.sendRedirect(request.getContextPath() + "/admin?action=listGrounds");
                    break;
                // Fallback if no action matches
                default:
                    response.sendRedirect(request.getContextPath() + "/admin");
            }

        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number: " + e.getMessage(), e);
        }
    }
    /**
     * Loads dashboard statistics and recent activity
     */
    private void loadDashboard(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException, SQLException {
// Total counts for dashboard summary
        request.setAttribute("totalUsers",    adminService.getCount("users"));
        request.setAttribute("totalGrounds",  adminService.getCount("grounds"));
        request.setAttribute("totalBookings", adminService.getCount("bookings"));
// Recent activity lists
        request.setAttribute("recentUsers",    adminService.getRecentUsers());
        request.setAttribute("recentBookings", adminService.getRecentBookings());
// Forward to dashboard page
        request.getRequestDispatcher("/Pages/Admin/dashboard.jsp")
                .forward(request, response);
    }
}