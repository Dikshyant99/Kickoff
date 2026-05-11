<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

message.txt
4 KB
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

message.txt
4 KB

...
@import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Barlow:wght@400;500;600&family=Barlow+Condensed:wght@500;600;700&display=swap');

*, *::before, *::after {
    box-sizing: border-box;
    margin: 0;
    padding: 0;

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Profile - KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Barlow:wght@400;500;600&family=Barlow+Condensed:wght@500;600;700&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userdashboard.css"/>
  <style>
    .edit_section_label {
      font-size: 11px;
      font-weight: 700;
      letter-spacing: 1.5px;
      text-transform: uppercase;
      color: #444;
      margin-bottom: 20px;
      padding-bottom: 10px;
      border-bottom: 1px solid #1e1e1e;
    }
    .form_group { margin-bottom: 18px; }
    .form_group label {
      display: block;
      font-size: 11px;
      font-weight: 700;
      letter-spacing: 1px;
      text-transform: uppercase;
      color: #444;
      margin-bottom: 7px;
    }
    .form_group input,
    .form_group select {
      width: 100%;
      padding: 10px 14px;
      background: #0c0c0c;
      border: 1px solid #222;
      border-radius: 3px;
      color: #e8e8e8;
      font-size: 14px;
      font-family: 'Barlow', sans-serif;
      font-weight: 500;
      transition: border-color 0.15s;
    }
    .form_group input:focus,
    .form_group select:focus {
      outline: none;
      border-color: #2a6fdb;
    }
    .form_group input::placeholder { color: #333; }
    .form_group select option { background: #141414; color: #e8e8e8; }
    .form_hint {
      font-size: 12px;
      color: #3a3a3a;
      margin-top: 5px;
    }
    .form_row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 16px;
    }
    .form_divider {
      border: none;
      border-top: 1px solid #1e1e1e;
      margin: 28px 0;
    }
    .form_actions {
      display: flex;
      gap: 10px;
      margin-top: 24px;
    }
    .form_actions .btn { flex: 1; text-align: center; padding: 11px; }
    .edit_avatar_wrap {
      display: flex;
      align-items: center;
      gap: 20px;
      margin-bottom: 32px;
      padding-bottom: 28px;
      border-bottom: 1px solid #1e1e1e;
    }
    .edit_avatar {
      width: 60px;
      height: 60px;
      border-radius: 4px;
      background: #2a6fdb;
      color: #fff;
      font-size: 22px;
      font-weight: 700;
      font-family: 'Barlow Condensed', sans-serif;
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
      flex-shrink: 0;
      text-transform: uppercase;
    }
    .edit_avatar img {
      width: 60px;
      height: 60px;
      object-fit: cover;
      object-position: top;
      display: block;
    }
    .edit_avatar_name {
      font-family: 'Barlow Condensed', sans-serif;
      font-size: 20px;
      font-weight: 700;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      color: #e8e8e8;
      margin-bottom: 3px;
    }
    .edit_avatar_sub {
      font-size: 12px;
      color: #444;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.8px;
    }
    @media (max-width: 600px) {
      .form_row { grid-template-columns: 1fr; }
    }
  </style>
</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
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
        <p class="page_title">Edit Profile</p>
        <a href="${pageContext.request.contextPath}/profile" class="btn btn_outline">Back</a>
      </div>

      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <%-- Personal Info Form --%>
      <div class="profile_card">

        <div class="edit_avatar_wrap">
          <div class="edit_avatar">
            <c:choose>
              <c:when test="${not empty user.image}">
                <img src="${pageContext.request.contextPath}/${user.image}" alt="Profile"/>
              </c:when>
              <c:otherwise>
                ${not empty sessionScope.firstName ? fn:substring(sessionScope.firstName, 0, 1) : 'U'}
              </c:otherwise>
            </c:choose>
          </div>
          <div>
            <div class="edit_avatar_name">${user.firstName} ${user.lastName}</div>
            <div class="edit_avatar_sub">Edit your details below</div>
          </div>
        </div>

        <form action="${pageContext.request.contextPath}/updateProfile" method="post">
          <input type="hidden" name="action" value="updateProfile"/>

          <p class="edit_section_label">Personal Information</p>

          <div class="form_row">
            <div class="form_group">
              <label>First Name</label>
              <input type="text" name="firstName" value="${user.firstName}" required/>
            </div>
            <div class="form_group">
              <label>Last Name</label>
              <input type="text" name="lastName" value="${user.lastName}"/>
            </div>
          </div>

          <div class="form_group">
            <label>Email</label>
            <input type="email" name="email" value="${user.email}" required/>
          </div>

          <div class="form_group">
            <label>Phone</label>
            <input type="text" name="phone" value="${user.phone}" placeholder="Enter phone number"/>
          </div>

          <hr class="form_divider"/>

          <p class="edit_section_label">Sport Preferences</p>

          <div class="form_row">
            <div class="form_group">
              <label>Favourite Sport</label>
              <input type="text" name="sport" value="${user.sport}" placeholder="e.g. Football"/>
            </div>
            <div class="form_group">
              <label>Skill Level</label>
              <select name="skillLevel">
                <option value="beginner"     <c:if test="${user.skillLevel eq 'beginner'}">selected</c:if>>Beginner</option>
                <option value="intermediate" <c:if test="${user.skillLevel eq 'intermediate'}">selected</c:if>>Intermediate</option>
                <option value="advanced"     <c:if test="${user.skillLevel eq 'advanced'}">selected</c:if>>Advanced</option>
              </select>
            </div>
          </div>

          <div class="form_actions">
            <a href="${pageContext.request.contextPath}/profile" class="btn btn_outline">Cancel</a>
            <button type="submit" class="btn btn_primary">Save Changes</button>
          </div>

        </form>
      </div>

      <%-- Change Password Form --%>
      <div class="profile_card">

        <form action="${pageContext.request.contextPath}/changePassword" method="post">
          <input type="hidden" name="action" value="changePassword"/>

          <p class="edit_section_label">Change Password</p>

          <div class="form_group">
            <label>Current Password</label>
            <input type="password" name="currentPassword" required/>
          </div>

          <div class="form_group">
            <label>New Password</label>
            <input type="password" name="newPassword" required/>
            <p class="form_hint">Minimum 6 characters</p>
          </div>

          <div class="form_group">
            <label>Confirm New Password</label>
            <input type="password" name="confirmPassword" required/>
          </div>

          <div class="form_actions">
            <a href="${pageContext.request.contextPath}/profile" class="btn btn_outline">Cancel</a>
            <button type="submit" class="btn btn_primary">Update Password</button>
          </div>

        </form>
      </div>

    </main>
  </div>

</body>
</html>