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

@WebServlet(asyncSupported = true, urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AdminService    adminService;
    private BookingService  bookingService;
    private NotificationDAO notifDAO;

    @Override
    public void init() {
        adminService   = new AdminService();
        bookingService = new BookingService();
        notifDAO       = new NotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "dashboard";

        try {
            switch (action) {

                case "dashboard":
                    loadDashboard(request, response);
                    break;

                case "listUsers":
                    request.setAttribute("users", adminService.getAllUsers());
                    request.getRequestDispatcher("/Pages/Admin/users.jsp")
                            .forward(request, response);
                    break;

                case "listGrounds":
                    request.setAttribute("grounds", adminService.getAllGrounds());
                    request.getRequestDispatcher("/Pages/Admin/grounds.jsp")
                            .forward(request, response);
                    break;

                case "listBookings":
                    request.setAttribute("bookings", bookingService.getAllBookings());
                    request.getRequestDispatcher("/Pages/Admin/bookings.jsp")
                            .forward(request, response);
                    break;

                case "deleteUser":
                    int deleteUserId = Integer.parseInt(request.getParameter("id"));
                    adminService.softDeleteUser(deleteUserId);
                    notifDAO.addNotification(
                            deleteUserId,
                            "account",
                            "Your account has been deactivated by the admin.",
                            deleteUserId,
                            "user"
                    );
                    request.getSession().setAttribute("successMsg", "User deactivated successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listUsers");
                    break;

                case "restoreUser":
                    int restoreUserId = Integer.parseInt(request.getParameter("id"));
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

                case "deleteGround":
                    adminService.deleteGround(
                            Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("successMsg", "Ground deleted successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listGrounds");
                    break;

                case "approveBooking":
                    int approveBookingId = Integer.parseInt(request.getParameter("id"));
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

                case "rejectBooking":
                    int rejectBookingId = Integer.parseInt(request.getParameter("id"));
                    bookingService.rejectBooking(rejectBookingId);
                    int rejectUserId = bookingService.getUserIdByBookingId(rejectBookingId);
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

        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {

                case "addGround":
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
                    request.getSession().setAttribute("successMsg", "Ground added successfully.");
                    response.sendRedirect(request.getContextPath() + "/admin?action=listGrounds");
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

    private void loadDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.setAttribute("totalUsers",    adminService.getCount("users"));
        request.setAttribute("totalGrounds",  adminService.getCount("grounds"));
        request.setAttribute("totalBookings", adminService.getCount("bookings"));
        request.setAttribute("recentUsers",    adminService.getRecentUsers());
        request.setAttribute("recentBookings", adminService.getRecentBookings());
        request.getRequestDispatcher("/Pages/Admin/dashboard.jsp")
                .forward(request, response);
    }
}