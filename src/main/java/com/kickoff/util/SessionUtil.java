package com.kickoff.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    // Session timeout duration (30 minutes)
    private static final int SESSION_TIMEOUT = 30*60;

    // Stores values in session and sets timeout
    public static void setAttribute(HttpServletRequest request, String key, Object value) {

        // Creates session if it doesn't exist
        HttpSession session = request.getSession(true);

        // Set session timeout to 30 minutes
        session.setMaxInactiveInterval(SESSION_TIMEOUT);

        // Store attribute
        session.setAttribute(key, value);
    }

    // Reads value from session
    public static Object getAttribute(HttpServletRequest request, String key) {

        // Does not create new session
        HttpSession session = request.getSession(false);

        if (session != null) {
            return session.getAttribute(key);
        }

        return null;
    }

    // Removes a specific attribute from session
    public static void removeAttribute(HttpServletRequest request, String key) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(key);
        }
    }

    // Destroys entire session during logout
    public static void invalidateSession(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    // Checks if user is logged in
    public static boolean isLoggedIn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        return session != null &&
                Boolean.TRUE.equals(session.getAttribute("loggedIn"));
    }

    // Checks if logged in user is admin
    public static boolean isAdmin(HttpServletRequest request) {

        Object role = getAttribute(request, "role");

        return "admin".equals(role);
    }
}