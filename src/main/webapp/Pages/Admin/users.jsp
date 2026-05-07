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
      <a href="${pageContext.request.contextPath}/listUsers"    class="sidebar_item active">Users</a>
      <a href="${pageContext.request.contextPath}/listGrounds"  class="sidebar_item">Grounds</a>
      <a href="${pageContext.request.contextPath}/listTeams"    class="sidebar_item">Teams</a>
      <a href="${pageContext.request.contextPath}/listBookings" class="sidebar_item">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/logout"       class="sidebar_item">Logout</a>
    </aside>

    <main class="main">
      <p class="page_title">Users</p>

      <%-- Success / Error messages --%>
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

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
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${empty requestScope.users}">
                <tr>
                  <td colspan="9" style="text-align:center; color:#888;">No users registered yet.</td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="user" items="${requestScope.users}" varStatus="loop">
                  <tr>
                    <td>${loop.count}</td>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>${user.sport}</td>
                    <td><span class="badge badge_blue">${user.role}</span></td>
                    <td>${user.joinedDate}</td>
                    <td>
                      <c:choose>
                        <c:when test="${user.active}">
                          <span class="badge badge_green">Active</span>
                        </c:when>
                        <c:otherwise>
                          <span class="badge badge_red">Inactive</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td style="display:flex; gap:8px;">
                      <c:choose>
                        <c:when test="${user.active}">
                          <a href="${pageContext.request.contextPath}/deleteUser?id=${user.userId}"
                             class="btn btn_red"
                             onclick="return confirm('Deactivate this user?')">Deactivate</a>
                        </c:when>
                        <c:otherwise>
                          <a href="${pageContext.request.contextPath}/restoreUser?id=${user.userId}"
                             class="btn btn_green"
                             onclick="return confirm('Restore this user?')">Restore</a>
                        </c:otherwise>
                      </c:choose>
                    </td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
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