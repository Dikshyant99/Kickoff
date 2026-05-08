package com.kickoff.model;

public class User {

    private int    userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String sport;
    private String skillLevel;
    private String password;
    private String role;
    private String createdAt;
    private String image;
    private boolean active;

    // Empty constructor:needed by UserDAO when reading from database
    public User() {

    }

    // Full constructor :must match exactly what UserService calls:
    public User(String firstName, String lastName, String email,
                String phone,    String sport,     String skillLevel,
                String password, String role,String image) {
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.email      = email;
        this.phone      = phone;
        this.sport      = sport;
        this.skillLevel = skillLevel;
        this.password   = password;
        this.role       = role;
        this.image = image;
    }


    // Getters and Setters
    public int
    getUserId()
    { return userId; }


    public void
    setUserId(int userId)
    { this.userId = userId; }

    public String
    getFirstName()
    { return firstName; }

    public void
    setFirstName(String firstName)
    { this.firstName = firstName; }

    public String
    getLastName()
    { return lastName; }

    public void
    setLastName(String lastName)
    { this.lastName = lastName; }

    public String
    getEmail()
    { return email; }
    public void
    setEmail(String email)
    { this.email = email; }

    public String
    getPhone()
    { return phone; }

    public void
    setPhone(String phone)
    { this.phone = phone; }

    public String
    getSport()
    { return sport; }

    public void
    setSport(String sport)
    { this.sport = sport; }

    public String
    getSkillLevel()
    { return skillLevel; }

    public void
    setSkillLevel(String skillLevel)
    { this.skillLevel = skillLevel; }

    public String
    getPassword()
    { return password; }

    public void
    setPassword(String password)
    { this.password = password; }

    public String
    getRole()
    { return role; }

    public void
    setRole(String role)
    { this.role = role; }

    public String
    getCreatedAt()
    { return createdAt; }

    public void
    setCreatedAt(String createdAt)
    { this.createdAt = createdAt; }

    public boolean
    isActive()
    { return active; }
    public void
    setActive(boolean active)
    { this.active = active; }

    // Helper: returns full name in one call
    // Used in LoginServlet: user.getFirstName()
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image=image;
    }


}