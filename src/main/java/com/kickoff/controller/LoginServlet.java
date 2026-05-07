package com.kickoff.controller;

import com.kickoff.model.User;
import com.kickoff.service.UserService;
import com.kickoff.util.CookiesUtil;
import com.kickoff.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read remember me cookies and pass to JSP
        String savedEmail = "";
        boolean remembered = false;

        Cookie emailCookie    = CookiesUtil.getCookie(request, "userEmail");
        Cookie rememberCookie = CookiesUtil.getCookie(request, "rememberMe");

        if (emailCookie != null) {
            savedEmail = emailCookie.getValue();
        }
        if (rememberCookie != null) {
            remembered = true;
        }

        request.setAttribute("savedEmail", savedEmail);
        request.setAttribute("remembered", remembered);

        request.getRequestDispatcher("/Pages/Auth/login.jsp")
                .forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Read form fields
        String email      = request.getParameter("email");
        String password   = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Get specific result from service
        String result = userService.checkLogin(email, password);

        if (result.equals("success")) {

            // Get full User object from database
            User user = userService.getUserByEmail(email);

            // SET SESSION
            // Store all user details in session
            SessionUtil.setAttribute(request, "loggedIn",   true);
            SessionUtil.setAttribute(request, "userId",     user.getUserId());
            SessionUtil.setAttribute(request, "firstName",  user.getFirstName());
            SessionUtil.setAttribute(request, "lastName",   user.getLastName());
            SessionUtil.setAttribute(request, "email",      user.getEmail());
            SessionUtil.setAttribute(request, "phone",      user.getPhone());
            SessionUtil.setAttribute(request, "sport",      user.getSport());
            SessionUtil.setAttribute(request, "skillLevel", user.getSkillLevel());
            SessionUtil.setAttribute(request, "role",       user.getRole());
            SessionUtil.setAttribute(request, "image",      user.getImage());
            SessionUtil.setAttribute(request, "createdAt",  user.getCreatedAt());

            // SET COOKIES
            if ("on".equals(rememberMe)) {
                // Remember me ticked — save for 7 days
                CookiesUtil.addCookie(response, "userEmail",  email,  7 * 24 * 60 * 60);
                CookiesUtil.addCookie(response, "rememberMe", "true", 7 * 24 * 60 * 60);
            } else {
                // Delete cookies
                CookiesUtil.deleteCookie(response, "userEmail");
                CookiesUtil.deleteCookie(response, "rememberMe");
            }

            // Redirect to admin or user
            if (user.getRole().equals("admin")) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/profile");
            }

        } else if (result.equals("wrong_password")) {
            request.setAttribute("errorMsg", "Wrong password. Please try again.");
            request.getRequestDispatcher("/Pages/Auth/login.jsp")
                    .forward(request, response);

        } else if (result.equals("user_not_found")) {
            request.setAttribute("errorMsg",
                    "No account found with this email. Please register.");
            request.getRequestDispatcher("/Pages/Auth/login.jsp")
                    .forward(request, response);

        } else if (result.equals("email_empty")) {
            request.setAttribute("errorMsg", "Please enter your email address.");
            request.getRequestDispatcher("/Pages/Auth/login.jsp")
                    .forward(request, response);

        } else if (result.equals("password_empty")) {
            request.setAttribute("errorMsg", "Please enter your password.");
            request.getRequestDispatcher("/Pages/Auth/login.jsp")
                    .forward(request, response);

        } else if (result.equals("invalid_email")) {
            request.setAttribute("errorMsg", "Please enter a valid email address.");
            request.getRequestDispatcher("/Pages/Auth/login.jsp")
                    .forward(request, response);

        } else {
            request.setAttribute("errorMsg", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/Pages/Auth/login.jsp")
                    .forward(request, response);
        }
    }
}