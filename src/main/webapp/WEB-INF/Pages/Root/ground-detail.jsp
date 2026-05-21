<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KickOff — ${ground.name}</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:ital,wght@0,400;0,500;0,600;1,400&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/ground-detail.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/grounds.css"/>


</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds" class="active">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
       <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
    </ul>
    <div class="navbar_actions">
      <c:choose>
        <c:when test="${sessionScope.loggedIn eq true}">
          <span class="welcome_text">Hi, ${sessionScope.firstName}</span>
          <c:choose>
            <c:when test="${sessionScope.role eq 'admin'}">
              <a href="${pageContext.request.contextPath}/admin" class="btn btn--outline">Dashboard</a>
            </c:when>
            <c:otherwise>
              <a href="${pageContext.request.contextPath}/profile" class="btn btn--outline">Dashboard</a>
            </c:otherwise>
          </c:choose>
          <a href="${pageContext.request.contextPath}/logout" class="btn btn--primary">Logout</a>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/login" class="btn btn--outline">Login</a>
          <a href="${pageContext.request.contextPath}/register" class="btn btn--primary">Register</a>
        </c:otherwise>
      </c:choose>
    </div>
  </nav>

  <div class="detail-header">
    <div class="detail-header_top">
      <a href="${pageContext.request.contextPath}/grounds" class="back-link">
         Back to Grounds
      </a>
      <c:choose>
        <c:when test="${ground.active}">
          <span class="detail-badge available">Available</span>
        </c:when>
        <c:otherwise>
          <span class="detail-badge unavailable">Unavailable</span>
        </c:otherwise>
      </c:choose>
    </div>
    <h1 class="detail-title">${ground.name}</h1>
    <div class="detail-meta">
      <span> ${ground.location}, ${ground.city}</span>
      <span>${ground.sportTypes}</span>
    </div>
  </div>

  <div class="detail-content">
    <div class="detail-main">
      <div class="detail-image">
        <c:choose>
          <c:when test="${not empty ground.imageUrl}">
            <img src="${pageContext.request.contextPath}/${ground.imageUrl}" alt="${ground.name}"/>
          </c:when>
          <c:otherwise>
            <img src="${pageContext.request.contextPath}/Assets/KICKOFF.jpg" alt="${ground.name}"/>
          </c:otherwise>
        </c:choose>
      </div>

      <div class="detail-section">
        <h3>About This Ground</h3>
        <p>
          <c:choose>
            <c:when test="${not empty ground.description}">
              ${ground.description}
            </c:when>
            <c:otherwise>
              This is a premium sports ground located in ${ground.city}.
              Perfect for ${ground.sportTypes} matches and training sessions.
              Book your slot today and enjoy a great playing experience.
            </c:otherwise>
          </c:choose>
        </p>
      </div>
    </div>

    <div class="detail-sidebar">
      <div class="detail-section">
        <h3>Pricing</h3>
        <div class="price-tag">
          Rs ${ground.pricePerHour} <span>/ hour</span>
        </div>
        <div class="action-buttons">
          <c:choose>
            <c:when test="${sessionScope.loggedIn eq true and sessionScope.role ne 'admin' and ground.active}">

              <a href="${pageContext.request.contextPath}/bookingForm?groundId=${ground.groundId}"
                 class="btn btn--primary" style="display:block; text-align:center; width:100%;">
                Book Now
              </a>
            </c:when>
            <c:when test="${sessionScope.role eq 'admin'}">
              <a href="${pageContext.request.contextPath}/admin" class="btn btn--outline" style="display:block; text-align:center; width:100%;">
                Manage Ground
              </a>
            </c:when>
            <c:when test="${not ground.active}">
              <span style="color: #ef4444; font-size: 0.9rem; text-align: center;">This ground is currently unavailable</span>
            </c:when>
            <c:otherwise>
              <a href="${pageContext.request.contextPath}/login" class="btn btn--primary" style="display:block; text-align:center; width:100%;">
                Login to Book
              </a>
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <div class="detail-section">
        <h3>Details</h3>
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">Location</span>
            <span class="info-value">${ground.location}</span>
          </div>
          <div class="info-item">
            <span class="info-label">City</span>
            <span class="info-value">${ground.city}</span>
          </div>
          <div class="info-item">
            <span class="info-label">Sports</span>
            <span class="info-value">${ground.sportTypes}</span>
          </div>
          <div class="info-item">
            <span class="info-label">Price</span>
            <span class="info-value">Rs ${ground.pricePerHour}/hr</span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <footer class="footer">
    <a href="${pageContext.request.contextPath}/home" class="footer_logo">Kick<span>Off</span></a>
    <ul class="footer_links">
      <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
      <li><a href="#">Privacy</a></li>
    </ul>
    <p class="footer_copy">© 2026 KickOff. All rights reserved.</p>
  </footer>

</body>
</html>