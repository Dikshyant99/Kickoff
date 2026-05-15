package com.kickoff.controller;

import com.kickoff.model.Booking;
import com.kickoff.model.ground;
import com.kickoff.model.groundslot;
import com.kickoff.service.BookingService;
import com.kickoff.service.GroundService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
/**
 * BookingServlet handles all user booking operations:
 * - Viewing booking form
 * - Making a booking
 * - Viewing user's bookings
 * - Cancelling a booking
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/myBookings", "/bookingForm", "/cancelBooking"})
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    // Services for booking and ground operations
    private BookingService bookingService;
    private GroundService  groundService;
    /**
     * Initialize service classes when servlet starts
     */
    @Override
    public void init() {
        bookingService = new BookingService();
        groundService  = new GroundService();
    }
    /**
     * Handles all GET requests based on URL path
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Determine which URL was called
        String path = request.getServletPath();

        try {
            switch (path) {
                // Show booking form for a specific ground
                case "/bookingForm":
                    showBookingForm(request, response);
                    break;
                // Cancel an existing booking
                case "/cancelBooking":
                    cancelBooking(request, response);
                    break;
                // Default: show user's bookings
                case "/myBookings":
                default:
                    showMyBookings(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
    /**
     * Handles POST requests (mainly for making bookings)
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // If action is "book", process booking
            if ("book".equals(request.getParameter("action"))) {
                makeBooking(request, response);
            } else {
                showMyBookings(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
    /**
     * Display booking form with available slots for selected ground
     */
    private void showBookingForm(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String groundId = request.getParameter("groundId");
        // If no ground ID, redirect to grounds listing
        if (groundId == null) {
            response.sendRedirect(request.getContextPath() + "/grounds");
            return;
        }
// Fetch ground details and available slots
        ground ground = groundService.getGroundById(Integer.parseInt(groundId));
        List<groundslot> slots = groundService.getAvailableSlots(Integer.parseInt(groundId));

        // Pass data to JSP
        request.setAttribute("ground", ground);
        request.setAttribute("slots", slots);
        // Forward to booking form page
        request.getRequestDispatcher("/Pages/User/booking-form.jsp")
                .forward(request, response);
    }

    /**
     * Process a booking request from user
     */
    private void makeBooking(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession(false);
        // Get required data from session and form
        int userId   = (int) session.getAttribute("userId");
        int groundId = Integer.parseInt(request.getParameter("groundId"));
        int slotId   = Integer.parseInt(request.getParameter("slotId"));
// Call service to create booking
        String result = bookingService.makeBooking(userId, groundId, slotId);
        // Show message based on result
        if (result.equals("success")) {
            session.setAttribute("successMsg",
                    "Booking submitted! Waiting for admin approval.");
        } else {
            session.setAttribute("errorMsg", result);
        }
        // Redirect to user's bookings page
        response.sendRedirect(request.getContextPath() + "/myBookings");
    }

    /**
     * Show all bookings made by the logged-in user
     */
    private void showMyBookings(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
// Fetch bookings from database
        List<Booking> bookings = bookingService.getBookingsByUser(userId);

        // Send data to JSP
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/Pages/User/bookings.jsp")
                .forward(request, response);
    }

    /**
     * Cancel an existing booking made by the user
     */
    private void cancelBooking(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession(false);
        int userId    = (int) session.getAttribute("userId");
        int bookingId = Integer.parseInt(request.getParameter("id"));
// Attempt cancellation
        String result = bookingService.cancelBooking(bookingId, userId);
        // Set success or error message
        if (result.equals("success")) {
            session.setAttribute("successMsg", "Booking cancelled successfully.");
        } else {
            session.setAttribute("errorMsg", result);
        }

        // Redirect back to bookings page
        response.sendRedirect(request.getContextPath() + "/myBookings");
    }
}