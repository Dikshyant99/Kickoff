<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Contact Us – KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Contact.css"/>
</head>

<body>

  <!-- ===== NAVBAR ===== -->
  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/contact" class="active">Contact</a></li>
    </ul>
    <div class="navbar_actions">
      <c:choose>
        <c:when test="${sessionScope.loggedIn eq true}">
          <span class="welcome_text">Hi, ${sessionScope.firstName}</span>
          <c:choose>
            <c:when test="${sessionScope.role eq 'admin'}">
              <a href="${pageContext.request.contextPath}/admin" class="login_btn">Dashboard</a>
            </c:when>
            <c:otherwise>
              <a href="${pageContext.request.contextPath}/profile" class="login_btn">Dashboard</a>
            </c:otherwise>
          </c:choose>
          <a href="${pageContext.request.contextPath}/logout" class="register_btn">Logout</a>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/login"    class="login_btn">Login</a>
          <a href="${pageContext.request.contextPath}/register" class="register_btn">Register</a>
        </c:otherwise>
      </c:choose>
    </div>
  </nav>

  <!-- ===== MAIN ===== -->
  <div class="container">

    <!-- LEFT SIDE INFO -->
    <div class="info-panel">
      <h3>Kick<span class="highlight">Off</span></h3>

      <div class="info-block">
        <p class="info-label">Address</p>
        <p class="info-value">
          Kamalpokhari, Kathmandu<br/>
          Nepal 44600
        </p>
      </div>

      <div class="info-block">
        <p class="info-label">Email</p>
        <p class="info-value">support@kickoff.com</p>
      </div>

      <div class="info-block">
        <p class="info-label">Phone</p>
        <p class="info-value">+977 9818674581</p>
      </div>

      <div class="info-block">
        <p class="info-label">Office Hours</p>
        <p class="info-value">
          Monday – Friday<br/>
          9:00 AM – 6:00 PM
        </p>
      </div>
    </div>

    <!-- RIGHT SIDE FORM -->
    <div class="card">

      <h2>Contact <span class="highlight">Us</span></h2>
      <p>We usually respond within 24 hours</p>

      <%-- Success / Error messages --%>
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="message success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="message error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <form class="contact-form" action="${pageContext.request.contextPath}/contact" method="post">

        <div class="row">
          <div class="form-group">
            <label>First Name</label>
            <input type="text" name="firstName" required/>
          </div>
          <div class="form-group">
            <label>Last Name</label>
            <input type="text" name="lastName" required/>
          </div>
        </div>

        <div class="form-group">
          <label>Email</label>
          <input type="email" name="email" required/>
        </div>

        <div class="form-group">
          <label>Phone</label>
          <input type="text" name="phone"/>
        </div>

        <div class="form-group">
          <label>Subject</label>
          <select name="subject" required>
            <option value="">Select a topic</option>
            <option value="general">General Inquiry</option>
            <option value="partnership">Partnership</option>
            <option value="support">Support</option>
          </select>
        </div>

        <div class="form-group">
          <label>Message</label>
          <textarea name="message" rows="4" required></textarea>
        </div>

        <button type="submit" class="btn">Send Message</button>

      </form>

    </div>

  </div>

  <!-- ===== FOOTER ===== -->
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