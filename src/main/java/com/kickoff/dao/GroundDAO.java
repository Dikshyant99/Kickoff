package com.kickoff.dao;

import com.kickoff.model.ground;
import com.kickoff.model.groundslot;
import com.kickoff.util.DBUtil;
import java.sql.*;
import java.util.*;

public class GroundDAO {

    //GET ALL ACTIVE GROUNDS
    public List<ground> getAllGrounds() throws SQLException {
        return getGroundsByFilter(null, null);
    }

    //GET GROUNDS BY FILTER (sport and/or city)
    public List<ground> getGroundsByFilter(String sport, String city) throws SQLException {
        List<ground> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM grounds WHERE is_active = true");
        List<Object> params = new ArrayList<>();

        if (sport != null && !sport.isEmpty()) {
            sql.append(" AND sport_types LIKE ?");
            params.add("%" + sport + "%");
        }

        if (city != null && !city.isEmpty()) {
            sql.append(" AND city = ?");
            params.add(city);
        }

        sql.append(" ORDER BY created_at DESC");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapGround(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching grounds by filter");
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    //  GET GROUND BY ID
    public ground getGroundById(int groundId) throws SQLException {
        String sql = "SELECT * FROM grounds WHERE ground_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groundId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapGround(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ground by ID: " + groundId);
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    // GET AVAILABLE SLOTS FOR GROUND
    public List<groundslot> getAvailableSlots(int groundId) throws SQLException {
        List<groundslot> list = new ArrayList<>();
        String sql = "SELECT * FROM ground_slots " +
                "WHERE ground_id = ? AND status = 'available' " +
                "AND slot_date >= CURDATE() " +
                "ORDER BY slot_date, start_time";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groundId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapSlot(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching available slots for ground: " + groundId);
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    //  MAP ResultSet TO Ground
    private ground mapGround(ResultSet rs) throws SQLException {
        ground g = new ground();
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

    // MAP ResultSet TO GroundSlot
    private groundslot mapSlot(ResultSet rs) throws SQLException {
        groundslot s = new groundslot();
        s.setSlotId(rs.getInt("slot_id"));
        s.setGroundId(rs.getInt("ground_id"));
        s.setSlotDate(rs.getString("slot_date"));
        s.setStartTime(rs.getString("start_time"));
        s.setEndTime(rs.getString("end_time"));
        s.setStatus(rs.getString("status"));
        return s;
    }
}