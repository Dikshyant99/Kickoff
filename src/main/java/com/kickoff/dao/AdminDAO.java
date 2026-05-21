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
/*
 * DAO class responsible for
 * Admin related database operations.
 *
 * Features:
 * - Retrieves users data
 * - Retrieves grounds data
 * - Retrieves booking details
 * - Inserts new grounds
 * - Deletes records
 * - Soft deletes and restores users
 * - Updates booking status
 * - Retrieves dashboard counts
 * - Fetches recent users and bookings
 */
public class AdminDAO {


    /*
     * Retrieves all active users.
     *
     * Excludes soft deleted users.
     *
     * Returns:
     *      List<Map<String, Object>> - users data
     */
    public List<Map<String, Object>> getUsers() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        /*
         * SQL: Fetch user details ordered by latest users.
         */
        String sql = "SELECT user_id, first_name, last_name, email, " +
                "phone, sport, skill_level, role, created_at " +
                "FROM users WHERE is_deleted = false ORDER BY created_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            /*
             * Iterate through result set.
             */
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                /*
                 * Store user details in map.
                 */
                row.put("userId",     rs.getInt("user_id"));
                row.put("firstName",  rs.getString("first_name"));
                row.put("lastName",   rs.getString("last_name"));
                row.put("email",      rs.getString("email"));
                row.put("phone",      rs.getString("phone"));
                row.put("sport",      rs.getString("sport"));
                row.put("skillLevel", rs.getString("skill_level"));
                row.put("role",       rs.getString("role"));
                row.put("createdAt",  rs.getString("created_at"));
                /*
                 * Add user data to list.
                 */
                list.add(row);
            }
        }
        return list;
    }

    /*
     * Retrieves all grounds details.
     *
     * Returns:
     *      List<Map<String, Object>> - grounds data
     */
    public List<Map<String, Object>> getGrounds() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        /*
         * SQL: Fetch grounds along with owner details.
         */
        String sql = "SELECT g.ground_id, g.name, g.location, g.city, " +
                "g.sport_types, g.price_per_hour, g.is_active, " +
                "u.first_name, u.last_name " +
                "FROM grounds g " +
                "JOIN users u ON g.owner_id = u.user_id " +
                "ORDER BY g.created_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            /*
            * Read grounds data.
            * */


            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                /*
                 * Store grounds information.
                 */
                row.put("groundId",     rs.getInt("ground_id"));
                row.put("name",         rs.getString("name"));
                row.put("location",     rs.getString("location"));
                row.put("city",         rs.getString("city"));
                row.put("sportTypes",   rs.getString("sport_types"));
                row.put("pricePerHour", rs.getString("price_per_hour"));
                row.put("isActive",     rs.getBoolean("is_active"));

                /*
                 * Build owner full name.
                 */
                row.put("ownerName",    rs.getString("first_name") + " " +
                        rs.getString("last_name"));

                list.add(row);
            }
        }
        return list;
    }


    /*
     * Retrieves all booking details.
     *
     * Returns:
     *      List<Map<String, Object>> - booking data
     */
    public List<Map<String, Object>> getBookings() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        /*
         * SQL: Fetch all bookings.
         */
        String sql = "SELECT * FROM bookings";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                /*
                 * Store booking details.
                 */
                row.put("bookingId", rs.getInt("booking_id"));
                row.put("status",    rs.getString("status"));
                list.add(row);
            }
        }
        return list;
    }

    /*
     * Inserts a new ground into database.
     */
    public void insertGround(int ownerId, String name, String location,
                             String city, String sportTypes,
                             String price, String description) throws SQLException {

        String sql = "INSERT INTO grounds (owner_id, name, location, city, sport_types, price_per_hour, description, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, true)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            /*
             * Set insert values.
             */

            ps.setInt(1, ownerId);
            ps.setString(2, name);
            ps.setString(3, location);
            ps.setString(4, city);
            ps.setString(5, sportTypes);
            ps.setString(6, price);
            ps.setString(7, description);
            /*
             * Execute insert query.
             */
            ps.executeUpdate();
        }
    }

    /*
     * Deletes a record from specified table.
     */
    public void delete(String table, String column, int id) throws SQLException {
        /*
         * SQL: Delete record using id.
         */
        String sql = "DELETE FROM " + table + " WHERE " + column + " = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set id parameter.
             */

            ps.setInt(1, id);
            /*
             * Execute delete query.
             */
            ps.executeUpdate();
        }
    }

    /*
     * Soft deletes a user.
     *
     * Updates is_deleted status to true.
     */
    public void softDeleteUser(int userId) throws SQLException {
        /*
         * SQL: Mark user as deleted.
         */
        String sql = "UPDATE users SET is_deleted = true WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    /*
     * Restores a soft deleted user.
     *
     * Updates is_deleted status to false.
     */
    public void restoreUser(int userId) throws SQLException {
        /*
         * SQL: Restore deleted user.
         */
        String sql = "UPDATE users SET is_deleted = false WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    /*
     * Updates booking status.
     */
    public void updateBookingStatus(int id, String status) throws SQLException {
        /*
         * SQL: Update booking status using booking id.
         */
        String sql = "UPDATE bookings SET status=? WHERE booking_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set update values.
             */

            ps.setString(1, status);
            ps.setInt(2, id);
            /*
             * Execute update query.
             */
            ps.executeUpdate();
        }
    }


    /*
     * Retrieves total record count.
     *
     * Excludes deleted users from users table.
     *
     * Returns:
     *      int - total count
     */
    public int getCount(String table) throws SQLException {
        /*
         * SQL: Count records from table.
         */
        String sql = table.equals("users")
                ? "SELECT COUNT(*) FROM users WHERE is_deleted = false"
                : "SELECT COUNT(*) FROM " + table;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            /*
             * Return count value.
             */

            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /*
     * Retrieves recently registered users.
     *
     * Excludes soft deleted users.
     *
     * Returns:
     *      List<Map<String, Object>> - recent users
     */
    public List<Map<String, Object>> getRecentUsers() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();

        /*
         * SQL: Fetch latest 5 active users.
         */
        String sql = "SELECT user_id, first_name, last_name, email, sport, role " +
                "FROM users WHERE is_deleted = false ORDER BY created_at DESC LIMIT 5";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                /*
                 * Store user information.
                 */
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

    /*
     * Retrieves recent booking details.
     *
     * Returns:
     *      List<Map<String, Object>> - recent bookings
     */
    public List<Map<String, Object>> getRecentBookings() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        /*
         * SQL: Fetch latest 5 bookings with user and ground details.
         */
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
            /*
             * Iterate through recent bookings.
             */

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                /*
                 * Store booking details.
                 */
                row.put("bookingId",  rs.getInt("booking_id"));
                row.put("status",     rs.getString("status"));
                row.put("totalPrice", rs.getString("total_price"));
                /*
                 * Build user full name.
                 */
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
// this is the end of admin dao