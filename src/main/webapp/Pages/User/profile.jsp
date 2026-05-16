<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Profile - KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Barlow:wght@400;500;600&family=Barlow+Condensed:wght@500;600;700&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userdashboard.css"/>
</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
       <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
    </ul>
    <div class="navbar_avatar">
      <c:choose>
        <c:when test="${not empty user.image}">
          <img src="${pageContext.request.contextPath}/${user.image}" class="avatar_img" alt="Profile"/>
        </c:when>
        <c:otherwise>
          <img src="${pageContext.request.contextPath}/Assets/default-avatar.png" class="avatar_img" alt="Profile"/>
        </c:otherwise>
      </c:choose>
    </div>
  </nav>

  <div class="layout">

    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/profile"    class="sidebar_item active">My Profile</a>
      <a href="${pageContext.request.contextPath}/myBookings" class="sidebar_item">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <div class="page_header">
        <p class="page_title">My Profile</p>
      </div>

      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <div class="profile_card">

        <div class="profile_top">
          <div class="profile_avatar">
            <c:choose>
              <c:when test="${not empty user.image}">
                <img src="${pageContext.request.contextPath}/${user.image}" class="profile_avatar_img" alt="Profile"/>
              </c:when>
              <c:otherwise>
                ${not empty user.firstName ? fn:substring(user.firstName, 0, 1) : 'U'}
              </c:otherwise>
            </c:choose>
          </div>
          <div>
            <div class="profile_name">${user.firstName} ${user.lastName}</div>
            <div class="profile_role">Member</div>
          </div>
        </div>

        <hr class="profile_divider"/>

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
                <c:otherwise>—</c:otherwise>
              </c:choose>
            </span>
          </div>
          <div class="profile_field">
            <span class="field_label">Favourite Sport</span>
            <span class="field_value">
              <c:choose>
                <c:when test="${not empty user.sport}">${user.sport}</c:when>
                <c:otherwise>—</c:otherwise>
              </c:choose>
            </span>
          </div>
          <div class="profile_field">
            <span class="field_label">Skill Level</span>
            <span class="field_value">
              <c:choose>
                <c:when test="${not empty user.skillLevel}">${user.skillLevel}</c:when>
                <c:otherwise>—</c:otherwise>
              </c:choose>
            </span>
          </div>
        </div>

        <div class="profile_actions">
          <a href="${pageContext.request.contextPath}/User/editProfile" class="btn btn_primary">Edit Profile</a>
        </div>

      </div>

    </main>
  </div>

</body>
</html>