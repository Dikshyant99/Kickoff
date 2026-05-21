<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Profile - KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
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
    <div class="navbar_actions">
      <span class="welcome_text">Hi, ${sessionScope.firstName}</span>
      <a href="${pageContext.request.contextPath}/profile" class="login_btn">Dashboard</a>
      <a href="${pageContext.request.contextPath}/logout"  class="register_btn">Logout</a>
    </div>
  </nav>

  <div class="layout">

    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/profile"    class="sidebar_item">My Profile</a>
      <a href="${pageContext.request.contextPath}/myBookings" class="sidebar_item">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <div class="page_header">
        <p class="page_title">Edit Profile</p>
        <a href="${pageContext.request.contextPath}/profile" class="btn btn_outline">Back</a>
      </div>

      <!-- Success/Error Messages -->
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <!-- ===== EDIT PROFILE FORM ===== -->
      <form action="${pageContext.request.contextPath}/updateProfile"
            method="post"
            enctype="multipart/form-data"
            style="margin-bottom: 24px;">

        <div class="profile_card" style="margin-bottom: 24px;">
          <h3 style="font-size: 1.1rem; font-weight: 600; color: #f0f0f0; margin-bottom: 20px;">
            Personal Information
          </h3>

         <!-- ===== PROFILE PICTURE UPLOAD ===== -->
         <div style="margin-bottom: 24px;">
           <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
             Profile Picture
           </label>

           <input type="file"
                  id="profilePicInput"
                  name="profilePic"
                  accept="image/*"
                  style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem; cursor: pointer;"
                  onchange="previewImage(event)"/>

           <!-- Preview of selected image -->
           <div id="previewContainer" style="margin-top: 12px; display: none;">
             <p style="font-size: 0.85rem; color: #999; margin-bottom: 8px;">Preview:</p>
             <img id="previewImage"
                  src=""
                  alt="Preview"
                  style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover; border: 2px solid #2a6fdb;"/>
           </div>
         </div>

          <!-- First Name & Last Name -->
          <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 16px;">
            <div>
              <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
                First Name
              </label>
              <input type="text"
                     name="firstName"
                     value="${user.firstName}"
                     required
                     style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
            </div>
            <div>
              <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
                Last Name
              </label>
              <input type="text"
                     name="lastName"
                     value="${user.lastName}"
                     style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
            </div>
          </div>

          <!-- Email -->
          <div style="margin-bottom: 16px;">
            <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
              Email
            </label>
            <input type="email"
                   name="email"
                   value="${user.email}"
                   required
                   style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
          </div>

          <!-- Phone -->
          <div style="margin-bottom: 16px;">
            <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
              Phone
            </label>
            <input type="text"
                   name="phone"
                   value="${user.phone}"
                   style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
          </div>

          <!-- Sport & Skill Level -->
          <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 16px;">
            <div>
              <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
                Favorite Sport
              </label>
              <input type="text"
                     name="sport"
                     value="${user.sport}"
                     style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
            </div>
            <div>
              <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
                Skill Level
              </label>
              <select name="skillLevel"
                      style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;">
                <option value="">Select Skill Level</option>
                <option value="Beginner" ${user.skillLevel eq 'Beginner' ? 'selected' : ''}>Beginner</option>
                <option value="Intermediate" ${user.skillLevel eq 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                <option value="Advanced" ${user.skillLevel eq 'Advanced' ? 'selected' : ''}>Advanced</option>
              </select>
            </div>
          </div>

          <!-- Save Changes Button -->
          <div style="display: flex; gap: 12px; margin-top: 24px;">
            <button type="button"
                    class="btn btn_outline"
                    onclick="window.location.href='${pageContext.request.contextPath}/profile'"
                    style="width: 50%; padding: 12px; font-size: 1rem;">
              Cancel
            </button>
            <button type="submit"
                    class="btn btn_primary"
                    style="width: 50%; padding: 12px; font-size: 1rem;">
              Save Changes
            </button>
          </div>
        </div>
      </form>

      <!-- ===== CHANGE PASSWORD FORM ===== -->
      <form action="${pageContext.request.contextPath}/changePassword" method="post">
        <div class="profile_card">
          <h3 style="font-size: 1.1rem; font-weight: 600; color: #f0f0f0; margin-bottom: 20px;">
            Change Password
          </h3>

          <!-- Current Password -->
          <div style="margin-bottom: 16px;">
            <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
              Current Password
            </label>
            <input type="password"
                   name="currentPassword"
                   required
                   style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
          </div>

          <!-- New Password -->
          <div style="margin-bottom: 16px;">
            <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
              New Password
            </label>
            <input type="password"
                   name="newPassword"
                   required
                   style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
          </div>

          <!-- Confirm Password -->
          <div style="margin-bottom: 16px;">
            <label style="display: block; font-size: 0.85rem; color: #999; margin-bottom: 8px;">
              Confirm Password
            </label>
            <input type="password"
                   name="confirmPassword"
                   required
                   style="width: 100%; padding: 12px; background: #2a2a2a; border: 1px solid #444; border-radius: 6px; color: #f0f0f0; font-size: 0.95rem;"/>
          </div>

          <!-- Update Password Button -->
          <div style="display: flex; gap: 12px; margin-top: 24px;">
            <button type="button"
                    class="btn btn_outline"
                    onclick="window.location.href='${pageContext.request.contextPath}/profile'"
                    style="width: 50%; padding: 12px; font-size: 1rem;">
              Cancel
            </button>
            <button type="submit"
                    class="btn btn_primary"
                    style="width: 50%; padding: 12px; font-size: 1rem;">
              Update Password
            </button>
          </div>
        </div>
      </form>

    </main>
  </div>

  <!-- JavaScript for image preview -->
  <script>
    function previewImage(event) {
      const file = event.target.files[0];

      if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
          const previewContainer = document.getElementById('previewContainer');
          const previewImage = document.getElementById('previewImage');

          previewImage.src = e.target.result;
          previewContainer.style.display = 'block';
        };

        reader.readAsDataURL(file);
      }
    }
  </script>

</body>
</html>
