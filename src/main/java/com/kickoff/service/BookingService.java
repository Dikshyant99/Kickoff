package com.kickoff.service;

import com.kickoff.dao.BookingDAO;
import com.kickoff.model.Booking;
import java.sql.SQLException;
import java.util.List;
/*
 * Service layer for booking operations.
 *
 * Handles business logic such as:
 * - Creating bookings
 * - Cancelling bookings
 * - Approving/rejecting bookings
 * - Fetching booking data
 * - Managing slot availability
 */
public class BookingService {
    /*
     * DAO instance used for
     * database interactions.
     */
    private BookingDAO BookingDAO = new BookingDAO();

    /*
     * DAO instance used for
     * database interactions.
     */
    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        return BookingDAO.getBookingsByUser(userId);
    }

    /*
     * Retrieves all bookings (admin view).
     */
    public List<Booking> getAllBookings() throws SQLException {
        return BookingDAO.getAllBookings();
    }

    /*
     * Creates a new booking.
     *
     * Steps:
     * - Validate input
     * - Calculate price
     * - Insert booking
     * - Mark slot as booked
     */
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

    /*
     * Cancels a booking made by user.
     *
     * Also frees up the associated slot.
     */
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

    /*
     * Approves a booking (admin action).
     */
    public String approveBooking(int bookingId) throws SQLException {
        boolean updated = BookingDAO.updateBookingStatus(bookingId, "confirmed");
        return updated ? "success" : "Failed to approve booking.";
    }


    /*
     * Rejects a booking (admin action).
     *
     * Also frees the slot.
     */
    public String rejectBooking(int bookingId) throws SQLException {
        // Get slot id to free it up
        int slotId = BookingDAO.getSlotIdFromBooking(bookingId);
        boolean updated = BookingDAO.updateBookingStatus(bookingId, "cancelled");
        if (updated && slotId > 0) {
            BookingDAO.updateSlotStatus(slotId, "available");
        }
        return updated ? "success" : "Failed to reject booking.";
    }

    /*
     * Returns user ID linked to booking.
     *
     * Used for notifications.
     */
    public int getUserIdByBookingId(int bookingId) throws SQLException {
        return BookingDAO.getUserIdByBookingId(bookingId);
    }
}