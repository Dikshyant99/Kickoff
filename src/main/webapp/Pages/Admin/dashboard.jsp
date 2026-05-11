<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard - KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admindashboard.css"/>
</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
    </ul>
    <span class="admin_badge">Admin</span>
  </nav>

  <div class="layout">

    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/admin"                        class="sidebar_item active">Overview</a>
      <a href="${pageContext.request.contextPath}/admin?action=listUsers"       class="sidebar_item">Users</a>
      <a href="${pageContext.request.contextPath}/admin?action=listGrounds"     class="sidebar_item">Grounds</a>
      <a href="${pageContext.request.contextPath}/admin?action=listBookings"    class="sidebar_item">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/logout"                       class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <p class="page_title">Overview</p>

      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <!-- STAT CARDS -->
      <div class="stats_grid">
        <div class="stat_card">
          <div class="stat_label">Total Users</div>
          <div class="stat_value">${not empty totalUsers ? totalUsers : 0}</div>
        </div>
        <div class="stat_card">
          <div class="stat_label">Grounds</div>
          <div class="stat_value">${not empty totalGrounds ? totalGrounds : 0}</div>
        </div>
        <div class="stat_card">
          <div class="stat_label">Bookings</div>
          <div class="stat_value">${not empty totalBookings ? totalBookings : 0}</div>
        </div>
      </div>

      <!-- RECENT USERS -->
      <div class="section">
        <div class="section_title">Recent Users</div>
        <c:choose>
          <c:when test="${empty recentUsers}">
            <div class="empty">No users registered yet.</div>
          </c:when>
          <c:otherwise>
            <c:forEach var="user" items="${recentUsers}">
              <div class="list_item">
                <div>
                  <div class="list_item_name">${user.firstName} ${user.lastName}</div>
                  <div class="list_item_meta">${user.email} &nbsp;·&nbsp; ${user.sport}</div>
                </div>
                <div class="list_item_right">
                  <span class="badge badge_blue">${user.role}</span>
                  <a href="${pageContext.request.contextPath}/admin?action=deleteUser&id=${user.userId}"
                     class="btn btn_red"
                     onclick="return confirm('Delete this user?')">Delete</a>
                </div>
              </div>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </div>

      <!-- RECENT BOOKINGS -->
      <div class="section">
        <div class="section_title">Recent Booking Requests</div>
        <c:choose>
          <c:when test="${empty recentBookings}">
            <div class="empty">No booking requests yet.</div>
          </c:when>
          <c:otherwise>
            <c:forEach var="booking" items="${recentBookings}">
              <div class="list_item">
                <div>
                  <div class="list_item_name">${booking.groundName}</div>
                  <div class="list_item_meta">
                    ${booking.userName} &nbsp;·&nbsp;
                    ${booking.slotDate} &nbsp;·&nbsp;
                    ${booking.startTime} - ${booking.endTime}
                  </div>
                </div>
                <div class="list_item_right">
                  <c:choose>
                    <c:when test="${booking.status eq 'confirmed'}">
                      <span class="badge badge_green">Approved</span>
                    </c:when>
                    <c:when test="${booking.status eq 'pending'}">
                      <span class="badge badge_yellow">Pending</span>
                      <a href="${pageContext.request.contextPath}/admin?action=approveBooking&id=${booking.bookingId}"
                         class="btn btn_green">Approve</a>
                      <a href="${pageContext.request.contextPath}/admin?action=rejectBooking&id=${booking.bookingId}"
                         class="btn btn_red">Reject</a>
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
      </div>

      <!-- RECENT TEAMS -->
      <div class="section">
        <div class="section_title">Recent Teams</div>
        <c:choose>
          <c:when test="${empty recentTeams}">
            <div class="empty">No teams created yet.</div>
          </c:when>
          <c:otherwise>
            <c:forEach var="team" items="${recentTeams}">
              <div class="list_item">
                <div>
                  <div class="list_item_name">${team.name}</div>
                  <div class="list_item_meta">
                    ${team.sportType} &nbsp;·&nbsp;
                    ${team.location} &nbsp;·&nbsp;
                    ${team.currentPlayers}/${team.maxPlayers} players
                  </div>
                </div>
                <div class="list_item_right">
                  <c:choose>
                    <c:when test="${team.recruitmentStatus eq 'open'}">
                      <span class="badge badge_green">Open</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge_red">Closed</span>
                    </c:otherwise>
                  </c:choose>
                  <a href="${pageContext.request.contextPath}/admin?action=deleteTeam&id=${team.teamId}"
                     class="btn btn_red"
                     onclick="return confirm('Delete this team?')">Delete</a>
                </div>
              </div>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </div>

    </main>
  </div>

</body>
</html>