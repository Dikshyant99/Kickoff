package com.kickoff.service;

import com.kickoff.dao.BookingDAO;
import com.kickoff.model.Booking;
import java.sql.SQLException;
import java.util.List;

public class BookingService {

    private BookingDAO BookingDAO = new BookingDAO();

    // Get all bookings for a user
    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        return BookingDAO.getBookingsByUser(userId);
    }

    // Get all bookings for admin
    public List<Booking> getAllBookings() throws SQLException {
        return BookingDAO.getAllBookings();
    }

    // Make a booking
    public String makeBooking(int userId, int groundId, int slotId) throws SQLException {
        if (groundId <= 0 || slotId <= 0) {
            return "Invalid ground or slot selected.";
        }

        // Calculate total price
        double totalPrice = BookingDAO.calculatePrice(groundId, slotId);

        // Create booking
        boolean booked = BookingDAO.createBooking(userId, groundId, slotId, totalPrice);
        if (!booked) return "Booking failed. Please try again.";

        // Mark slot as booked
        BookingDAO.updateSlotStatus(slotId, "booked");

        return "success";
    }

    // Cancel a booking
    public String cancelBooking(int bookingId, int userId) throws SQLException {
        // Get slot id to free it up
        int slotId = BookingDAO.getSlotIdFromBooking(bookingId);

        boolean cancelled = BookingDAO.cancelBooking(bookingId, userId);
        if (!cancelled) return "Failed to cancel booking.";

        // Free the slot back to available
        if (slotId > 0) {
            BookingDAO.updateSlotStatus(slotId, "available");
        }

        return "success";
    }

    // Approve booking (admin)
    public String approveBooking(int bookingId) throws SQLException {
        boolean updated = BookingDAO.updateBookingStatus(bookingId, "confirmed");
        return updated ? "success" : "Failed to approve booking.";
    }

    // Reject booking (admin)
    public String rejectBooking(int bookingId) throws SQLException {
        // Get slot id to free it up
        int slotId = BookingDAO.getSlotIdFromBooking(bookingId);
        boolean updated = BookingDAO.updateBookingStatus(bookingId, "cancelled");
        if (updated && slotId > 0) {
            BookingDAO.updateSlotStatus(slotId, "available");
        }
        return updated ? "success" : "Failed to reject booking.";
    }
}