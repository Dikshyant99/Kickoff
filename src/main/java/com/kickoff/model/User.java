package com.kickoff.model;
/*
 * Model class representing
 * a system user.
 *
 * Stores:
 * - Personal information
 * - Login details
 * - Sports preferences
 * - User role and status
 */
public class User {
    /*
     * User table fields.
     */
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

    /*
     * Default constructor.
     *
     * Used when reading data
     * from database tables.
     */
    public User() {

    }

    /*
     * Full constructor used
     * during user registration.
     */
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


    /*
     * Returns user ID.
     */
    public int
    getUserId()
    { return userId; }

    /*
     * Sets user ID.
     */
    public void
    setUserId(int userId)
    { this.userId = userId; }
    /*
     * Returns first name.
     */
    public String
    getFirstName()
    { return firstName; }
    /*
     * Sets first name.
     */
    public void
    setFirstName(String firstName)
    { this.firstName = firstName; }
    /*
     * Returns last name.
     */
    public String
    getLastName()
    { return lastName; }
    /*
     * Sets last name.
     */
    public void
    setLastName(String lastName)
    { this.lastName = lastName; }
    /*
     * Returns email address.
     */
    public String
    getEmail()
    { return email; }
    /*
     * Sets email address.
     */
    public void
    setEmail(String email)
    { this.email = email; }

    /*
     * Returns phone number.
     */
    public String
    getPhone()
    { return phone; }
    /*
     * Sets phone number.
     */
    public void
    setPhone(String phone)
    { this.phone = phone; }
    /*
     * Returns favorite sport.
     */
    public String
    getSport()
    { return sport; }
    /*
     * Sets favorite sport.
     */
    public void
    setSport(String sport)
    { this.sport = sport; }
    /*
     * Returns skill level.
     */
    public String
    getSkillLevel()
    { return skillLevel; }
    /*
     * Sets skill level.
     */
    public void
    setSkillLevel(String skillLevel)
    { this.skillLevel = skillLevel; }

    /*
     * Returns password.
     */
    public String
    getPassword()
    { return password; }
    /*
     * Sets password.
     */
    public void
    setPassword(String password)
    { this.password = password; }

    /*
     * Returns user role.
     */
    public String
    getRole()
    { return role; }

    /*
     * Sets user role.
     */
    public void
    setRole(String role)
    { this.role = role; }
    /*
     * Returns account creation date.
     */
    public String
    getCreatedAt()
    { return createdAt; }
    /*
     * Sets account creation date.
     */
    public void
    setCreatedAt(String createdAt)
    { this.createdAt = createdAt; }
    /*
     * Returns account active status.
     */
    public boolean
    isActive()
    { return active; }
    /*
     * Sets account active status.
     */
    public void
    setActive(boolean active)
    { this.active = active; }


    /*
     * Returns full user name.
     *
     * Combines first and last name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    /*
     * Returns profile image path.
     */
    public String getImage() {
        return image;
    }
    /*
     * Sets profile image path.
     */
    public void setImage(String image) {

        this.image=image;
    }


}