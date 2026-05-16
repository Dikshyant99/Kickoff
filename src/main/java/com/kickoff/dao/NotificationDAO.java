package com.kickoff.dao;

import com.kickoff.model.Notification;
import com.kickoff.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
 * DAO class responsible for
 * Notification related database operations.
 *
 * Features:
 * - Inserts notifications
 * - Retrieves unread notifications
 * - Retrieves all notifications
 * - Counts unread notifications
 * - Marks notifications as read
 * - Deletes notifications
 */
public class NotificationDAO {

    /*
     * Inserts a new notification.
     *
     * Returns:
     *      boolean - true if insert successful
     */
    public boolean addNotification(int userId, String type, String message,
                                   int referenceId, String referenceType) {
        /*
         * SQL: Insert notification details.
         */
        String sql = "INSERT INTO notifications (user_id, type, message, reference_id, reference_type) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set notification values.
             */

            ps.setInt(1, userId);
            ps.setString(2, type);
            ps.setString(3, message);
            ps.setInt(4, referenceId);
            ps.setString(5, referenceType);
            /*
             * Execute insert query.
             */
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            /*
             * Handle SQL exception.
             */
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Retrieves unread notifications.
     *
     * Used for notification badge count.
     *
     * Returns:
     *      List<Notification> - unread notifications
     */
    public List<Notification> getUnread(int userId) {
        List<Notification> list = new ArrayList<>();
        /*
         * SQL: Fetch unread notifications.
         */
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = 0 " +
                "ORDER BY created_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set user id parameter.
             */

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            /*
             * Store unread notifications.
             */
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            /*
             * Handle SQL errors.
             */
            e.printStackTrace();
        }
        return list;
    }

    /*
     * Retrieves all notifications for user.
     *
     * Returns:
     *      List<Notification> - all notifications
     */
    public List<Notification> getAll(int userId) {
        List<Notification> list = new ArrayList<>();
        /*
         * SQL: Fetch all notifications.
         */
        String sql = "SELECT * FROM notifications WHERE user_id = ? " +
                "ORDER BY created_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
            * Set user id parameter.
            */


            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            /*
             * Store notification records.
             */
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            /*
             * Handle SQL errors.
             */
            e.printStackTrace();
        }
        return list;
    }

    /*
     * Counts unread notifications.
     *
     * Returns:
     *      int - unread notification count
     */
    public int countUnread(int userId) {
        /*
         * SQL: Count unread notifications.
         */
        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND is_read = 0";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set user id parameter.
             */

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            /*
             * Return unread count.
             */
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            /*
             * Handle SQL exception.
             */
            e.printStackTrace();
        }
        return 0;
    }


    /*
     * Marks all notifications as read.
     *
     * Returns:
     *      boolean - true if update successful
     */
    public boolean markAllRead(int userId) {
        /*
         * SQL: Update all notifications to read.
         */
        String sql = "UPDATE notifications SET is_read = 1 WHERE user_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set user id parameter.
             */

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            /*
             * Handle SQL exception.
             */
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Marks a single notification as read.
     *
     * Returns:
     *      boolean - true if update successful
     */
    public boolean markOneRead(int notificationId) {

        /*
         * SQL: Update notification read status.
         */
        String sql = "UPDATE notifications SET is_read = 1 WHERE notification_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            /*
             * Set notification id.
             */

            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            /*
             * Handle SQL exception.
             */
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Deletes a notification.
     *
     * Returns:
     *      boolean - true if delete successful
     */
    public boolean deleteOne(int notificationId) {
        /*
         * SQL: Delete notification record.
         */
        String sql = "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            /*
             * Set notification id.
             */

            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            /*
             * Handle SQL exception.
             */
            e.printStackTrace();
            return false;
        }
    }


    /*
     * Maps ResultSet data to Notification object.
     *
     * Returns:
     *      Notification - mapped notification object
     */
    private Notification mapRow(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setNotificationId(rs.getInt("notification_id"));

        /*
         * Store notification details.
         */
        n.setUserId(rs.getInt("user_id"));
        n.setType(rs.getString("type"));
        n.setMessage(rs.getString("message"));
        n.setReferenceId(rs.getInt("reference_id"));
        n.setReferenceType(rs.getString("reference_type"));
        n.setRead(rs.getBoolean("is_read"));
        n.setCreatedAt(rs.getTimestamp("created_at"));
        return n;
    }
}