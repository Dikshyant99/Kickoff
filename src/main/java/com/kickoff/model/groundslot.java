package com.kickoff.model;
/*
 * Model class representing
 * a ground booking slot.
 *
 * Stores:
 * - Slot details
 * - Ground reference
 * - Date and time information
 * - Slot availability status
 */
public class groundslot {
    /*
     * Ground slot table fields.
     */
    private int slotId;
    private int groundId;
    private String slotDate;
    private String startTime;
    private String endTime;
    private String status;
    /*
     * Default constructor.
     */

    public groundslot() {}


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
     * Returns slot status.
     */
    public String getStatus()
    { return status; }
    /*
     * Sets slot status.
     */
    public void setStatus(String v)
    { this.status = v; }
}

