package com.kickoff.util;
import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CookiesUtil {

    /**
     * Creates and adds a secure cookie to the response.
     */
    public static void addCookie(HttpServletResponse response, String name,
                                 String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);              // Set BEFORE adding to response
        cookie.setSecure(true);                //  Only send over HTTPS
        response.addCookie(cookie);            // Add to response LAST
    }

    /**
     * Retrieves a cookie by name from the request.
     * Uses Java Streams for clean, safe implementation.
     * Returns null if cookie not found.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if(request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes a cookie by setting maxAge to 0 and value to empty string.
     * Path must match the original cookie path.
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, ""); // Empty string instead of null
        cookie.setMaxAge(0);                     // Expire immediately
        cookie.setPath("/");                     // Must match original path
        cookie.setHttpOnly(true);                // Must match original settings
        cookie.setSecure(true);                  // Must match original settings
        response.addCookie(cookie);
    }
}