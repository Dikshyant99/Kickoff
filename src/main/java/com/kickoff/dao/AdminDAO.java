package com.kickoff.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kickoff.util.DBUtil;

public class AdminDAO {

    // Users
    public List<Map<String, Object>> getUsers() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT user_id, first_name, last_name, email, " +
                "phone, sport, skill_level, role, created_at " +
                "FROM users WHERE is_deleted = false ORDER BY created_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("userId",     rs.getInt("user_id"));
                row.put("firstName",  rs.getString("first_name"));
                row.put("lastName",   rs.getString("last_name"));
                row.put("email",      rs.getString("email"));
                row.put("phone",      rs.getString("phone"));
                row.put("sport",      rs.getString("sport"));
                row.put("skillLevel", rs.getString("skill_level"));
                row.put("role",       rs.getString("role"));
                row.put("createdAt",  rs.getString("created_at"));
                list.add(row);
            }
        }
        return list;
    }

    // Grounds
    public List<Map<String, Object>> getGrounds() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT g.ground_id, g.name, g.location, g.city, " +
                "g.sport_types, g.price_per_hour, g.is_active, " +
                "u.first_name, u.last_name " +
                "FROM grounds g " +
                "JOIN users u ON g.owner_id = u.user_id " +
                "ORDER BY g.created_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("groundId",     rs.getInt("ground_id"));
                row.put("name",         rs.getString("name"));
                row.put("location",     rs.getString("location"));
                row.put("city",         rs.getString("city"));
                row.put("sportTypes",   rs.getString("sport_types"));
                row.put("pricePerHour", rs.getString("price_per_hour"));
                row.put("isActive",     rs.getBoolean("is_active"));
                row.put("ownerName",    rs.getString("first_name") + " " +
                        rs.getString("last_name"));
                list.add(row);
            }
        }
        return list;
    }


    // Bookings
    public List<Map<String, Object>> getBookings() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("bookingId", rs.getInt("booking_id"));
                row.put("status",    rs.getString("status"));
                list.add(row);
            }
        }
        return list;
    }

    // Insert Ground
    public void insertGround(int ownerId, String name, String location,
                             String city, String sportTypes,
                             String price, String description) throws SQLException {

        String sql = "INSERT INTO grounds (owner_id, name, location, city, sport_types, price_per_hour, description, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, true)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ownerId);
            ps.setString(2, name);
            ps.setString(3, location);
            ps.setString(4, city);
            ps.setString(5, sportTypes);
            ps.setString(6, price);
            ps.setString(7, description);
            ps.executeUpdate();
        }
    }

    // Delete
    public void delete(String table, String column, int id) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE " + column + " = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Soft Delete User
    public void softDeleteUser(int userId) throws SQLException {
        String sql = "UPDATE users SET is_deleted = true WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    // Restore User
    public void restoreUser(int userId) throws SQLException {
        String sql = "UPDATE users SET is_deleted = false WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    // Update Booking Status
    public void updateBookingStatus(int id, String status) throws SQLException {
        String sql = "UPDATE bookings SET status=? WHERE booking_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // Filtering deleted users
    public int getCount(String table) throws SQLException {
        String sql = table.equals("users")
                ? "SELECT COUNT(*) FROM users WHERE is_deleted = false"
                : "SELECT COUNT(*) FROM " + table;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Recent Users, excludes soft-deleted users
    public List<Map<String, Object>> getRecentUsers() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT user_id, first_name, last_name, email, sport, role " +
                "FROM users WHERE is_deleted = false ORDER BY created_at DESC LIMIT 5";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("userId",    rs.getInt("user_id"));
                row.put("firstName", rs.getString("first_name"));
                row.put("lastName",  rs.getString("last_name"));
                row.put("email",     rs.getString("email"));
                row.put("sport",     rs.getString("sport"));
                row.put("role",      rs.getString("role"));
                list.add(row);
            }
        }
        return list;
    }

    // Recent Bookings
    public List<Map<String, Object>> getRecentBookings() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.status, b.total_price, " +
                "u.first_name, u.last_name, " +
                "g.name AS ground_name, " +
                "gs.slot_date, gs.start_time, gs.end_time " +
                "FROM bookings b " +
                "JOIN users u         ON b.user_id   = u.user_id " +
                "JOIN grounds g       ON b.ground_id = g.ground_id " +
                "JOIN ground_slots gs ON b.slot_id   = gs.slot_id " +
                "ORDER BY b.booked_at DESC LIMIT 5";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("bookingId",  rs.getInt("booking_id"));
                row.put("status",     rs.getString("status"));
                row.put("totalPrice", rs.getString("total_price"));
                row.put("userName",   rs.getString("first_name") + " " +
                        rs.getString("last_name"));
                row.put("groundName", rs.getString("ground_name"));
                row.put("slotDate",   rs.getString("slot_date"));
                row.put("startTime",  rs.getString("start_time"));
                row.put("endTime",    rs.getString("end_time"));
                list.add(row);
            }
        }
        return list;
    }


}