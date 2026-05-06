package com.kickoff.service;

import com.kickoff.dao.GroundDAO;
import com.kickoff.model.ground;
import com.kickoff.model.groundslot;
import java.sql.SQLException;
import java.util.List;

public class GroundService {

    private GroundDAO groundDAO = new GroundDAO();

    // Get all active grounds
    public List<ground> getAllGrounds() throws SQLException {
        try {
            return groundDAO.getAllGrounds();
        } catch (SQLException e) {
            System.err.println("Error fetching all grounds: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Get ground by ID
    public ground getGroundById(int groundId) throws SQLException {
        try {
            return groundDAO.getGroundById(groundId);
        } catch (SQLException e) {
            System.err.println("Error fetching ground by ID " + groundId + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Get available slots for a ground
    public List<groundslot> getAvailableSlots(int groundId) throws SQLException {
        try {
            return groundDAO.getAvailableSlots(groundId);
        } catch (SQLException e) {
            System.err.println("Error fetching available slots for ground " + groundId + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}