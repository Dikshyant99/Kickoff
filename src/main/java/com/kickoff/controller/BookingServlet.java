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

@WebServlet(asyncSupported = true, urlPatterns = {
        "/myBookings",
        "/bookingForm",
        "/makeBooking",
        "/cancelBooking"
})
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BookingService bookingService;
    private GroundService  groundService;

    @Override
    public void init() {
        bookingService = new BookingService();
        groundService  = new GroundService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        try {
            switch (path) {

                case "/bookingForm":
                    showBookingForm(request, response);
                    break;

                case "/cancelBooking":
                    cancelBooking(request, response);
                    break;

                case "/myBookings":
                default:
                    showMyBookings(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        try {
            switch (path) {

                case "/makeBooking":
                    makeBooking(request, response);
                    break;

                default:
                    showMyBookings(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    // ===== SHOW BOOKING FORM =====
    private void showBookingForm(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String groundId = request.getParameter("groundId");
        if (groundId == null) {
            response.sendRedirect(request.getContextPath() + "/listGrounds");
            return;
        }

        ground ground = groundService.getGroundById(Integer.parseInt(groundId));
        List<groundslot> slots = groundService.getAvailableSlots(Integer.parseInt(groundId));

        request.setAttribute("ground", ground);
        request.setAttribute("slots", slots);
        request.getRequestDispatcher("/Pages/User/booking-form.jsp")
                .forward(request, response);
    }

    // ===== MAKE NEW BOOKING =====
    private void makeBooking(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession(false);
        int userId   = (int) session.getAttribute("userId");
        int groundId = Integer.parseInt(request.getParameter("groundId"));
        int slotId   = Integer.parseInt(request.getParameter("slotId"));

        String result = bookingService.makeBooking(userId, groundId, slotId);

        if (result.equals("success")) {
            session.setAttribute("successMsg",
                    "Booking submitted! Waiting for admin approval.");
        } else {
            session.setAttribute("errorMsg", result);
        }

        response.sendRedirect(request.getContextPath() + "/myBookings");
    }

    // ===== SHOW MY BOOKINGS =====
    private void showMyBookings(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/Pages/User/bookings.jsp")
                .forward(request, response);
    }

    // ===== CANCEL BOOKING =====
    private void cancelBooking(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession(false);
        int userId    = (int) session.getAttribute("userId");
        int bookingId = Integer.parseInt(request.getParameter("id"));

        String result = bookingService.cancelBooking(bookingId, userId);

        if (result.equals("success")) {
            session.setAttribute("successMsg", "Booking cancelled successfully.");
        } else {
            session.setAttribute("errorMsg", result);
        }

        response.sendRedirect(request.getContextPath() + "/myBookings");
    }
}