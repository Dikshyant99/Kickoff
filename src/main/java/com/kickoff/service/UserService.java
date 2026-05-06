package com.kickoff.service;

import com.kickoff.dao.UserDAO;
import com.kickoff.model.User;
import com.kickoff.util.PasswordUtil;
import com.kickoff.util.ValidationUtil;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    // REGISTRATION
    // Validates all input then saves new user to database
    // Returns "success" or a specific error message string
    public String registerUser(String firstName, String lastName,
                               String email,     String phone,
                               String sport,     String skillLevel,
                               String password,  String confirmPassword,
                               String image) {

        // Check required fields are not empty
        if (ValidationUtil.isNullOrEmpty(firstName) ||
                ValidationUtil.isNullOrEmpty(lastName)  ||
                ValidationUtil.isNullOrEmpty(email)     ||
                ValidationUtil.isNullOrEmpty(password)) {
            return "First name, last name, email and password are required.";
        }

        // Names must be letters only
        if (!ValidationUtil.isAlphaOnly(firstName)) {
            return "First name must contain letters only.";
        }
        if (!ValidationUtil.isAlphaOnly(lastName)) {
            return "Last name must contain letters only.";
        }

        // Valid email format
        if (!ValidationUtil.isValidEmail(email)) {
            return "Please enter a valid email address.";
        }

        // Phone is optional but must be 10 digits if provided
        if (!ValidationUtil.isNullOrEmpty(phone) &&
                !ValidationUtil.isValidPhone(phone)) {
            return "Phone number must be 10 digits.";
        }

        // Password must be at least 8 chars with letters and numbers
        if (!ValidationUtil.isValidPassword(password)) {
            return "Password must be at least 8 characters with letters and numbers.";
        }

        // Both passwords must match
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        // No duplicate email allowed
        if (userDAO.emailExists(email)) {
            return "An account with this email already exists.";
        }

        // No duplicate phone allowed
        if (!ValidationUtil.isNullOrEmpty(phone) &&
                userDAO.phoneExists(phone)) {
            return "This phone number is already registered.";
        }

        // Hash password before saving , never store plain text
        String hashedPassword = PasswordUtil.hash(password);

        // Create User object with image and save to database
        User user = new User(firstName, lastName, email, phone,
                sport, skillLevel, hashedPassword, "user", image);

        boolean saved = userDAO.registerUser(user);
        return saved ? "success" : "Registration failed. Please try again.";
    }

    // CHECK LOGIN
    // Validates input first then calls DAO for database check
    // Returns specific string telling exactly what happened:
    // "success"   :credentials correct
    // "wrong_password" :email found but password wrong
    // "user_not_found" :no account with this email
    // "email_empty"   :email field was blank
    // "password_empty" : password field was blank
    // "invalid_email"  :email format is wrong
    // "error"         :database error
    public String checkLogin(String email, String password) {

        // Check email is not empty
        if (ValidationUtil.isNullOrEmpty(email)) {
            return "email_empty";
        }

        // Check password is not empty
        if (ValidationUtil.isNullOrEmpty(password)) {
            return "password_empty";
        }

        // Check email format is valid
        if (!ValidationUtil.isValidEmail(email)) {
            return "invalid_email";
        }

        // Pass to DAO for actual database check
        // DAO will return "success", "wrong_password", "user_not_found" or "error"
        return userDAO.checkLogin(email, password);
    }

    //  GET USER BY EMAIL
    // Called after checkLogin() returns "success"
    // Returns the full User object so servlet can store user details
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}