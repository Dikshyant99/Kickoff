package com.kickoff.model;

/*
 * Model class representing
 * a sports ground.
 *
 * Stores:
 * - Ground details
 * - Owner information
 * - Pricing details
 * - Availability status
 */
public class ground {
    /*
     * Ground table fields.
     */
    private int groundId;
    private int ownerId;
    private String name;
    private String location;
    private String city;
    private String sportTypes;
    private double pricePerHour;
    private String description;
    private String imageUrl;
    private boolean isActive;
    private String createdAt;
    /*
     * Default constructor.
     */

    public ground() {}
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
     * Returns owner ID.
     */
    public int getOwnerId()
    { return ownerId; }

    /*
     * Returns owner ID.
     */
    public void setOwnerId(int v)
    { this.ownerId = v; }

    /*
     * Returns ground name.
     */
    public String getName()
    { return name; }
    /*
     * Sets ground name.
     */
    public void setName(String v)
    { this.name = v; }
    /*
     * Returns ground location.
     */
    public String getLocation()
    { return location; }
    /*
     * Sets ground location.
     */
    public void setLocation(String v)
    { this.location = v; }
    /*
     * Returns city name.
     */
    public String getCity()
    { return city; }
    /*
     * Sets city name.
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
     * Returns hourly ground price.
     */
    public double getPricePerHour()
    { return pricePerHour; }
    /*
     * Sets hourly ground price.
     */
    public void setPricePerHour(double v)
    { this.pricePerHour = v; }
    /*
     * Returns ground description.
     */
    public String getDescription()
    { return description; }

    /*
     * Sets ground description.
     */
    public void setDescription(String v)
    { this.description = v; }

    /*
     * Returns ground image URL.
     */
    public String getImageUrl()
    { return imageUrl; }
    /*
     * Sets ground image URL.
     */
    public void setImageUrl(String v)
    { this.imageUrl = v; }
    /*
     * Returns ground active status.
     */
    public boolean isActive()
    { return isActive; }
    /*
     * Sets ground active status.
     */
    public void setActive(boolean v)
    { this.isActive = v; }
    /*
     * Returns ground creation date.
     */
    public String getCreatedAt()
    { return createdAt; }
    /*
     * Sets ground creation date.
     */
    public void setCreatedAt(String v)
    { this.createdAt = v; }
}
