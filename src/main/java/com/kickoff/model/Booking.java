package com.kickoff.model;
/*
 * Model class representing
 * a booking record.
 *
 * Stores:
 * - Booking details
 * - User information
 * - Ground information
 * - Slot timing details
 */
public class Booking {
    /*
     * Booking table fields.
     */
    private int bookingId;
    private int userId;
    private int groundId;
    private int slotId;
    private String status;
    private double totalPrice;
    private String bookedAt;
    /*
     * Extra fields used for display
     * in booking pages and admin panel.
     */

    private String groundName;
    private String city;
    private String sportTypes;
    private String slotDate;
    private String startTime;
    private String endTime;
    private String userName;

    /*
     * Default constructor.
     */

    public Booking() {}
    /*
     * Returns booking ID.
     */

    public int getbookingId()
    { return bookingId; }
    /*
     * Sets booking ID.
     */
    public void setbookingId(int v)
    { this.bookingId = v; }
    /*
     * Returns user ID.
     */
    public int getUserId()
    { return userId; }
    /*
     * Sets user ID.
     */
    public void setUserId(int v)
    { this.userId = v; }

    /*
     * Returns ground ID.
     */
    public int getGroundId()
    { return groundId; }
    /*
     * Sets ground ID.
     */
    public void setGroundId(int v)
    { this.groundId = v; }

    /*
     * Returns slot ID.
     */
    public int getSlotId()
    { return slotId; }
    /*
     * Sets slot ID.
     */
    public void setSlotId(int v)
    { this.slotId = v; }
    /*
     * Returns booking status.
     */
    public String getStatus()
    { return status; }
    /*
     * Sets booking status.
     */
    public void setStatus(String v)
    { this.status = v; }
    /*
     * Returns total booking price.
     */
    public double getTotalPrice()
    { return totalPrice; }
    /*
     * Sets total booking price.
     */
    public void setTotalPrice(double v)
    { this.totalPrice = v; }
    /*
     * Returns booking date and time.
     */
    public String getBookedAt()
    { return bookedAt; }
    /*
     * Sets booking date and time.
     */
    public void setBookedAt(String v)
    { this.bookedAt = v; }
    /*
     * Returns ground name.
     */
    public String getGroundName()
    { return groundName; }

    /*
     * Sets ground name.
     */
    public void setGroundName(String v)
    { this.groundName = v; }
    /*
     * Returns ground city.
     */
    public String getCity()
    { return city; }

    /*
     * Sets ground city.
     */
    public void setCity(String v)
    { this.city = v; }
    /*
     * Returns supported sport types.
     */
    public String getSportTypes()
    { return sportTypes; }
    /*
     * Sets supported sport types.
     */
    public void setSportTypes(String v)
    { this.sportTypes = v; }

    /*
     * Returns slot date.
     */
    public String getSlotDate()
    { return slotDate; }
    /*
     * Sets slot date.
     */
    public void setSlotDate(String v)
    { this.slotDate = v; }
    /*
     * Returns slot start time.
     */
    public String getStartTime()
    { return startTime; }
    /*
     * Sets slot start time.
     */
    public void setStartTime(String v)
    { this.startTime = v; }
    /*
     * Returns slot end time.
     */
    public String getEndTime()
    { return endTime; }
    /*
     * Sets slot end time.
     */
    public void setEndTime(String v)
    { this.endTime = v; }
    /*
     * Returns user full name.
     */
    public String getUserName()
    { return userName; }

    /*
     * Sets user full name.
     */
    public void setUserName(String v)
    { this.userName = v; }
}