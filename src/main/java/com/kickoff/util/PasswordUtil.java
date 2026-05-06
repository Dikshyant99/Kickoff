package com.kickoff.util;

import org.mindrot.jbcrypt.BCrypt;

// Handles all password security.
// NEVER store plain text passwords in the database.
// BCrypt automatically adds a salt and is much safer than SHA-256.

public class PasswordUtil {

    // Takes a plain text password and returns a secure hashed version.
    // The 12 in gensalt(12) is the work factor - higher = slower = more secure.
    // Called during registration before saving to database.
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Compares a plain text password against a stored hash.
    // Returns true if they match, false if not.
    // Called during login to verify the entered password.
    public static boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}