<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Teams - KickOff Admin</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admindashboard.css"/>
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
    <span class="admin_badge">Admin</span>
  </nav>

  <div class="layout">
    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/admin"                      class="sidebar_item">Overview</a>
      <a href="${pageContext.request.contextPath}/admin?action=listUsers"     class="sidebar_item">Users</a>
      <a href="${pageContext.request.contextPath}/admin?action=listGrounds"   class="sidebar_item">Grounds</a>
      <a href="${pageContext.request.contextPath}/admin?action=listTeams"     class="sidebar_item active">Teams</a>
      <a href="${pageContext.request.contextPath}/admin?action=listBookings"  class="sidebar_item">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/logout"                     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">
      <p class="page_title">Teams</p>

      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <input type="text" id="teamSearch" class="search_input"
             placeholder="Search teams..."
             onkeyup="filterTeams()"/>

      <div id="teamsList">
        <c:choose>
          <c:when test="${empty teams}">
            <div class="empty">No teams found.</div>
          </c:when>
          <c:otherwise>
            <c:forEach var="team" items="${teams}">
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

  <script>
    function filterTeams() {
      var input = document.getElementById("teamSearch").value.toLowerCase();
      var items = document.querySelectorAll("#teamsList .list_item");
      items.forEach(function(item) {
        item.style.display =
          item.innerText.toLowerCase().includes(input) ? "flex" : "none";
      });
    }
  </script>

</body>
</html>