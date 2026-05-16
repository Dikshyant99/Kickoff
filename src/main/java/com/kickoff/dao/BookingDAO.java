package com.kickoff.dao;

import com.kickoff.model.Booking;
import com.kickoff.util.DBUtil;
import java.sql.*;
import java.util.*;
/*
 * DAO class responsible for
 * Booking related database operations.
 *
 * Features:
 * - Retrieves user bookings
 * - Retrieves all bookings for admin
 * - Creates new bookings
 * - Updates slot and booking status
 * - Cancels bookings
 * - Retrieves slot and user details
 * - Calculates booking price
 */
public class BookingDAO {


    /*
     * Retrieves bookings for a specific user.
     *
     * Returns:
     *      List<Booking> - user bookings
     */
    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        List<Booking> list = new ArrayList<>();
        /*
         * SQL: Fetch booking details with
         * ground and slot information.
         */
        String sql = "SELECT b.booking_id, b.user_id, b.ground_id, b.slot_id, " +
                "b.status, b.total_price, b.booked_at, " +
                "g.name AS ground_name, g.city, g.sport_types, " +
                "gs.slot_date, gs.start_time, gs.end_time " +
                "FROM bookings b " +
                "JOIN grounds g       ON b.ground_id = g.ground_id " +
                "JOIN ground_slots gs ON b.slot_id   = gs.slot_id " +
                "WHERE b.user_id = ? " +
                "ORDER BY b.booked_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set user id parameter.
             */

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            /*
             * Store booking records.
             */
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        }
        return list;
    }

    /*
     * Retrieves all bookings for admin.
     *
     * Returns:
     *      List<Booking> - all booking records
     */
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> list = new ArrayList<>();
        /*
         * SQL: Fetch all booking details
         * including user information.
         */
        String sql = "SELECT b.booking_id, b.user_id, b.ground_id, b.slot_id, " +
                "b.status, b.total_price, b.booked_at, " +
                "g.name AS ground_name, g.city, g.sport_types, " +
                "gs.slot_date, gs.start_time, gs.end_time, " +
                "u.first_name, u.last_name " +
                "FROM bookings b " +
                "JOIN grounds g       ON b.ground_id = g.ground_id " +
                "JOIN ground_slots gs ON b.slot_id   = gs.slot_id " +
                "JOIN users u         ON b.user_id   = u.user_id " +
                "ORDER BY b.booked_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            /*
             * Read booking records.
             */

            while (rs.next()) {
                Booking b = mapBooking(rs);
                /*
                 * Build user full name.
                 */
                b.setUserName(rs.getString("first_name") + " " + rs.getString("last_name"));
                list.add(b);
            }
        }
        return list;
    }

    /*
     * Creates a new booking.
     *
     * Returns:
     *      boolean - true if booking created
     */
    public boolean createBooking(int userId, int groundId,
                                 int slotId, double totalPrice) throws SQLException {
        /*
         * SQL: Insert booking record.
         */
        String sql = "INSERT INTO bookings (user_id, ground_id, slot_id, status, total_price) " +
                "VALUES (?, ?, ?, 'pending', ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set booking values.
             */

            ps.setInt(1,    userId);
            ps.setInt(2,    groundId);
            ps.setInt(3,    slotId);
            ps.setDouble(4, totalPrice);
            /*
             * Execute insert query.
             */
            return ps.executeUpdate() > 0;
        }
    }

    /*
     * Updates slot availability status.
     *
     * Returns:
     *      boolean - true if update successful
     */
    public boolean updateSlotStatus(int slotId, String status) throws SQLException {
        /*
         * SQL: Update slot status.
         */
        String sql = "UPDATE ground_slots SET status = ? WHERE slot_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set update values.
             */

            ps.setString(1, status);
            ps.setInt(2, slotId);
            return ps.executeUpdate() > 0;
        }
    }


    /*
     * Cancels a booking.
     *
     * Returns:
     *      boolean - true if cancelled successfully
     */
    public boolean cancelBooking(int bookingId, int userId) throws SQLException {
        /*
         * SQL: Update booking status to cancelled.
         */
        String sql = "UPDATE bookings SET status = 'cancelled' " +
                "WHERE booking_id = ? AND user_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set booking and user ids.
             */

            ps.setInt(1, bookingId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /*
     * Updates booking status by admin.
     *
     * Returns:
     *      boolean - true if update successful
     */
    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        /*
         * SQL: Update booking status.
         */
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set update parameters.
             */

            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        }
    }

    /*
     * Retrieves slot id from booking.
     *
     * Returns:
     *      int - slot id
     */
    public int getSlotIdFromBooking(int bookingId) throws SQLException {
        /*
         * SQL: Fetch slot id using booking id.
         */
        String sql = "SELECT slot_id FROM bookings WHERE booking_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            /*
             * Return slot id if found.
             */
            if (rs.next()) return rs.getInt("slot_id");
        }
        return -1;
    }

    /*
     * Retrieves user id from booking.
     *
     * Used for notifications.
     *
     * Returns:
     *      int - user id
     */
    public int getUserIdByBookingId(int bookingId) throws SQLException {
        /*
         * SQL: Fetch user id from booking.
         */
        String sql = "SELECT user_id FROM bookings WHERE booking_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            /*
             * Return user id if found.
             */
            if (rs.next()) return rs.getInt("user_id");
        }
        return -1;
    }

    /*
     * Calculates total booking price.
     *
     * Price calculation:
     * - Fetch ground hourly price
     * - Calculate slot duration
     * - Multiply price by hours
     *
     * Returns:
     *      double - total booking amount
     */
    public double calculatePrice(int groundId, int slotId) throws SQLException {
        double price = 0;
        /*
         * SQL: Fetch ground hourly price.
         */

        String priceSql = "SELECT price_per_hour FROM grounds WHERE ground_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(priceSql)) {
            ps.setInt(1, groundId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) price = rs.getDouble("price_per_hour");
        }
        /*
         * SQL: Fetch slot duration.
         */

        String durationSql = "SELECT start_time, end_time FROM ground_slots WHERE slot_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(durationSql)) {
            ps.setInt(1, slotId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                /*
                 * Retrieve slot start and end time.
                 */
                Time start = rs.getTime("start_time");
                Time end   = rs.getTime("end_time");
                /*
                 * Calculate duration in hours.
                 */
                double hours = (end.getTime() - start.getTime()) / (1000.0 * 60 * 60);
                /*
                 * Calculate final price.
                 */
                price = price * hours;
            }
        }
        return price;
    }

    /*
     * Maps ResultSet data to Booking object.
     *
     * Returns:
     *      Booking - mapped booking object
     */
    private Booking mapBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        /*
         * Store booking details.
         */
        b.setbookingId(rs.getInt("booking_id"));
        b.setUserId(rs.getInt("user_id"));
        b.setGroundId(rs.getInt("ground_id"));
        b.setSlotId(rs.getInt("slot_id"));
        b.setStatus(rs.getString("status"));
        b.setTotalPrice(rs.getDouble("total_price"));
        b.setBookedAt(rs.getString("booked_at"));
        /*
         * Store ground details.
         */
        b.setGroundName(rs.getString("ground_name"));
        b.setCity(rs.getString("city"));
        b.setSportTypes(rs.getString("sport_types"));
        /*
         * Store slot details.
         */
        b.setSlotDate(rs.getString("slot_date"));
        b.setStartTime(rs.getString("start_time"));
        b.setEndTime(rs.getString("end_time"));
        return b;
    }
}