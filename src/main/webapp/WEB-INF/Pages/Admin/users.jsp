<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Users - KickOff Admin</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admindashboard.css"/>
  <style>
    body { background: #111; color: #fff; font-family: 'DM Sans', sans-serif; margin: 0; }

    .page_title {
      font-family: 'Bebas Neue', sans-serif;
      font-size: 1.8rem;
      letter-spacing: 2px;
      color: #fff;
      margin-bottom: 20px;
    }

    .msg_success {
      background: rgba(34,197,94,0.1);
      border: 1px solid rgba(34,197,94,0.25);
      color: #22c55e;
      padding: 10px 14px;
      border-radius: 6px;
      margin-bottom: 16px;
      font-size: 0.875rem;
    }

    .msg_error {
      background: rgba(239,68,68,0.1);
      border: 1px solid rgba(239,68,68,0.25);
      color: #ef4444;
      padding: 10px 14px;
      border-radius: 6px;
      margin-bottom: 16px;
      font-size: 0.875rem;
    }

    .search_input {
      width: 260px;
      padding: 9px 14px;
      background: #181818;
      border: 1px solid #232323;
      border-radius: 6px;
      color: #fff;
      font-family: 'DM Sans', sans-serif;
      font-size: 0.875rem;
      margin-bottom: 20px;
      outline: none;
    }
    .search_input:focus { border-color: #2979ff; }
    .search_input::placeholder { color: #555; }

    .table_wrap {
      background: #181818;
      border: 1px solid #232323;
      border-radius: 8px;
      overflow: hidden;
    }

    table { width: 100%; border-collapse: collapse; font-size: 0.875rem; }

    thead tr { border-bottom: 1px solid #232323; }

    thead th {
      padding: 13px 16px;
      text-align: left;
      font-size: 0.7rem;
      text-transform: uppercase;
      letter-spacing: 0.8px;
      color: #555;
      font-weight: 600;
    }

    tbody tr {
      border-bottom: 1px solid #1e1e1e;
      transition: background 0.15s;
    }
    tbody tr:last-child { border-bottom: none; }
    tbody tr:hover { background: #1e1e1e; }

    tbody td {
      padding: 13px 16px;
      color: #ccc;
      vertical-align: middle;
    }
    tbody td:first-child { color: #555; font-size: 0.8rem; }

    .user_name { color: #fff; font-weight: 500; }

    .role_admin { color: #2979ff; font-weight: 600; font-size: 0.82rem; }
    .role_user  { color: #8a8a8a; font-size: 0.82rem; }

    .status_dot {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      font-size: 0.82rem;
    }
    .status_dot::before {
      content: '';
      display: inline-block;
      width: 6px;
      height: 6px;
      border-radius: 50%;
    }
    .status_active { color: #22c55e; }
    .status_active::before { background: #22c55e; }

    .action_link {
      font-size: 0.82rem;
      font-weight: 600;
      text-decoration: none;
      background: none;
      border: none;
      cursor: pointer;
    }
    .action_deactivate { color: #ef4444; }
    .action_deactivate:hover { color: #ff6b6b; }
  </style>
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
  <span class="admin_badge">Admin</span>
</nav>

<div class="layout">
  <aside class="sidebar">
    <a href="${pageContext.request.contextPath}/admin"                     class="sidebar_item">Overview</a>
    <a href="${pageContext.request.contextPath}/admin?action=listUsers"    class="sidebar_item active">Users</a>
    <a href="${pageContext.request.contextPath}/admin?action=listGrounds"  class="sidebar_item">Grounds</a>
    <a href="${pageContext.request.contextPath}/admin?action=listBookings" class="sidebar_item">Booking Requests</a>
    <a href="${pageContext.request.contextPath}/logout"                    class="sidebar_item">Logout</a>
  </aside>

  <main class="main">
    <p class="page_title">Users</p>

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
          <c:when test="${empty users}">
            <tr>
              <td colspan="9" style="color:#555; text-align:center; padding:24px;">No users found.</td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="user" items="${users}" varStatus="loop">
              <tr>
                <td>${loop.count}</td>
                <td class="user_name">${user.firstName} ${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.sport}</td>
                <td>
                  <c:choose>
                    <c:when test="${user.role eq 'admin'}">
                      <span class="role_admin">admin</span>
                    </c:when>
                    <c:otherwise>
                      <span class="role_user">user</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>${user.createdAt}</td>
                <td><span class="status_dot status_active">Active</span></td>
                <td>
                  <a href="${pageContext.request.contextPath}/admin?action=deleteUser&id=${user.userId}"
                     class="action_link action_deactivate"
                     onclick="return confirm('Deactivate this user?')">Deactivate</a>
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