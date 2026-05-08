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

        // request attributes + forward
        request.setAttribute("savedEmail", savedEmail);
        request.setAttribute("remembered", remembered);
        request.getRequestDispatcher("/Pages/Auth/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email      = request.getParameter("email");
        String password   = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        String result = userService.checkLogin(email, password);

        if (result.equals("success")) {

            User user = userService.getUserByEmail(email);

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

            if ("on".equals(rememberMe)) {
                CookiesUtil.addCookie(response, "userEmail",  email,  7 * 24 * 60 * 60);
                CookiesUtil.addCookie(response, "rememberMe", "true", 7 * 24 * 60 * 60);
            } else {
                CookiesUtil.deleteCookie(response, "userEmail");
                CookiesUtil.deleteCookie(response, "rememberMe");
            }

            if (user.getRole().equals("admin")) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/profile");
            }

        } else {
            if (result.equals("wrong_password")) {
                request.getSession().setAttribute("errorMsg", "Wrong password. Please try again.");
            } else if (result.equals("user_not_found")) {
                request.getSession().setAttribute("errorMsg", "No account found with this email. Please register.");
            } else if (result.equals("email_empty")) {
                request.getSession().setAttribute("errorMsg", "Please enter your email address.");
            } else if (result.equals("password_empty")) {
                request.getSession().setAttribute("errorMsg", "Please enter your password.");
            } else if (result.equals("invalid_email")) {
                request.getSession().setAttribute("errorMsg", "Please enter a valid email address.");
            } else {
                request.getSession().setAttribute("errorMsg", "Something went wrong. Please try again.");
            }
            // FIX: redirect to servlet, not JSP
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}