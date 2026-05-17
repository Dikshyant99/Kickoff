package com.kickoff.model;

import java.sql.Timestamp;
/*
 * Model class representing
 * a user notification.
 *
 * Stores:
 * - Notification details
 * - Notification type
 * - Reference information
 * - Read status and timestamp
 */
public class Notification {
    /*
     * Notification table fields.
     */
    private int notificationId;
    private int userId;
    private String type;
    private String message;
    private int referenceId;
    private String referenceType;
    private boolean isRead;
    private Timestamp createdAt;
    /*
     * Default constructor.
     */
    public Notification() {}
    /*
     * Returns notification ID.
     */

    public int getNotificationId() { return notificationId; }

    /*
     * Sets notification ID.
     */
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }
    /*
     * Returns user ID.
     */
    public int getUserId() { return userId; }
    /*
     * Sets user ID.
     */
    public void setUserId(int userId) { this.userId = userId; }
    /*
     * Returns notification type.
     */
    public String getType() { return type; }
    /*
     * Sets notification type.
     */
    public void setType(String type) { this.type = type; }
    /*
     * Returns notification message.
     */
    public String getMessage() { return message; }
    /*
     * Returns notification message.
     */
    public void setMessage(String message) { this.message = message; }

    /*
     * Gets reference ID.
     */
    public int getReferenceId() { return referenceId; }

    /*
     * Sets reference ID.
     */
    public void setReferenceId(int referenceId) { this.referenceId = referenceId; }
    /*
     * Returns reference type.
     */
    public String getReferenceType() { return referenceType; }
    /*
     * Returns reference type.
     */
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    /*
     * Returns notification read status.
     */

    public boolean isRead() { return isRead; }

    /*
     * Returns notification read status.
     */
    public void setRead(boolean read) { isRead = read; }

    public Timestamp getCreatedAt() { return createdAt; }
    /*
     * Sets notification creation time.
     */
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}