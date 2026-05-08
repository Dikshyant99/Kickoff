<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Bookings - KickOff</title>
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
      <a href="${pageContext.request.contextPath}/profile"                 class="sidebar_item">My Profile</a>
      <a href="${pageContext.request.contextPath}/Pages/User/myteam.jsp"   class="sidebar_item">My Team</a>
      <a href="${pageContext.request.contextPath}/myBookings"              class="sidebar_item active">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"                  class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <div class="page_header">
        <p class="page_title">My Bookings</p>
        <a href="${pageContext.request.contextPath}/grounds" class="btn btn_outline">+ Book a Ground</a>
      </div>

      <%-- messages read from session, removed after display --%>
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <%-- changed: requestScope.bookings replaced with sessionScope.bookings --%>
      <c:choose>
        <c:when test="${empty sessionScope.bookings}">
          <div class="empty">
            You have no bookings yet.
            <a href="${pageContext.request.contextPath}/grounds" style="color:#2a6fdb;">
              Browse grounds to book one.
            </a>
          </div>
        </c:when>
        <c:otherwise>
          <c:forEach var="booking" items="${sessionScope.bookings}">
            <div class="list_item">
              <div>
                <div class="list_item_name">${booking.groundName}</div>
                <div class="list_item_meta">
                  ${booking.slotDate} &nbsp;·&nbsp;
                  ${booking.startTime} - ${booking.endTime} &nbsp;·&nbsp;
                  Rs ${booking.totalPrice}
                </div>
              </div>
              <div class="list_item_right">
                <c:choose>
                  <c:when test="${booking.status eq 'confirmed'}">
                    <span class="badge badge_green">Approved</span>
                  </c:when>
                  <c:when test="${booking.status eq 'pending'}">
                    <span class="badge badge_yellow">Pending</span>
                    <a href="${pageContext.request.contextPath}/cancelBooking?id=${booking.bookingId}"
                       class="btn btn_red"
                       onclick="return confirm('Cancel this booking?')">Cancel</a>
                  </c:when>
                  <c:otherwise>
                    <span class="badge badge_red">Cancelled</span>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
          </c:forEach>
        </c:otherwise>
      </c:choose>

    </main>
  </div>

</body>
</html>