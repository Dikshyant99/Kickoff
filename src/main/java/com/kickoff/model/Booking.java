package com.kickoff.model;

public class Booking {
    private int bookingId;
    private int userId;
    private int groundId;
    private int slotId;
    private String status;
    private double totalPrice;
    private String bookedAt;

    // Extra fields for display
    private String groundName;
    private String city;
    private String sportTypes;
    private String slotDate;
    private String startTime;
    private String endTime;
    private String userName;

    public Booking() {}

    public int getbookingId()
    { return bookingId; }
    public void setbookingId(int v)
    { this.bookingId = v; }
    public int getUserId()
    { return userId; }
    public void setUserId(int v)
    { this.userId = v; }
    public int getGroundId()
    { return groundId; }
    public void setGroundId(int v)
    { this.groundId = v; }
    public int getSlotId()
    { return slotId; }
    public void setSlotId(int v)
    { this.slotId = v; }
    public String getStatus()
    { return status; }
    public void setStatus(String v)
    { this.status = v; }
    public double getTotalPrice()
    { return totalPrice; }
    public void setTotalPrice(double v)
    { this.totalPrice = v; }
    public String getBookedAt()
    { return bookedAt; }
    public void setBookedAt(String v)
    { this.bookedAt = v; }
    public String getGroundName()
    { return groundName; }
    public void setGroundName(String v)
    { this.groundName = v; }
    public String getCity()
    { return city; }
    public void setCity(String v)
    { this.city = v; }
    public String getSportTypes()
    { return sportTypes; }
    public void setSportTypes(String v)
    { this.sportTypes = v; }
    public String getSlotDate()
    { return slotDate; }
    public void setSlotDate(String v)
    { this.slotDate = v; }
    public String getStartTime()
    { return startTime; }
    public void setStartTime(String v)
    { this.startTime = v; }
    public String getEndTime()
    { return endTime; }
    public void setEndTime(String v)
    { this.endTime = v; }
    public String getUserName()
    { return userName; }
    public void setUserName(String v)
    { this.userName = v; }
}