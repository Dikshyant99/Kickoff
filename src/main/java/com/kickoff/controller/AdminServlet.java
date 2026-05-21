package com.kickoff.controller;

import com.kickoff.dao.NotificationDAO;
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
 * This Servlet is responsible for handling all admin-related actions
 * such as managing users, grounds, bookings, and dashboard data.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    // Service objects for business logic
    private AdminService    adminService;
    private BookingService  bookingService;
    // DAO for sending notifications
    private NotificationDAO notifDAO;

    @Override
    /**
     * Initializes service and DAO objects when servlet starts.
     */
    public void init() {
        adminService   = new AdminService();
        bookingService = new BookingService();
        notifDAO       = new NotificationDAO();
    }

    @Override

    /**
     * Handles all GET requests for admin actions.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get action parameter from URL
        String action = request.getParameter("action");
        if (action == null) action = "dashboard";

        try {
            switch (action) {
                case "dashboard":
                    loadDashboard(request, response);
                    break;

                // Display all users
                case "listUsers":
                    request.setAttribute("users", adminService.getAllUsers());
                    request.getRequestDispatcher("/WEB-INF/Pages/Admin/users.jsp")
                            .forward(request, response);
                    break;

                // Display all grounds
                case "listGrounds":
                    request.setAttribute("grounds", adminService.getAllGrounds());
                    request.getRequestDispatcher("/WEB-INF/Pages/Admin/grounds.jsp")
                            .forward(request, response);
                    break;

                //Display Bookings
                case "listBookings":
                    request.setAttribute("bookings", bookingService.getAllBookings());
                    request.getRequestDispatcher("/WEB-INF/Pages/Admin/bookings.jsp")
                            .forward(request, response);
                    break;
                //Soft Delete Users
                case "deleteUser":
                    // Get user ID from request
                    int deleteUserId = Integer.parseInt(request.getParameter("id"));
                    // Mark user as inactive
                    adminService.softDeleteUser(deleteUserId);
                    // Send notification to user
                    notifDAO.addNotification(
                            deleteUserId,
                            "account",
                            "Your account has been deactivated by the admin.",
                            deleteUserId,
                            "user"
                    );
                    // Store success message in session
                    request.getSession().setAttribute("successMsg", "User deactivated successfully.");
                    // Redirect back to users page
                    response.sendRedirect(request.getContextPath() + "/admin?action=listUsers");
                    break;

                // Restore user account
                case "restoreUser":
                    int restoreUserId = Integer.parseInt(request.getParameter("id"));

                    // Notify user about restoration
                    adminService.restoreUser(restoreUserId);
                    notifDAO.addNotification(
                            restoreUserId,
                            "account",
                            "Your account has been restored by the admin.",
                            restoreUserId,
                            "user"
                    );
                    request.getSession().setAttribute("successMsg", "User restored successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listUsers");
                    break;

                // Delete grounds
                case "deleteGround":
                    adminService.deleteGround(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg", "Ground deleted successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listGrounds");
                    break;
                // Approve a booking request
                case "approveBooking":
                    int approveBookingId = Integer.parseInt(request.getParameter("id"));
                    // Update booking status
                    bookingService.approveBooking(approveBookingId);
                    int approveUserId = bookingService.getUserIdByBookingId(approveBookingId);
                    notifDAO.addNotification(
                            approveUserId,
                            "booking",
                            "Your booking has been approved.",
                            approveBookingId,
                            "booking"
                    );
                    request.getSession().setAttribute("successMsg", "Booking approved successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listBookings");
                    break;
                //Reject booking
                case "rejectBooking":
                    int rejectBookingId = Integer.parseInt(request.getParameter("id"));
                    // Update booking status
                    bookingService.rejectBooking(rejectBookingId);
                    // Get associated user ID
                    int rejectUserId = bookingService.getUserIdByBookingId(rejectBookingId);

                    // Notify user
                    notifDAO.addNotification(
                            rejectUserId,
                            "booking",
                            "Your booking has been rejected.",
                            rejectBookingId,
                            "booking"
                    );
                    request.getSession().setAttribute("successMsg", "Booking rejected successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listBookings");
                    break;
                // Default action
                default:
                    loadDashboard(request, response);
            }

        } catch (SQLException e) {
            // Handle database-related error
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            // Handle invalid numeric input
            throw new ServletException("Invalid ID: " + e.getMessage(), e);
        }
    }
    /**
     * Handles POST requests for admin actions.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get action parameter
        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                // Add new ground
                case "addGround":
                    // Get logged-in user ID from session
                    Object userIdObj = request.getSession().getAttribute("userId");
                    // Prevent unauthorized access
                    if (userIdObj == null) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    int ownerId = Integer.parseInt(userIdObj.toString());
                    // Add ground details to database
                    adminService.addGround(
                            ownerId,
                            request.getParameter("name"),
                            request.getParameter("location"),
                            request.getParameter("city"),
                            request.getParameter("sportTypes"),
                            request.getParameter("pricePerHour"),
                            request.getParameter("description")
                    );
                    request.getSession().setAttribute("successMsg", "Ground added successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listGrounds");
                    break;
                // Default redirect
                default:
                    response.sendRedirect(request.getContextPath() + "/admin");
            }

        } catch (SQLException e) {
            // Handle database errors
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            //Handles invalid input error
            throw new ServletException("Invalid number: " + e.getMessage(), e);
        }
    }

    /**
     * Loads dashboard statistics and recent activities.
     */
    private void loadDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // Dashboard summary counts
        request.setAttribute("totalUsers",    adminService.getCount("users"));
        request.setAttribute("totalGrounds",  adminService.getCount("grounds"));
        request.setAttribute("totalBookings", adminService.getCount("bookings"));

        // Recent activity lists
        request.setAttribute("recentUsers",    adminService.getRecentUsers());
        request.setAttribute("recentBookings", adminService.getRecentBookings());

        // Forward to dashboard page
        request.getRequestDispatcher("/WEB-INF/Pages/Admin/dashboard.jsp")
                .forward(request, response);
    }
}