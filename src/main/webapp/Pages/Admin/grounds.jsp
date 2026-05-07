<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Grounds – KickOff Admin</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admindashboard.css"/>
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
    <span class="admin_badge">Admin</span>
  </nav>

  <div class="layout">
    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/admin"        class="sidebar_item">Overview</a>
      <a href="${pageContext.request.contextPath}/listUsers"    class="sidebar_item">Users</a>
      <a href="${pageContext.request.contextPath}/listGrounds"  class="sidebar_item active">Grounds</a>
      <a href="${pageContext.request.contextPath}/listTeams"    class="sidebar_item">Teams</a>
      <a href="${pageContext.request.contextPath}/listBookings" class="sidebar_item">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/logout"       class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <%-- Success / Error messages --%>
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <div class="page_header">
        <p class="page_title">Grounds</p>
        <button onclick="document.getElementById('addGroundForm').style.display='block'"
                class="btn btn_primary">+ Add Ground</button>
      </div>

      <%-- Add Ground Form (hidden by default) --%>
      <div id="addGroundForm" style="display:none; background:#1a1a1a;
           border-radius:14px; padding:24px; margin-bottom:24px;">
        <p style="font-size:1rem; font-weight:600; color:#f0f0f0; margin-bottom:16px;">
          Add New Ground
        </p>
        <form action="${pageContext.request.contextPath}/addGround" method="post">
          <div style="display:grid; grid-template-columns:1fr 1fr; gap:16px; margin-bottom:16px;">
            <div>
              <label style="font-size:12px; color:#888; display:block; margin-bottom:6px;">Ground Name</label>
              <input type="text" name="name" required class="search_input" placeholder="e.g. City Football Arena"/>
            </div>
            <div>
              <label style="font-size:12px; color:#888; display:block; margin-bottom:6px;">Location</label>
              <input type="text" name="location" required class="search_input" placeholder="e.g. Baneshwor"/>
            </div>
            <div>
              <label style="font-size:12px; color:#888; display:block; margin-bottom:6px;">City</label>
              <input type="text" name="city" required class="search_input" placeholder="e.g. Kathmandu"/>
            </div>
            <div>
              <label style="font-size:12px; color:#888; display:block; margin-bottom:6px;">Sport Types</label>
              <input type="text" name="sportTypes" required class="search_input" placeholder="e.g. Football"/>
            </div>
            <div>
              <label style="font-size:12px; color:#888; display:block; margin-bottom:6px;">Price Per Hour (Rs)</label>
              <input type="number" name="pricePerHour" required class="search_input" placeholder="e.g. 800"/>
            </div>
            <div>
              <label style="font-size:12px; color:#888; display:block; margin-bottom:6px;">Description</label>
              <input type="text" name="description" class="search_input" placeholder="Short description"/>
            </div>
          </div>
          <div style="display:flex; gap:12px;">
            <button type="submit" class="btn btn_primary">Save Ground</button>
            <button type="button" class="btn btn_outline"
                    onclick="document.getElementById('addGroundForm').style.display='none'">
              Cancel
            </button>
          </div>
        </form>
      </div>

      <%-- Search --%>
      <input type="text" id="groundSearch" class="search_input"
             placeholder="Search grounds..."
             onkeyup="filterGrounds()"/>

      <%-- Grounds List --%>
      <div id="groundsList">
        <c:choose>
          <c:when test="${empty requestScope.grounds}">
            <div class="empty">No grounds found.</div>
          </c:when>
          <c:otherwise>
            <c:forEach var="ground" items="${requestScope.grounds}">
              <div class="list_item">
                <div>
                  <div class="list_item_name">${ground.name}</div>
                  <div class="list_item_meta">
                    ${ground.city} &nbsp;·&nbsp;
                    ${ground.sportTypes} &nbsp;·&nbsp;
                    Rs ${ground.pricePerHour}/hr &nbsp;·&nbsp;
                    Owner: ${ground.ownerName}
                  </div>
                </div>
                <div class="list_item_right">
                  <c:choose>
                    <c:when test="${ground.isActive}">
                      <span class="badge badge_green">Active</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge_yellow">Inactive</span>
                    </c:otherwise>
                  </c:choose>
                  <a href="${pageContext.request.contextPath}/deleteGround?id=${ground.groundId}"
                     class="btn btn_red"
                     onclick="return confirm('Delete this ground?')">Delete</a>
                </div>
              </div>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </div>

    </main>
  </div>

  <script>
    function filterGrounds() {
      var input = document.getElementById("groundSearch").value.toLowerCase();
      var items = document.querySelectorAll("#groundsList .list_item");
      items.forEach(function(item) {
        item.style.display =
          item.innerText.toLowerCase().includes(input) ? "flex" : "none";
      });
    }
  </script>

</body>
</html>