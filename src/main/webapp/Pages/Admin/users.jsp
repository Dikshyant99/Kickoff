<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Users – KickOff Admin</title>
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
      <a href="${pageContext.request.contextPath}/Pages/Admin/users.jsp"    class="sidebar_item active">Users</a>
      <a href="${pageContext.request.contextPath}/Pages/Admin/grounds.jsp"  class="sidebar_item">Grounds</a>
      <a href="${pageContext.request.contextPath}/Pages/Admin/teams.jsp"    class="sidebar_item">Teams</a>
      <a href="${pageContext.request.contextPath}/Pages/Admin/bookings.jsp" class="sidebar_item">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/LogoutServlet"            class="sidebar_item">Logout</a>
    </aside>

    <main class="main">
      <p class="page_title">Users</p>

      <input type="text" id="searchInput" class="search_input"
             placeholder="Search users..."
             onkeyup="filterTable()"/>

      <div class="table_wrap">
        <table id="usersTable">
          <thead>
            <tr>
              <th>#</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Sport</th>
              <th>Role</th>
              <th>Joined</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <%-- Static rows - will be replaced by c:forEach when servlet is wired --%>
            <tr>
              <td>1</td>
              <td>John Doe</td>
              <td>john@test.com</td>
              <td>9812345678</td>
              <td>Football</td>
              <td><span class="badge badge_blue">user</span></td>
              <td>2026-04-15</td>
              <td>
                <a href="${pageContext.request.contextPath}/AdminServlet?action=deleteUser&id=1"
                   class="btn btn_red"
                   onclick="return confirm('Delete this user?')">Delete</a>
              </td>
            </tr>
            <tr>
              <td>2</td>
              <td>Admin KickOff</td>
              <td>admin@kickoff.com</td>
              <td>9800000000</td>
              <td>Football</td>
              <td><span class="badge badge_green">admin</span></td>
              <td>2026-04-01</td>
              <td>
                <a href="${pageContext.request.contextPath}/AdminServlet?action=deleteUser&id=2"
                   class="btn btn_red"
                   onclick="return confirm('Delete this user?')">Delete</a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

    </main>
  </div>

  <script>
    function filterTable() {
      var input = document.getElementById("searchInput").value.toLowerCase();
      var rows  = document.querySelectorAll("#usersTable tbody tr");
      rows.forEach(function(row) {
        row.style.display =
          row.innerText.toLowerCase().includes(input) ? "" : "none";
      });
    }
  </script>

</body>
</html>