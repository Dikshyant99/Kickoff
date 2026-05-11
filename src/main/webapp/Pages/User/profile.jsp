<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Profile - KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userdashboard.css"/>
  <style>
    .edit_wrapper {
      display: flex;
      justify-content: center;
      padding: 40px 20px;
    }
    .edit_card {
      background: #1a1a2e;
      border: 1px solid #2a2a4a;
      border-radius: 16px;
      padding: 40px;
      width: 100%;
      max-width: 560px;
    }
    .edit_avatar_row {
      display: flex;
      align-items: center;
      gap: 20px;
      margin-bottom: 32px;
    }
    .edit_avatar {
      width: 80px; height: 80px;
      border-radius: 50%;
      background: #2a6fdb;
      color: #fff;
      font-size: 28px; font-weight: 600;
      display: flex; align-items: center; justify-content: center;
      overflow: hidden;
      border: 3px solid #2a6fdb;
      flex-shrink: 0;
    }
    .edit_avatar img {
      width: 80px; height: 80px;
      object-fit: cover; object-position: top;
    }
    .edit_avatar_info h3 {
      margin: 0 0 4px 0;
      color: #fff;
      font-size: 18px;
      font-family: 'DM Sans', sans-serif;
      font-weight: 600;
    }
    .edit_avatar_info p {
      margin: 0;
      color: #888;
      font-size: 13px;
    }
    .edit_section_title {
      font-family: 'Bebas Neue', sans-serif;
      font-size: 13px;
      letter-spacing: 2px;
      color: #2a6fdb;
      text-transform: uppercase;
      margin: 0 0 16px 0;
      padding-bottom: 8px;
      border-bottom: 1px solid #2a2a4a;
    }
    .form_group { margin-bottom: 16px; }
    .form_group label {
      display: block;
      font-size: 11px; font-weight: 600;
      letter-spacing: 1px; text-transform: uppercase;
      color: #888; margin-bottom: 6px;
    }
    .form_group input,
    .form_group select {
      width: 100%;
      padding: 11px 14px;
      background: #12122a;
      border: 1px solid #2a2a4a;
      border-radius: 8px;
      color: #fff;
      font-size: 14px;
      font-family: 'DM Sans', sans-serif;
      box-sizing: border-box;
      transition: border-color 0.2s;
    }
    .form_group input:focus,
    .form_group select:focus {
      outline: none;
      border-color: #2a6fdb;
    }
    .form_group select option { background: #1a1a2e; }
    .form_hint { font-size: 12px; color: #666; margin-top: 5px; }
    .form_row { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; }
    .edit_divider { border: none; border-top: 1px solid #2a2a4a; margin: 28px 0; }
    .form_actions { display: flex; gap: 12px; margin-top: 28px; }
    .form_actions .btn {
      flex: 1; text-align: center; padding: 12px;
      border: none; cursor: pointer;
      font-size: 14px; font-family: 'DM Sans', sans-serif;
      font-weight: 600; border-radius: 8px;
      text-decoration: none; display: inline-block;
    }
    .msg_success {
      background: rgba(39,174,96,0.15);
      border: 1px solid #27ae60; color: #27ae60;
      padding: 12px 16px; border-radius: 8px;
      margin-bottom: 20px; font-size: 14px;
    }
    .msg_error {
      background: rgba(231,76,60,0.15);
      border: 1px solid #e74c3c; color: #e74c3c;
      padding: 12px 16px; border-radius: 8px;
      margin-bottom: 20px; font-size: 14px;
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

    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/profile"    class="sidebar_item active">My Profile</a>
      <a href="${pageContext.request.contextPath}/myTeam"     class="sidebar_item">My Team</a>
      <a href="${pageContext.request.contextPath}/myBookings" class="sidebar_item">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">
      <div class="edit_wrapper">
        <div class="edit_card">

          <%-- avatar row --%>
          <div class="edit_avatar_row">
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
            <div class="edit_avatar_info">

              <h3>${user.firstName} ${user.lastName}</h3>
              <p>Update your profile information below</p>
            </div>
          </div>

          <%-- messages --%>
          <c:if test="${not empty sessionScope.successMsg}">
            <div class="msg_success">${sessionScope.successMsg}</div>
            <c:remove var="successMsg" scope="session"/>
          </c:if>
          <c:if test="${not empty sessionScope.errorMsg}">
            <div class="msg_error">${sessionScope.errorMsg}</div>
            <c:remove var="errorMsg" scope="session"/>
          </c:if>

          <%-- form 1 - edit profile --%>
          <form action="${pageContext.request.contextPath}/updateProfile" method="post">
            <input type="hidden" name="action" value="updateProfile"/>

            <p class="edit_section_title">Personal Information</p>

            <div class="form_row">
              <div class="form_group">
                <label>First Name</label>
                <input type="text" name="firstName"
                       value="${user.firstName}" required/>
              </div>
              <div class="form_group">
                <label>Last Name</label>
                <input type="text" name="lastName"
                       value="${user.lastName}"/>
              </div>
            </div>

            <div class="form_group">
              <label>Email</label>
              <input type="email" name="email"
                     value="${user.email}" required/>
            </div>

            <div class="form_group">
              <label>Phone</label>
              <input type="text" name="phone"
                     value="${user.phone}" placeholder="Enter phone number"/>
            </div>

            <hr class="edit_divider"/>

            <p class="edit_section_title">Sport Preferences</p>

            <div class="form_row">
              <div class="form_group">
                <label>Favourite Sport</label>
                <input type="text" name="sport"
                       value="${user.sport}" placeholder="e.g. Football"/>
              </div>
              <div class="form_group">
                <label>Skill Level</label>
                <select name="skillLevel">
                  <option value="beginner"
                    <c:if test="${user.skillLevel eq 'beginner'}">selected</c:if>>
                    Beginner
                  </option>
                  <option value="intermediate"
                    <c:if test="${user.skillLevel eq 'intermediate'}">selected</c:if>>
                    Intermediate
                  </option>
                  <option value="advanced"
                    <c:if test="${user.skillLevel eq 'advanced'}">selected</c:if>>
                    Advanced
                  </option>
                </select>
              </div>
            </div>

            <div class="form_actions">
              <a href="${pageContext.request.contextPath}/profile"
                 class="btn btn_outline">Cancel</a>
              <button type="submit" class="btn btn_primary">Save Changes</button>
            </div>

          </form>

          <hr class="edit_divider"/>

          <%-- form 1 - change password --%>
          <form action="${pageContext.request.contextPath}/changePassword" method="post">
            <input type="hidden" name="action" value="changePassword"/>

            <p class="edit_section_title">Change Password</p>

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
              <a href="${pageContext.request.contextPath}/profile"
                 class="btn btn_outline">Cancel</a>
              <button type="submit" class="btn btn_primary">Update Password</button>
            </div>

          </form>

        </div>
      </div>
    </main>
  </div>

</body>
</html>