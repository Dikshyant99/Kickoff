package com.kickoff.dao;

import com.kickoff.model.ground;
import com.kickoff.model.groundslot;
import com.kickoff.util.DBUtil;
import java.sql.*;
import java.util.*;
/*
 * DAO class responsible for
 * Ground related database operations.
 *
 * Features:
 * - Retrieves all active grounds
 * - Filters grounds by sport and city
 * - Retrieves ground details by id
 * - Fetches available slots for grounds
 * - Maps ResultSet data to model objects
 */
public class GroundDAO {

    /*
     * Retrieves all active grounds.
     *
     * Returns:
     *      List<ground> - all active grounds
     */
    public List<ground> getAllGrounds() throws SQLException {
        /*
         * Calls filter method without filters.
         */
        return getGroundsByFilter(null, null);

    }

    /*
     * Retrieves grounds based on filters.
     *
     * Filters:
     * - Sport type
     * - City
     *
     * Returns:
     *      List<ground> - filtered grounds list
     */
    public List<ground> getGroundsByFilter(String sport, String city) throws SQLException {
        List<ground> list = new ArrayList<>();

        /*
         * Base SQL query for active grounds.
         */

        StringBuilder sql = new StringBuilder("SELECT * FROM grounds WHERE is_active = true");
        List<Object> params = new ArrayList<>();
        /*
         * Add sport filter if provided.
         */

        if (sport != null && !sport.isEmpty()) {
            sql.append(" AND sport_types LIKE ?");
            params.add("%" + sport + "%");
        }

        /*
         * Add city filter if provided.
         */

        if (city != null && !city.isEmpty()) {
            sql.append(" AND city = ?");
            params.add(city);
        }
        /*
         * Sort grounds by latest created.
         */

        sql.append(" ORDER BY created_at DESC");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            /*
             * Set query parameters dynamically.
             */

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                /*
                 * Store ground records.
                 */
                while (rs.next()) {
                    list.add(mapGround(rs));
                }
            }
        } catch (SQLException e) {
            /*
             * Print SQL error details.
             */
            System.err.println("Error fetching grounds by filter");
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    /*
     * Retrieves ground details using ground id.
     *
     * Returns:
     *      ground - ground object
     */
    public ground getGroundById(int groundId) throws SQLException {
        /*
         * SQL: Fetch ground by id.
         */
        String sql = "SELECT * FROM grounds WHERE ground_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set ground id parameter.
             */

            ps.setInt(1, groundId);
            try (ResultSet rs = ps.executeQuery()) {
                /*
                 * Return ground if found.
                 */
                if (rs.next()) return mapGround(rs);
            }
        } catch (SQLException e) {
            /*
             * Handle SQL exception.
             */
            System.err.println("Error fetching ground by ID: " + groundId);
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            throw e;
        }
        return null;
    }


    /*
     * Retrieves available slots for a ground.
     *
     * Conditions:
     * - Slot must be available
     * - Slot date must be today or future
     *
     * Returns:
     *      List<groundslot> - available slots
     */
    public List<groundslot> getAvailableSlots(int groundId) throws SQLException {
        List<groundslot> list = new ArrayList<>();
        /*
         * SQL: Fetch available ground slots.
         */
        String sql = "SELECT * FROM ground_slots " +
                "WHERE ground_id = ? AND status = 'available' " +
                "ORDER BY slot_date, start_time";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set ground id parameter.
             */

            ps.setInt(1, groundId);
            try (ResultSet rs = ps.executeQuery()) {
                /*
                 * Store slot records.
                 */
                while (rs.next()) {
                    list.add(mapSlot(rs));
                }
            }
        } catch (SQLException e) {
            /*
             * Handle SQL errors.
             */
            System.err.println("Error fetching available slots for ground: " + groundId);
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    /*
     * Maps ResultSet data to ground object.
     *
     * Returns:
     *      ground - mapped ground object
     */
    private ground mapGround(ResultSet rs) throws SQLException {
        ground g = new ground();
        /*
         * Store ground details.
         */
        g.setGroundId(rs.getInt("ground_id"));
        g.setOwnerId(rs.getInt("owner_id"));
        g.setName(rs.getString("name"));
        g.setLocation(rs.getString("location"));
        g.setCity(rs.getString("city"));
        g.setSportTypes(rs.getString("sport_types"));
        g.setPricePerHour(rs.getDouble("price_per_hour"));
        g.setDescription(rs.getString("description"));
        g.setImageUrl(rs.getString("image_url"));
        g.setActive(rs.getBoolean("is_active"));
        g.setCreatedAt(rs.getString("created_at"));
        return g;
    }

    /*
     * Maps ResultSet data to groundslot object.
     *
     * Returns:
     *      groundslot - mapped slot object
     */
    private groundslot mapSlot(ResultSet rs) throws SQLException {
        groundslot s = new groundslot();
        /*
         * Store slot details.
         */
        s.setSlotId(rs.getInt("slot_id"));
        s.setGroundId(rs.getInt("ground_id"));
        s.setSlotDate(rs.getString("slot_date"));
        s.setStartTime(rs.getString("start_time"));
        s.setEndTime(rs.getString("end_time"));
        s.setStatus(rs.getString("status"));
        return s;
    }
}