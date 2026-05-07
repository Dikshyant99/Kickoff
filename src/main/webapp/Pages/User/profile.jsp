<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Profile – KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userdashboard.css"/>
  <style>
    .profile_avatar {
      width: 80px; height: 80px;
      min-width: 80px; min-height: 80px;
      border-radius: 50%;
      background: #2a6fdb;
      color: #fff;
      font-size: 28px; font-weight: 600;
      display: flex; align-items: center; justify-content: center;
      flex-shrink: 0; overflow: hidden;
      border: 3px solid #2a6fdb;
    }
    .profile_avatar_img {
      width: 80px; height: 80px;
      min-width: 80px; min-height: 80px;
      object-fit: cover; object-position: top; display: block;
    }
    .msg_success {
      background: rgba(39,174,96,0.15);
      border: 1px solid #27ae60;
      color: #27ae60;
      padding: 12px 16px;
      border-radius: 8px;
      margin-bottom: 16px;
      font-size: 14px;
    }
    .msg_error {
      background: rgba(231,76,60,0.15);
      border: 1px solid #e74c3c;
      color: #e74c3c;
      padding: 12px 16px;
      border-radius: 8px;
      margin-bottom: 16px;
      font-size: 14px;
    }
  </style>
</head>
<body>

  <!-- ===== NAVBAR ===== -->
  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/Pages/Root/Homepage.jsp" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/Pages/Root/Homepage.jsp">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/grounds.jsp">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/teams.jsp">Teams</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/about.jsp">About</a></li>
    </ul>
    <div class="navbar_avatar">
      <c:choose>
        <c:when test="${not empty user.image}">
          <img src="${pageContext.request.contextPath}/${user.image}"
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

    <!-- ===== SIDEBAR ===== -->
    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/ProfileServlet"          class="sidebar_item active">My Profile</a>
      <a href="${pageContext.request.contextPath}/Pages/User/myteam.jsp"   class="sidebar_item">My Team</a>
      <a href="${pageContext.request.contextPath}/Pages/User/bookings.jsp" class="sidebar_item">My Bookings</a>
      <a href="${pageContext.request.contextPath}/LogoutServlet"           class="sidebar_item">Logout</a>
    </aside>

    <!-- ===== MAIN ===== -->
    <main class="main">

      <!-- Flash messages -->
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <c:if test="${empty requestScope.user}">
        <div class="msg_error">
          Could not load profile. Please
          <a href="${pageContext.request.contextPath}/Pages/Auth/login.jsp">login again</a>.
        </div>
      </c:if>

      <c:if test="${not empty requestScope.user}">
        <div class="profile_card">

          <!-- Avatar + name -->
          <div class="profile_top">
            <div class="profile_avatar">
              <c:choose>
                <c:when test="${not empty user.image}">
                  <img src="${pageContext.request.contextPath}/${user.image}"
                       class="profile_avatar_img" alt="Profile"/>
                </c:when>
                <c:otherwise>
                  <%= session.getAttribute("firstName") != null ?
                      session.getAttribute("firstName").toString().substring(0,1).toUpperCase() : "U" %>
                </c:otherwise>
              </c:choose>
            </div>
            <div class="profile_info">
              <h2 class="profile_name">${user.firstName} ${user.lastName}</h2>
              <p class="profile_role">${user.role}</p>
            </div>
          </div>

          <hr class="profile_divider"/>

          <!-- Details grid -->
          <div class="profile_grid">

            <div class="profile_field">
              <span class="field_label">Email</span>
              <span class="field_value">${user.email}</span>
            </div>

            <div class="profile_field">
              <span class="field_label">Phone</span>
              <span class="field_value">
                <c:choose>
                  <c:when test="${not empty user.phone}">${user.phone}</c:when>
                  <c:otherwise>Not provided</c:otherwise>
                </c:choose>
              </span>
            </div>

            <div class="profile_field">
              <span class="field_label">Favourite Sport</span>
              <span class="field_value">
                <c:choose>
                  <c:when test="${not empty user.sport}">${user.sport}</c:when>
                  <c:otherwise>Not set</c:otherwise>
                </c:choose>
              </span>
            </div>

            <div class="profile_field">
              <span class="field_label">Skill Level</span>
              <span class="field_value">
                <span class="badge badge_blue">
                  <c:choose>
                    <c:when test="${not empty user.skillLevel}">${user.skillLevel}</c:when>
                    <c:otherwise>Not set</c:otherwise>
                  </c:choose>
                </span>
              </span>
            </div>

            <div class="profile_field">
              <span class="field_label">Member Since</span>
              <span class="field_value">${user.createdAt}</span>
            </div>

            <div class="profile_field">
              <span class="field_label">Role</span>
              <span class="field_value">
                <span class="badge badge_green">${user.role}</span>
              </span>
            </div>

          </div>

          <hr class="profile_divider"/>

          <!-- Actions -->
          <div class="profile_actions">
            <a href="${pageContext.request.contextPath}/EditProfileServlet"
               class="btn btn_primary">Edit Profile</a>
            <a href="${pageContext.request.contextPath}/EditProfileServlet"
               class="btn btn_outline">Change Password</a>
          </div>

        </div>
      </c:if>

    </main>
  </div>

</body>
</html>