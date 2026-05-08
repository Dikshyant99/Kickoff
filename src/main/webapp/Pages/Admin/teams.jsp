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
    <a href="${pageContext.request.contextPath}/Pages/Root/Homepage.jsp" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/Pages/Root/Homepage.jsp">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/grounds.jsp">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/teams.jsp">Teams</a></li>
      <li><a href="${pageContext.request.contextPath}/Pages/Root/about.jsp">About</a></li>
    </ul>
    <span class="admin_badge">Admin</span>
  </nav>

  <div class="layout">
    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/AdminServlet"                      class="sidebar_item">Overview</a>
      <a href="${pageContext.request.contextPath}/AdminServlet?action=listUsers"     class="sidebar_item">Users</a>
      <a href="${pageContext.request.contextPath}/AdminServlet?action=listGrounds"   class="sidebar_item">Grounds</a>
      <a href="${pageContext.request.contextPath}/AdminServlet?action=listTeams"     class="sidebar_item active">Teams</a>
      <a href="${pageContext.request.contextPath}/AdminServlet?action=listBookings"  class="sidebar_item">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/LogoutServlet"                     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">
      <p class="page_title">Teams</p>

      <%-- messages read from session, removed after display --%>
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

      <%-- changed: static items replaced with sessionScope.teams --%>
      <div id="teamsList">
        <c:choose>
          <c:when test="${empty sessionScope.teams}">
            <div class="empty">No teams found.</div>
          </c:when>
          <c:otherwise>
            <c:forEach var="team" items="${sessionScope.teams}">
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
                  <a href="${pageContext.request.contextPath}/AdminServlet?action=deleteTeam&id=${team.teamId}"
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