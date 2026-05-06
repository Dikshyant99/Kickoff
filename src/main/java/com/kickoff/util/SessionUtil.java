package com.kickoff.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    //stores values in a session, called during login to save users detail
    public static void setAttribute(HttpServletRequest request, String key,Object value) {
        HttpSession session = request.getSession(true);//creates session if not exists
        session.setAttribute(key, value);
    }

    //Reads a value from session by key and returns null if session doesn't exist or key not found

    public static Object getAttribute(HttpServletRequest request, String key) {
        // TODO Auto-generated constructor stub
        HttpSession session = request.getSession(false);//doesn't creates new session
        if(session != null) {
            return session.getAttribute(key);
        }
        return null;
    }
    //Removes one specific attribute from session
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.removeAttribute(key);
        }

    }
    //Destroys the entire session, called during logout
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }

    // Checks if user is logged in
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null &&
                Boolean.TRUE.equals(session.getAttribute("loggedIn"));



    }
    //checks if admin is logged in
    public static boolean isAdmin(HttpServletRequest request)
    {
        Object role=getAttribute(request,"role");
        return "admin".equals(role);
    }
}
