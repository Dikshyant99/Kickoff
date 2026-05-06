package com.kickoff.util;

// Contains reusable validation methods used by UserService.
// Keeps validation logic in one place instead of repeated in every servlet.

public class ValidationUtil {

    // Returns true if the string is null or contains only whitespace.
    // Used to check required fields are not left blank.
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Checks email follows basic format
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    // Phone must be exactly 10 digits - no spaces, no dashes.
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^[0-9]{10}$");
    }

    // Name fields must only contain letters and spaces.
    // Rejects inputs like "John123" or "Admin!"
    public static boolean isAlphaOnly(String value) {
        return value != null && value.matches("^[a-zA-Z ]+$");
    }

    // Password must be at least 8 characters with at least
    // one letter and one number.
    public static boolean isValidPassword(String password) {
        return password != null &&
                password.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$");
    }
}