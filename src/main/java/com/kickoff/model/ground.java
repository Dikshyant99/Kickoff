package com.kickoff.model;




public class ground {
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

    public ground() {}

    public int getGroundId()
    { return groundId; }
    public void setGroundId(int v)
    { this.groundId = v; }
    public int getOwnerId()
    { return ownerId; }
    public void setOwnerId(int v)
    { this.ownerId = v; }
    public String getName()
    { return name; }
    public void setName(String v)
    { this.name = v; }
    public String getLocation()
    { return location; }
    public void setLocation(String v)
    { this.location = v; }

    public String getCity()
    { return city; }
    public void setCity(String v)
    { this.city = v; }
    public String getSportTypes()
    { return sportTypes; }
    public void setSportTypes(String v)
    { this.sportTypes = v; }
    public double getPricePerHour()
    { return pricePerHour; }
    public void setPricePerHour(double v)
    { this.pricePerHour = v; }
    public String getDescription()
    { return description; }
    public void setDescription(String v)
    { this.description = v; }
    public String getImageUrl()
    { return imageUrl; }
    public void setImageUrl(String v)
    { this.imageUrl = v; }
    public boolean isActive()
    { return isActive; }
    public void setActive(boolean v)
    { this.isActive = v; }
    public String getCreatedAt()
    { return createdAt; }
    public void setCreatedAt(String v)
    { this.createdAt = v; }
}
