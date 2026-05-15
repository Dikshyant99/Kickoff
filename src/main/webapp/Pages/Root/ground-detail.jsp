<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KickOff — ${ground.name}</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:ital,wght@0,400;0,500;0,600;1,400&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/grounds.css"/>
  <style>
    .detail-header {
      padding: 36px 48px;
      background: #1a1a2e;
      border-bottom: 1px solid #2a2a2a;
    }

    .back-link {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      color: #8a8a8a;
      font-size: 0.875rem;
      text-decoration: none;
      margin-bottom: 16px;
    }

    .back-link:hover { color: #2979ff; }

    .detail-title {
      font-family: 'Bebas Neue', sans-serif;
      font-size: 2.8rem;
      color: #fff;
      letter-spacing: 2px;
      margin-bottom: 10px;
    }

    .detail-meta {
      display: flex;
      align-items: center;
      gap: 18px;
      color: #8a8a8a;
      font-size: 0.9rem;
    }

    .detail-meta span {
      display: flex;
      align-items: center;
      gap: 5px;
    }

    .detail-badge {
      padding: 4px 10px;
      font-size: 0.78rem;
      font-weight: 600;
      border-radius: 4px;
    }

    .detail-badge.available {
      background: rgba(34, 197, 94, 0.12);
      color: #22c55e;
      border: 1px solid rgba(34, 197, 94, 0.3);
    }

    .detail-badge.unavailable {
      background: rgba(239, 68, 68, 0.12);
      color: #ef4444;
      border: 1px solid rgba(239, 68, 68, 0.3);
    }

    .detail-content {
      display: grid;
      grid-template-columns: 2fr 1fr;
      gap: 28px;
      padding: 36px 48px;
      max-width: 1200px;
      margin: 0 auto;
    }

    .detail-image {
      width: 100%;
      height: 380px;
      border-radius: 8px;
      overflow: hidden;
      margin-bottom: 20px;
    }

    .detail-image img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .detail-section {
      background: #181818;
      border: 1px solid #2a2a2a;
      border-radius: 8px;
      padding: 20px;
      margin-bottom: 20px;
    }

    .detail-section h3 {
      font-size: 1rem;
      font-weight: 600;
      color: #fff;
      margin-bottom: 14px;
      padding-bottom: 10px;
      border-bottom: 1px solid #2a2a2a;
    }

    .detail-section p {
      color: #8a8a8a;
      line-height: 1.7;
      font-size: 0.9rem;
    }

    .info-grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 14px;
    }

    .info-item {
      display: flex;
      flex-direction: column;
      gap: 3px;
    }

    .info-label {
      font-size: 0.7rem;
      color: #8a8a8a;
      text-transform: uppercase;
      letter-spacing: 0.8px;
    }

    .info-value {
      font-size: 0.95rem;
      color: #fff;
      font-weight: 500;
    }

    .price-tag {
      font-family: 'Bebas Neue', sans-serif;
      font-size: 1.9rem;
      color: #2979ff;
      letter-spacing: 1px;
    }

    .price-tag span {
      font-size: 0.9rem;
      color: #8a8a8a;
      font-family: 'DM Sans', sans-serif;
    }

    .action-buttons {
      display: flex;
      flex-direction: column;
      gap: 10px;
      margin-top: 18px;
    }

    .btn-detail {
      padding: 13px 20px;
      border-radius: 6px;
      font-family: 'DM Sans', sans-serif;
      font-size: 0.9rem;
      font-weight: 600;
      text-align: center;
      cursor: pointer;
      transition: background 0.2s;
      display: block;
      text-decoration: none;
    }

    .btn-detail--primary {
      background: #2979ff;
      color: #fff;
      border: none;
    }

    .btn-detail--primary:hover { background: #1a56cc; }

    .btn-detail--outline {
      background: transparent;
      color: #fff;
      border: 1px solid #2979ff;
    }

    .btn-detail--outline:hover { background: rgba(41, 121, 255, 0.08); }
  </style>
</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds" class="active">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
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
        ← Back to Grounds
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
      <span>📍 ${ground.location}, ${ground.city}</span>
      <span>🏅 ${ground.sportTypes}</span>
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
              <a href="${pageContext.request.contextPath}/listGrounds" class="btn btn--outline" style="display:block; text-align:center; width:100%;">
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