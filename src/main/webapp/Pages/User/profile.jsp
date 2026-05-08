<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Profile - KickOff</title>
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

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/teams">Teams</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
    </ul>
    <div class="navbar_avatar">
      <c:choose>
        <c:when test="${not empty sessionScope.user.image}">
          <img src="${pageContext.request.contextPath}/${sessionScope.user.image}"
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
      <a href="${pageContext.request.contextPath}/profile"   class="sidebar_item active">My Profile</a>
      <a href="${pageContext.request.contextPath}/myTeam"    class="sidebar_item">My Team</a>
      <a href="${pageContext.request.contextPath}/myBookings" class="sidebar_item">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"    class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <c:if test="${empty sessionScope.user}">
        <div class="msg_error">
          Could not load profile. Please
          <a href="${pageContext.request.contextPath}/login">login again</a>.
        </div>
      </c:if>

      <c:if test="${not empty sessionScope.user}">
        <div class="profile_card">

          <div class="profile_top">
            <div class="profile_avatar">
              <c:choose>
                <c:when test="${not empty sessionScope.user.image}">
                  <img src="${pageContext.request.contextPath}/${sessionScope.user.image}"
                       class="profile_avatar_img" alt="Profile"/>
                </c:when>
                <c:otherwise>
                  ${not empty sessionScope.firstName ? fn:substring(sessionScope.firstName, 0, 1) : 'U'}
                </c:otherwise>
              </c:choose>
            </div>
            <div class="profile_info">
              <h2 class="profile_name">${sessionScope.user.firstName} ${sessionScope.user.lastName}</h2>
              <p class="profile_role">${sessionScope.user.role}</p>
            </div>
          </div>

          <hr class="profile_divider"/>

          <div class="profile_grid">

            <div class="profile_field">
              <span class="field_label">Email</span>
              <span class="field_value">${sessionScope.user.email}</span>
            </div>

            <div class="profile_field">
              <span class="field_label">Phone</span>
              <span class="field_value">
                <c:choose>
                  <c:when test="${not empty sessionScope.user.phone}">${sessionScope.user.phone}</c:when>
                  <c:otherwise>Not provided</c:otherwise>
                </c:choose>
              </span>
            </div>

            <div class="profile_field">
              <span class="field_label">Favourite Sport</span>
              <span class="field_value">
                <c:choose>
                  <c:when test="${not empty sessionScope.user.sport}">${sessionScope.user.sport}</c:when>
                  <c:otherwise>Not set</c:otherwise>
                </c:choose>
              </span>
            </div>

            <div class="profile_field">
              <span class="field_label">Skill Level</span>
              <span class="field_value">
                <span class="badge badge_blue">
                  <c:choose>
                    <c:when test="${not empty sessionScope.user.skillLevel}">${sessionScope.user.skillLevel}</c:when>
                    <c:otherwise>Not set</c:otherwise>
                  </c:choose>
                </span>
              </span>
            </div>

            <div class="profile_field">
              <span class="field_label">Member Since</span>
              <span class="field_value">${sessionScope.user.createdAt}</span>
            </div>

            <div class="profile_field">
              <span class="field_label">Role</span>
              <span class="field_value">
                <span class="badge badge_green">${sessionScope.user.role}</span>
              </span>
            </div>

          </div>

          <hr class="profile_divider"/>

          <div class="profile_actions">
            <a href="${pageContext.request.contextPath}/editProfile"
               class="btn btn_primary">Edit Profile</a>
            <a href="${pageContext.request.contextPath}/changePassword"
               class="btn btn_outline">Change Password</a>
          </div>

        </div>
      </c:if>

    </main>
  </div>

</body>
</html>