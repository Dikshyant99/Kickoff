package com.kickoff.service;

import com.kickoff.dao.AdminDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/*
 * Service class responsible for
 * admin related operations.
 *
 * Handles:
 * - User management
 * - Ground management
 * - Booking management
 * - Dashboard statistics
 */
public class AdminService {
    /*
     * DAO object used for
     * database operations.
     */
    private AdminDAO dao = new AdminDAO();
    /*
     * Retrieves all active users.
     *
     * Returns:
     *      List<Map<String, Object>>
     */
    public List<Map<String, Object>> getAllUsers() throws SQLException {
        return dao.getUsers();
    }
    /*
     * Retrieves all grounds.
     *
     * Returns:
     *      List<Map<String, Object>>
     */
    public List<Map<String, Object>> getAllGrounds() throws SQLException {
        return dao.getGrounds();
    }

    /*
     * Retrieves all bookings.
     *
     * Returns:
     *      List<Map<String, Object>>
     */

    public List<Map<String, Object>> getAllBookings() throws SQLException {
        return dao.getBookings();
    }

    /*
     * Adds a new ground.
     */
    public void addGround(int ownerId, String name, String location,
                          String city, String sportTypes,
                          String price, String description) throws SQLException {
        dao.insertGround(ownerId, name, location, city, sportTypes, price, description);
    }
    /*
     * Permanently deletes user.
     */
    public void deleteUser(int id) throws SQLException {
        dao.delete("users", "user_id", id);
    }
    /*
     * Soft deletes user account.
     */
    public void softDeleteUser(int id) throws SQLException {
        dao.softDeleteUser(id);
    }
    /*
     * Restores soft deleted user.
     */
    public void restoreUser(int id) throws SQLException {
        dao.restoreUser(id);
    }
    /*
     * Deletes a ground record.
     */

    public void deleteGround(int id) throws SQLException {
        dao.delete("grounds", "ground_id", id);
    }

    /*
     * Updates booking status.
     */
    public void updateBookingStatus(int id, String status) throws SQLException {
        dao.updateBookingStatus(id, status);
    }
    /*
     * Returns total record count
     * for selected table.
     */
    public int getCount(String table) throws SQLException {
        return dao.getCount(table);
    }
    /*
     * Retrieves recently registered users.
     *
     * Returns:
     *      List<Map<String, Object>>
     */

    public List<Map<String, Object>> getRecentUsers() throws SQLException {
        return dao.getRecentUsers();
    }

    /*
     * Retrieves recent bookings.
     *
     * Returns:
     *      List<Map<String, Object>>
     */
    public List<Map<String, Object>> getRecentBookings() throws SQLException {
        return dao.getRecentBookings();
    }


}
