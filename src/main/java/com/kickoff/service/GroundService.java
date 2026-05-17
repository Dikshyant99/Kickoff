package com.kickoff.service;

import com.kickoff.dao.GroundDAO;
import com.kickoff.model.ground;
import com.kickoff.model.groundslot;
import java.sql.SQLException;
import java.util.List;
/*
 * Service layer for ground related operations.
 *
 * Handles:
 * - Fetching all grounds
 * - Filtering grounds
 * - Getting ground details
 * - Fetching available slots
 */
public class GroundService {
    /*
     * DAO instance used for
     * database operations.
     */
    private GroundDAO groundDAO = new GroundDAO();

    /*
     * Retrieves all active grounds.
     */
    public List<ground> getAllGrounds() throws SQLException {
        try {
            return groundDAO.getAllGrounds();
        } catch (SQLException e) {
            System.err.println("Error fetching all grounds: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /*
     * Retrieves grounds based on filters
     * like sport type and city.
     */
    public List<ground> getGroundsByFilter(String sport, String city) throws SQLException {
        try {
            return groundDAO.getGroundsByFilter(sport, city);
        } catch (SQLException e) {
            System.err.println("Error fetching grounds by filter: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /*
     * Retrieves a single ground by ID.
     */
    public ground getGroundById(int groundId) throws SQLException {
        try {
            return groundDAO.getGroundById(groundId);
        } catch (SQLException e) {
            System.err.println("Error fetching ground by ID " + groundId + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /*
     * Retrieves available slots for a ground.
     */
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