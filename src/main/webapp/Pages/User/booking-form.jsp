<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Book Ground – KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userdashboard.css"/>
</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/teams.jsp">Teams</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/about.jsp">About</a></li>
    </ul>
    <div class="navbar_actions">
      <span class="welcome_text">Hi, ${sessionScope.firstName}</span>
      <a href="${pageContext.request.contextPath}/profile" class="login_btn">Dashboard</a>
      <a href="${pageContext.request.contextPath}/logout"  class="register_btn">Logout</a>
    </div>
  </nav>

  <div class="layout">

    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/profile"    class="sidebar_item">My Profile</a>
      <a href="${pageContext.request.contextPath}/Pages/User/myteam.jsp" class="sidebar_item">My Team</a>
      <a href="${pageContext.request.contextPath}/myBookings" class="sidebar_item active">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <c:if test="${not empty requestScope.ground}">

        <div class="page_header">
          <p class="page_title">Book — ${requestScope.ground.name}</p>
          <a href="${pageContext.request.contextPath}/grounds" class="btn btn_outline">← Back to Grounds</a>
        </div>

        <%-- Ground info --%>
        <div class="profile_card" style="margin-bottom:24px;">
          <div class="profile_grid">
            <div class="profile_field">
              <span class="field_label">Ground</span>
              <span class="field_value">${requestScope.ground.name}</span>
            </div>
            <div class="profile_field">
              <span class="field_label">Location</span>
              <span class="field_value">${requestScope.ground.city}</span>
            </div>
            <div class="profile_field">
              <span class="field_label">Sport</span>
              <span class="field_value">${requestScope.ground.sportTypes}</span>
            </div>
            <div class="profile_field">
              <span class="field_label">Price Per Hour</span>
              <span class="field_value">Rs ${requestScope.ground.pricePerHour}</span>
            </div>
          </div>
        </div>

        <%-- Slot selection --%>
        <div class="profile_card">
          <h3 style="font-size:1.1rem; font-weight:600; color:#f0f0f0; margin-bottom:20px;">
            Select a Time Slot
          </h3>

          <c:choose>
            <c:when test="${empty requestScope.slots}">
              <div class="empty">
                No available slots for this ground at the moment.
                <br/>
                <a href="${pageContext.request.contextPath}/grounds"
                   style="color:#2a6fdb;">Browse other grounds</a>
              </div>
            </c:when>
            <c:otherwise>
              <form action="${pageContext.request.contextPath}/makeBooking" method="post">
                <input type="hidden" name="groundId" value="${requestScope.ground.groundId}"/>

                <div style="display:flex; flex-direction:column; gap:12px; margin-bottom:24px;">
                  <c:forEach var="slot" items="${requestScope.slots}">
                    <label style="display:flex; align-items:center; gap:16px;
                                  background:#2a2a2a; border-radius:10px;
                                  padding:16px 20px; cursor:pointer;">
                      <input type="radio" name="slotId" value="${slot.slotId}"
                             required style="accent-color:#2a6fdb; width:18px; height:18px;"/>
                      <div>
                        <div style="font-size:0.95rem; font-weight:600; color:#f0f0f0;">
                          ${slot.slotDate}
                        </div>
                        <div style="font-size:0.85rem; color:#888; margin-top:4px;">
                          ${slot.startTime} – ${slot.endTime}
                        </div>
                      </div>
                      <span class="badge badge_green" style="margin-left:auto;">Available</span>
                    </label>
                  </c:forEach>
                </div>

                <button type="submit" class="btn btn_primary"
                        style="width:100%; padding:14px; font-size:1rem;">
                  Confirm Booking
                </button>

              </form>
            </c:otherwise>
          </c:choose>

        </div>

      </c:if>

      <c:if test="${empty requestScope.ground}">
        <div class="msg_error">
          Ground not found.
          <a href="${pageContext.request.contextPath}/grounds">Go back to grounds</a>.
        </div>
      </c:if>

    </main>
  </div>

</body>
</html>