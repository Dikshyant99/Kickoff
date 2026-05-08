<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Bookings - KickOff Admin</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admindashboard.css"/>
</head>
<body>

  <nav class="navbar">
    <a href="${pageContext.request.contextPath}/HomeServlet" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/HomeServlet">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/GroundServlet">Grounds</a></li>
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
      <a href="${pageContext.request.contextPath}/AdminServlet?action=listTeams"     class="sidebar_item">Teams</a>
      <a href="${pageContext.request.contextPath}/AdminServlet?action=listBookings"  class="sidebar_item active">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/LogoutServlet"                     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <p class="page_title">Booking Requests</p>

      <%-- messages read from session, removed after display --%>
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <div class="filter_tabs">
        <button class="btn btn_primary" onclick="filterBookings('all')">All</button>
        <button class="btn btn_outline" onclick="filterBookings('pending')">Pending</button>
        <button class="btn btn_green"   onclick="filterBookings('confirmed')">Approved</button>
        <button class="btn btn_red"     onclick="filterBookings('cancelled')">Cancelled</button>
      </div>

      <%-- changed: requestScope.bookings replaced with sessionScope.bookings --%>
      <div id="bookingsList">
        <c:choose>
          <c:when test="${empty sessionScope.bookings}">
            <div class="empty">No booking requests yet.</div>
          </c:when>
          <c:otherwise>
            <c:forEach var="booking" items="${sessionScope.bookings}">
              <div class="list_item" data-status="${booking.status}">
                <div>
                  <div class="list_item_name">${booking.groundName}</div>
                  <div class="list_item_meta">
                    ${booking.userName} &nbsp;·&nbsp;
                    ${booking.slotDate} &nbsp;·&nbsp;
                    ${booking.startTime} - ${booking.endTime} &nbsp;·&nbsp;
                    Rs ${booking.totalPrice}
                  </div>
                </div>
                <div class="list_item_right">
                  <c:choose>
                    <c:when test="${booking.status eq 'confirmed'}">
                      <span class="badge badge_green">Approved</span>
                    </c:when>
                    <c:when test="${booking.status eq 'pending'}">
                      <span class="badge badge_yellow">Pending</span>
                      <a href="${pageContext.request.contextPath}/AdminServlet?action=approveBooking&id=${booking.bookingId}"
                         class="btn btn_green">Approve</a>
                      <a href="${pageContext.request.contextPath}/AdminServlet?action=rejectBooking&id=${booking.bookingId}"
                         class="btn btn_red">Reject</a>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge_red">Cancelled</span>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </div>

    </main>
  </div>

  <script>
    function filterBookings(status) {
      var items = document.querySelectorAll("#bookingsList .list_item");
      items.forEach(function(item) {
        if (status === 'all' || item.dataset.status === status) {
          item.style.display = "flex";
        } else {
          item.style.display = "none";
        }
      });
    }
  </script>

</body>
</html>