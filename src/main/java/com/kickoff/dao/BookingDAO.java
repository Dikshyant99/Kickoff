package com.kickoff.dao;

import com.kickoff.model.Booking;
import com.kickoff.util.DBUtil;
import java.sql.*;
import java.util.*;

public class BookingDAO {

    // Get Booking from users
    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        List<Booking> list = new ArrayList<>();
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

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        }
        return list;
    }

    // Get bookings from admin
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> list = new ArrayList<>();
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

            while (rs.next()) {
                Booking b = mapBooking(rs);
                b.setUserName(rs.getString("first_name") + " " + rs.getString("last_name"));
                list.add(b);
            }
        }
        return list;
    }

    // new bookings
    public boolean createBooking(int userId, int groundId,
                                 int slotId, double totalPrice) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, ground_id, slot_id, status, total_price) " +
                "VALUES (?, ?, ?, 'pending', ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1,    userId);
            ps.setInt(2,    groundId);
            ps.setInt(3,    slotId);
            ps.setDouble(4, totalPrice);
            return ps.executeUpdate() > 0;
        }
    }

    // update time
    public boolean updateSlotStatus(int slotId, String status) throws SQLException {
        String sql = "UPDATE ground_slots SET status = ? WHERE slot_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, slotId);
            return ps.executeUpdate() > 0;
        }
    }

    // cancel booking
    public boolean cancelBooking(int bookingId, int userId) throws SQLException {
        String sql = "UPDATE bookings SET status = 'cancelled' " +
                "WHERE booking_id = ? AND user_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    // update time for admin
    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        }
    }

    // get slotid from booking.
    public int getSlotIdFromBooking(int bookingId) throws SQLException {
        String sql = "SELECT slot_id FROM bookings WHERE booking_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("slot_id");
        }
        return -1;
    }

    // get price from slot
    public double calculatePrice(int groundId, int slotId) throws SQLException {
        double price = 0;

        String priceSql = "SELECT price_per_hour FROM grounds WHERE ground_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(priceSql)) {
            ps.setInt(1, groundId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) price = rs.getDouble("price_per_hour");
        }

        String durationSql = "SELECT start_time, end_time FROM ground_slots WHERE slot_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(durationSql)) {
            ps.setInt(1, slotId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Time start = rs.getTime("start_time");
                Time end   = rs.getTime("end_time");
                double hours = (end.getTime() - start.getTime()) / (1000.0 * 60 * 60);
                price = price * hours;
            }
        }
        return price;
    }

    // MAP ResultSet TO Booking
    private Booking mapBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setBookingId(rs.getInt("booking_id"));
        b.setUserId(rs.getInt("user_id"));
        b.setGroundId(rs.getInt("ground_id"));
        b.setSlotId(rs.getInt("slot_id"));
        b.setStatus(rs.getString("status"));
        b.setTotalPrice(rs.getDouble("total_price"));
        b.setBookedAt(rs.getString("booked_at"));
        b.setGroundName(rs.getString("ground_name"));
        b.setCity(rs.getString("city"));
        b.setSportTypes(rs.getString("sport_types"));
        b.setSlotDate(rs.getString("slot_date"));
        b.setStartTime(rs.getString("start_time"));
        b.setEndTime(rs.getString("end_time"));
        return b;
    }
}