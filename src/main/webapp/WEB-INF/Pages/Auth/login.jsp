<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login - KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login.css"/>
</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
       <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
    </ul>
    <div class="navbar_actions">
      <a href="${pageContext.request.contextPath}/login"    class="login_btn">Login</a>
      <a href="${pageContext.request.contextPath}/register" class="register_btn">Register</a>
    </div>
  </nav>

  <div class="auth_wrapper">
    <div class="auth_card">

      <div class="auth_brand">
        <a href="${pageContext.request.contextPath}/home">Kick<span>Off</span></a>
      </div>
      <p class="auth_heading">Welcome back</p>
      <p class="auth_subheading">Log in to book grounds and manage your teams</p>

      <hr class="auth_divider"/>

      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <c:if test="${param.registered eq 'true'}">
        <div class="msg_success">Account created successfully! Please log in.</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/login" method="post">

        <div class="form_group">
          <label class="form_label" for="email">Email address</label>
          <input class="form_input" type="email" id="email" name="email"
                 placeholder="you@example.com"
                 value="${savedEmail}"
                 required/>
        </div>

        <div class="form_group">
          <label class="form_label" for="password">Password</label>
          <input class="form_input" type="password" id="password" name="password"
                 placeholder="Enter your password"
                 required/>
        </div>

        <div class="form_row">
          <label class="form_checkbox_label">
            <input type="checkbox" name="rememberMe"
                   <c:if test="${remembered}">checked</c:if>/>
            Remember me
          </label>
          <a href="#" class="form_forgot">Forgot password?</a>
        </div>

        <button type="submit" class="auth_submit_btn">Log In</button>

      </form>

      <p class="auth_footer_text">
        Don't have an account?
        <a href="${pageContext.request.contextPath}/register">Sign up for free</a>
      </p>

    </div>
  </div>

  <footer class="footer">
    <a href="${pageContext.request.contextPath}/home" class="footer_logo">Kick<span>Off</span></a>
    <ul class="footer_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
      <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
    </ul>
    <p class="footer_copy">© 2026 KickOff. All rights reserved.</p>
  </footer>

</body>
</html>