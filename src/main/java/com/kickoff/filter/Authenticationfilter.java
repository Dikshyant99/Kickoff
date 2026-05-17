package com.kickoff.filter;
/*
 * Authentication filter for protected routes.
 *
 * Features:
 * - Restricts access to logged-in users only
 * - Restricts admin routes to admin users
 * - Redirects unauthorized users to login page
 */
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/*

Authentication filter for all protected routes.
Admin routes require role = admin.
User routes require being logged in.*/
@WebFilter({
        "/admin",
        "/profile",
        "/editProfile",
        "/updateProfile",
        "/changePassword",
        "/myBookings",
        "/bookingForm",
        "/confirmBooking",
        "/notifications"
})
public class Authenticationfilter implements Filter {
    /*
     * Initializes filter configuration.
     */

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    /*
     * Filters incoming requests.
     *
     * Checks:
     * - User login status
     * - Admin authorization for admin routes
     *
     * Redirects unauthorized users to login page.
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        /*
         * Convert request and response
         * into HTTP-specific objects.
         */

        HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        /*
         * Get existing session.
         * false prevents creating new session.
         */

        HttpSession session = httpRequest.getSession(false);
        /*
         * Get current request path.
         */
        String path = httpRequest.getServletPath();
        /*
         * Check if user is logged in.
         * Email session attribute is used
         * to verify login status.
         */

        boolean isLoggedIn = (session != null && session.getAttribute("email") != null);
        /*
         * Check if logged-in user
         * has admin role.
         */
        boolean isAdmin    = (isLoggedIn && "admin".equals(session.getAttribute("role")));
        /*
         * Redirect user to login page
         * if not authenticated.
         */

        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        /*
         * Restrict admin route access.
         * Only admin users are allowed.
         */
        if (path.equals("/admin") && !isAdmin) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        /*
         * Continue request processing
         * if authentication passes.
         */

        chain.doFilter(request, response);
    }
    /*
     * Cleans up filter resources.
     */

    @Override
    public void destroy() {}
}