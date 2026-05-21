<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Importing JSTL core library for loops, conditions, and other JSP tags -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Bookings - KickOff Admin</title>
  <!-- Google fonts used for styling -->
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <!-- External CSS file for admin dashboard design -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admindashboard.css"/>
</head>
<body>
<!-- NAVBAR SECTION -->
  <nav class="navbar">
    <!-- Website logo -->
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <!-- Navigation links -->
    <ul class="navbar_links">
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
      <li><a href="${pageContext.request.contextPath}/about">About</a></li>
       <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
    </ul>
    <!-- Admin badge -->
    <span class="admin_badge">Admin</span>
  </nav>
<!-- MAIN LAYOUT -->
  <div class="layout">
    <!-- Sidebar menu for admin actions -->
    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/admin"                          class="sidebar_item">Overview</a>
      <a href="${pageContext.request.contextPath}/admin?action=listUsers"         class="sidebar_item">Users</a>
      <a href="${pageContext.request.contextPath}/admin?action=listGrounds"       class="sidebar_item">Grounds</a>
      <a href="${pageContext.request.contextPath}/admin?action=listBookings"      class="sidebar_item active">Booking Requests</a>
      <a href="${pageContext.request.contextPath}/logout"                         class="sidebar_item">Logout</a>
    </aside>

    <main class="main">
      <!-- MAIN CONTENT -->
      <p class="page_title">Booking Requests</p>
      <!-- Success message shown after successful action -->
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <!-- Remove message after displaying once -->
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <!-- Error message shown if action fails -->
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>
      <!-- FILTER BUTTONS -->
      <div class="filter_tabs">
        <!--BUTTONS -->
        <button class="btn btn_primary" onclick="filterBookings('all')">All</button>
        <button class="btn btn_outline" onclick="filterBookings('pending')">Pending</button>
        <button class="btn btn_green"   onclick="filterBookings('confirmed')">Approved</button>
        <button class="btn btn_red"     onclick="filterBookings('cancelled')">Cancelled</button>
      </div>

      <div id="bookingsList">
        <c:choose>

          <!-- Show message if there are no bookings -->
          <c:when test="${empty bookings}">
            <div class="empty">No booking requests yet.</div>
          </c:when>
          <c:otherwise>
            <!-- Loop through all booking records -->
            <c:forEach var="booking" items="${bookings}">
              <!-- Store booking status for filtering using JavaScript -->
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
                    <!-- If booking is already approved -->
                    <c:when test="${booking.status eq 'confirmed'}">
                      <span class="badge badge_green">Approved</span>
                    </c:when>
                    <!-- Pending bookings can be approved or rejected -->
                    <c:when test="${booking.status eq 'pending'}">
                      <span class="badge badge_yellow">Pending</span>
                      <!-- Pass booking id to approve action -->
                      <a href="${pageContext.request.contextPath}/admin?action=approveBooking&id=${booking.bookingId}"
                         class="btn btn_green">Approve</a>
                      <!-- Pass booking id to reject action -->
                      <a href="${pageContext.request.contextPath}/admin?action=rejectBooking&id=${booking.bookingId}"
                         class="btn btn_red">Reject</a>
                    </c:when>
                    <c:otherwise>
                      <!-- Remaining bookings are treated as cancelled -->
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
    // Filters booking cards according to selected status
    function filterBookings(status) {
      var items = document.querySelectorAll("#bookingsList .list_item");
      // Show all items or matching status only
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