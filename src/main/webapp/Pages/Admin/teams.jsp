<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Teams – KickOff Admin</title>
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
      <a href="${pageContext.request.contextPath}/AdminServlet"             class="sidebar_item">Overview</a>
      <a href="${pageContext.request.contextPath}/Pages/Admin/users.jsp"    class="sidebar_item">Users</a>
      <a href="${pageContext.request.contextPath}/Pages/Admin/grounds.jsp"  class="sidebar_item">Grounds</a>
      <a href="${pageContext.request.contextPath}/Pages/Admin/teams.jsp"    class="sidebar_item active">Teams</a>
      <a href="${pageContext.request.contextPath}/Pages/Admin/bookings.jsp" class="sidebar_item">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/LogoutServlet"            class="sidebar_item">Logout</a>
    </aside>

    <main class="main">
      <p class="page_title">Teams</p>

      <input type="text" id="teamSearch" class="search_input"
             placeholder="Search teams..."
             onkeyup="filterTeams()"/>

      <div id="teamsList">

        <div class="list_item">
          <div>
            <div class="list_item_name">Thunder FC</div>
            <div class="list_item_meta">
              Football &nbsp;·&nbsp; Kathmandu &nbsp;·&nbsp;
              8/11 players &nbsp;·&nbsp; Captain: John Doe
            </div>
          </div>
          <div class="list_item_right">
            <span class="badge badge_green">Open</span>
            <a href="#" class="btn btn_red"
               onclick="return confirm('Delete this team?')">Delete</a>
          </div>
        </div>

        <div class="list_item">
          <div>
            <div class="list_item_name">Storm Cricket XI</div>
            <div class="list_item_meta">
              Cricket &nbsp;·&nbsp; Lalitpur &nbsp;·&nbsp;
              11/11 players &nbsp;·&nbsp; Captain: Ram Sharma
            </div>
          </div>
          <div class="list_item_right">
            <span class="badge badge_red">Closed</span>
            <a href="#" class="btn btn_red"
               onclick="return confirm('Delete this team?')">Delete</a>
          </div>
        </div>

        <div class="list_item">
          <div>
            <div class="list_item_name">Hoops KTM</div>
            <div class="list_item_meta">
              Basketball &nbsp;·&nbsp; Kathmandu &nbsp;·&nbsp;
              3/5 players &nbsp;·&nbsp; Captain: Sita Rai
            </div>
          </div>
          <div class="list_item_right">
            <span class="badge badge_green">Open</span>
            <a href="#" class="btn btn_red"
               onclick="return confirm('Delete this team?')">Delete</a>
          </div>
        </div>

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