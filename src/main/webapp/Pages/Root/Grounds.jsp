<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KickOff — Grounds</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:ital,wght@0,400;0,500;0,600;1,400&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/grounds.css"/>
</head>
<body>

  <!-- ===== NAVBAR ===== -->
  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds" class="active">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/teams">Teams</a></li>
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
          <a href="${pageContext.request.contextPath}/login"    class="btn btn--outline">Login</a>
          <a href="${pageContext.request.contextPath}/register" class="btn btn--primary">Register</a>
        </c:otherwise>
      </c:choose>
    </div>
  </nav>

  <!-- ===== PAGE HEADER ===== -->
  <div class="page-header">
    <h1 class="page-header_title">Browse <span>Grounds</span></h1>
    <p class="page-header_sub">Find and book sports grounds near you</p>
    <div class="page-header_search">
      <form action="${pageContext.request.contextPath}/grounds" method="get"
            style="display:flex; gap:10px; width:100%;">
        <input type="text" name="q" placeholder="Search grounds, sports, location..."
               value="${param.q}"/>
        <button type="submit">Search</button>
      </form>
    </div>
  </div>

  <!-- ===== MAIN LAYOUT ===== -->
  <div class="grounds-layout">

    <!-- SIDEBAR -->
    <aside class="sidebar">
      <div class="sidebar_block">
        <label class="sidebar_label">Sport</label>
        <ul class="sidebar_list">
          <li class="sidebar_item ${empty param.sport ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds">All Sports</a>
          </li>
          <li class="sidebar_item ${param.sport eq 'Football' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?sport=Football">Football</a>
          </li>
          <li class="sidebar_item ${param.sport eq 'Cricket' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?sport=Cricket">Cricket</a>
          </li>
          <li class="sidebar_item ${param.sport eq 'Basketball' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?sport=Basketball">Basketball</a>
          </li>
          <li class="sidebar_item ${param.sport eq 'Volleyball' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?sport=Volleyball">Volleyball</a>
          </li>
          <li class="sidebar_item ${param.sport eq 'Tennis' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?sport=Tennis">Tennis</a>
          </li>
        </ul>
      </div>

      <div class="sidebar_block">
        <label class="sidebar_label">Location</label>
        <ul class="sidebar_list">
          <li class="sidebar_item ${empty param.city ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds">All Areas</a>
          </li>
          <li class="sidebar_item ${param.city eq 'Kathmandu' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?city=Kathmandu">Kathmandu</a>
          </li>
          <li class="sidebar_item ${param.city eq 'Lalitpur' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?city=Lalitpur">Lalitpur</a>
          </li>
          <li class="sidebar_item ${param.city eq 'Bhaktapur' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/grounds?city=Bhaktapur">Bhaktapur</a>
          </li>
        </ul>
      </div>
    </aside>

    <!-- GROUNDS MAIN -->
    <main class="grounds-main">

      <div class="grounds-top">
        <p class="grounds-count">
          Showing ${empty grounds ? '0' : grounds.size()} grounds
        </p>
        <div class="grounds-sort">
          Sort by
          <select>
            <option>Price: Low to High</option>
            <option>Price: High to Low</option>
            <option>Availability</option>
          </select>
        </div>
      </div>

      <div class="cards-grid">
        <c:choose>

          <%-- No grounds found --%>
          <c:when test="${empty grounds}">
            <div style="grid-column:1/-1; text-align:center; color:#888; padding:60px 0;">
              No grounds found. Try a different search or filter.
            </div>
          </c:when>

          <%-- Dynamic grounds from database --%>
          <c:otherwise>
            <c:forEach var="ground" items="${grounds}">
              <div class="ground-card">
                <div class="ground-card_image">
                  <c:choose>
                    <c:when test="${not empty ground.imageUrl}">
                      <img src="${pageContext.request.contextPath}/${ground.imageUrl}"
                           alt="${ground.name}"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/Assets/KICKOFF.jpg"
                             alt="${ground.name}"/>
                    </c:otherwise>
                  </c:choose>
                </div>
                <div class="ground-card_body">
                  <div class="ground-card_name">${ground.name}</div>
                  <div class="ground-card_meta">${ground.city} · ${ground.sportTypes}</div>
                  <div class="ground-card_footer">
                    <span class="ground-card_price">Rs ${ground.pricePerHour}/hr</span>
                    <c:choose>
                      <c:when test="${ground.active}">
                        <span class="badge badge--green">Available</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge badge--red">Unavailable</span>
                      </c:otherwise>
                    </c:choose>
                  </div>

                  <%-- Book Now button - only for logged in users --%>
                  <c:choose>
                    <c:when test="${sessionScope.loggedIn eq true and sessionScope.role ne 'admin'}">
                      <a href="${pageContext.request.contextPath}/bookingForm?groundId=${ground.groundId}"
                         class="btn btn--primary"
                         style="display:block; text-align:center; margin-top:12px; width:100%;">
                        Book Now
                      </a>
                    </c:when>
                    <c:when test="${sessionScope.role eq 'admin'}">
                      <a href="${pageContext.request.contextPath}/listGrounds"
                         class="btn btn--outline"
                         style="display:block; text-align:center; margin-top:12px; width:100%;">
                        Manage
                      </a>
                    </c:when>
                    <c:otherwise>
                      <a href="${pageContext.request.contextPath}/login"
                         class="btn btn--outline"
                         style="display:block; text-align:center; margin-top:12px; width:100%;">
                        Login to Book
                      </a>
                    </c:otherwise>
                  </c:choose>

                </div>
              </div>
            </c:forEach>
          </c:otherwise>

        </c:choose>
      </div>
    </main>
  </div>

  <!-- ===== FOOTER ===== -->
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