<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Team – KickOff</title>
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
    <div class="navbar_avatar">
      <c:choose>
        <c:when test="${not empty sessionScope.image}">
          <img src="${pageContext.request.contextPath}/${sessionScope.image}"
               class="avatar_img" alt="Profile"/>
        </c:when>
        <c:otherwise>
          <img src="${pageContext.request.contextPath}/Assets/default-avatar.png"
               class="avatar_img" alt="Profile"/>
        </c:otherwise>
      </c:choose>
    </div>
  </nav>

  <div class="layout">

    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/profile"    class="sidebar_item">My Profile</a>
      <a href="${pageContext.request.contextPath}/Pages/User/myteam.jsp" class="sidebar_item active">My Team</a>
      <a href="${pageContext.request.contextPath}/myBookings" class="sidebar_item">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <div class="team_section">

        <div class="team_info">
          <h2 class="team_name">Thunder FC</h2>
          <p class="team_meta">Football &nbsp;·&nbsp; Intermediate &nbsp;·&nbsp; Kathmandu</p>
          <p class="team_members_label">Team Members</p>
        </div>

        <div class="members_list">

          <div class="member_item">
            <div class="member_left">
              <div class="member_avatar">D</div>
              <span class="member_name">Dikshyant Karki</span>
              <span class="badge badge_captain">Captain</span>
            </div>
            <span class="badge badge_green">Available</span>
          </div>

          <div class="member_item">
            <div class="member_left">
              <div class="member_avatar">Y</div>
              <span class="member_name">Yug Gautam</span>
            </div>
            <span class="badge badge_green">Available</span>
          </div>

          <div class="member_item">
            <div class="member_left">
              <div class="member_avatar">R</div>
              <span class="member_name">Ram Sharma</span>
            </div>
            <span class="badge badge_yellow">Busy</span>
          </div>

        </div>

        <div class="team_actions">
          <a href="#" class="btn btn_primary">Invite Player</a>
          <a href="#" class="btn btn_outline">Leave Team</a>
        </div>

      </div>

    </main>
  </div>

</body>
</html>