<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register – KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/register.css"/>
</head>

<body>

  <!-- ===== NAVBAR ===== -->
  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/teams.jsp">Teams</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/findPlayers.jsp">Find Players</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/about.jsp">About</a></li>
    </ul>
    <div class="navbar_actions">
      <a href="${pageContext.request.contextPath}/login"    class="login_btn">Login</a>
      <a href="${pageContext.request.contextPath}/register" class="register_btn">Register</a>
    </div>
  </nav>

  <!-- ===== AUTH WRAPPER ===== -->
  <div class="auth_wrapper">
    <div class="auth_card">

      <div class="auth_brand">
        <a href="${pageContext.request.contextPath}/home">Kick<span>Off</span></a>
      </div>
      <p class="auth_heading">Create your account</p>
      <p class="auth_subheading">Join thousands of players and teams across Nepal</p>

      <hr class="auth_divider"/>

      <form action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">

        <%-- Error message --%>
        <c:if test="${not empty requestScope.errorMsg}">
          <div class="msg_error">${requestScope.errorMsg}</div>
        </c:if>

        <%-- Success message --%>
        <c:if test="${not empty requestScope.successMsg}">
          <div class="msg_success">${requestScope.successMsg}</div>
        </c:if>

        <!-- Personal Info -->
        <p class="section_tag">Personal Info</p>

        <div class="form_row_2col">
          <div class="form_group">
            <label class="form_label" for="firstName">First name</label>
            <input class="form_input" type="text" id="firstName" name="firstName"
                   placeholder="Dikshyant"
                   value="${not empty requestScope.firstName ? requestScope.firstName : ''}"
                   required/>
          </div>
          <div class="form_group">
            <label class="form_label" for="lastName">Last name</label>
            <input class="form_input" type="text" id="lastName" name="lastName"
                   placeholder="Karki"
                   value="${not empty requestScope.lastName ? requestScope.lastName : ''}"
                   required/>
          </div>
        </div>

        <div class="form_group">
          <label class="form_label" for="email">Email address</label>
          <input class="form_input" type="email" id="email" name="email"
                 placeholder="dikshyant67@gmail.com"
                 value="${not empty requestScope.email ? requestScope.email : ''}"
                 required/>
        </div>

        <div class="form_group">
          <label class="form_label" for="phone">Phone number</label>
          <input class="form_input" type="tel" id="phone" name="phone"
                 placeholder="98XXXXXXXX"
                 value="${not empty requestScope.phone ? requestScope.phone : ''}"/>
        </div>

        <!-- IMAGE UPLOAD -->
        <div class="form_group">
          <label class="form_label" for="image">Profile Image</label>
          <input class="form_input" type="file" id="image" name="image"
                 accept="image/*"/>
        </div>

        <!-- Sports Profile -->
        <p class="section_tag">Sports Profile</p>

        <div class="form_row_2col">
          <div class="form_group">
            <label class="form_label" for="sport">Favourite sport</label>
            <select class="form_select" id="sport" name="sport">
              <option value="" disabled
                <c:if test="${empty requestScope.sport}">selected</c:if>>
                Select sport
              </option>
              <option value="football"
                <c:if test="${requestScope.sport eq 'football'}">selected</c:if>>
                Football
              </option>
              <option value="cricket"
                <c:if test="${requestScope.sport eq 'cricket'}">selected</c:if>>
                Cricket
              </option>
              <option value="basketball"
                <c:if test="${requestScope.sport eq 'basketball'}">selected</c:if>>
                Basketball
              </option>
              <option value="tennis"
                <c:if test="${requestScope.sport eq 'tennis'}">selected</c:if>>
                Tennis
              </option>
            </select>
          </div>
          <div class="form_group">
            <label class="form_label" for="skill">Skill level</label>
            <select class="form_select" id="skill" name="skillLevel">
              <option value="" disabled
                <c:if test="${empty requestScope.skillLevel}">selected</c:if>>
                Select level
              </option>
              <option value="beginner"
                <c:if test="${requestScope.skillLevel eq 'beginner'}">selected</c:if>>
                Beginner
              </option>
              <option value="intermediate"
                <c:if test="${requestScope.skillLevel eq 'intermediate'}">selected</c:if>>
                Intermediate
              </option>
              <option value="advanced"
                <c:if test="${requestScope.skillLevel eq 'advanced'}">selected</c:if>>
                Advanced
              </option>
            </select>
          </div>
        </div>

        <!-- Security -->
        <p class="section_tag">Security</p>

        <div class="form_group">
          <label class="form_label" for="password">Password</label>
          <input class="form_input" type="password" id="password" name="password"
                 placeholder="Min. 8 characters with letters and numbers"
                 required/>
        </div>

        <div class="form_group">
          <label class="form_label" for="confirmPassword">Confirm password</label>
          <input class="form_input" type="password" id="confirmPassword"
                 name="confirmPassword"
                 placeholder="Repeat your password"
                 required/>
        </div>

        <div class="terms_row">
          <input type="checkbox" id="terms" name="terms" required/>
          <label class="terms_text" for="terms">
            I agree to KickOff's
            <a href="#">Terms of Service</a> and
            <a href="#">Privacy Policy</a>
          </label>
        </div>

        <button type="submit" class="auth_submit_btn">Create Account</button>

      </form>

      <div class="auth_separator">
        <div class="auth_separator_line"></div>
        <span class="auth_separator_text">or sign up with</span>
        <div class="auth_separator_line"></div>
      </div>

      <p class="auth_footer_text">
        Already have an account?
        <a href="${pageContext.request.contextPath}/login">Log in</a>
      </p>

    </div>
  </div>

  <!-- ===== FOOTER ===== -->
  <footer class="footer">
    <a href="${pageContext.request.contextPath}/home" class="footer_logo">Kick<span>Off</span></a>
    <ul class="footer_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/about.jsp">About</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/contact.jsp">Contact</a></li>
    </ul>
    <p class="footer_copy">© 2026 KickOff. All rights reserved.</p>
  </footer>

</body>
</html>