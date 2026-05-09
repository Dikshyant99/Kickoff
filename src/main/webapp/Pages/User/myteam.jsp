<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Team - KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userdashboard.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/myteam.css"/>
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
    <div class="navbar_avatar">
      <c:choose>
        <c:when test="${not empty sessionScope.image}">
          <img src="${pageContext.request.contextPath}/${sessionScope.image}"
               class="avatar_img" alt="Profile"/>
        </c:when>
        <c:otherwise>
          <img src="${pageContext.request.contextPath}/Assets/default-avatar.png"
               class="avatar_img" alt="Profile"/>
        </c:otherwise>
      </c:choose>
    </div>
  </nav>

  <div class="layout">

    <aside class="sidebar">
      <a href="${pageContext.request.contextPath}/profile"    class="sidebar_item">My Profile</a>
      <a href="${pageContext.request.contextPath}/myTeam"     class="sidebar_item active">My Team</a>
      <a href="${pageContext.request.contextPath}/myBookings" class="sidebar_item">My Bookings</a>
      <a href="${pageContext.request.contextPath}/logout"     class="sidebar_item">Logout</a>
    </aside>

    <main class="main">

      <%-- flash messages --%>
      <c:if test="${not empty sessionScope.successMsg}">
        <div class="msg_success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
      </c:if>
      <c:if test="${not empty sessionScope.errorMsg}">
        <div class="msg_error">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
      </c:if>

      <%-- ===== USER HAS A TEAM ===== --%>
      <c:if test="${not empty team}">

        <%-- team info card --%>
        <div class="profile_card">

          <div class="profile_top">
            <div class="profile_info">
              <h2 class="profile_name">${team.name}</h2>
              <p class="profile_role">${team.sportType} &nbsp;·&nbsp; ${team.location}</p>
            </div>
          </div>

          <hr class="profile_divider"/>

          <%-- stat grid --%>
          <div class="team_stat_grid">
            <div class="team_stat_box">
              <div class="team_stat_label">Players</div>
              <div class="team_stat_value">${team.currentPlayers}/${team.maxPlayers}</div>
            </div>
            <div class="team_stat_box">
              <div class="team_stat_label">Skill Level</div>
              <div class="team_stat_value" style="font-size:1rem;">${team.skillLevel}</div>
            </div>
            <div class="team_stat_box">
              <div class="team_stat_label">Recruitment</div>
              <div class="team_stat_value" style="font-size:1rem;">
                <c:choose>
                  <c:when test="${team.recruitmentStatus eq 'open'}">
                    <span class="badge badge_green">Open</span>
                  </c:when>
                  <c:otherwise>
                    <span class="badge badge_red">Closed</span>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
          </div>

          <hr class="profile_divider"/>

          <%-- action buttons --%>
          <div class="team_actions">
            <c:choose>
              <c:when test="${isCaptain}">
                <c:choose>
                  <c:when test="${team.recruitmentStatus eq 'open'}">
                    <a href="${pageContext.request.contextPath}/myTeam?action=closeRecruitment"
                       class="btn btn_outline">Close Recruitment</a>
                  </c:when>
                  <c:otherwise>
                    <a href="${pageContext.request.contextPath}/myTeam?action=openRecruitment"
                       class="btn btn_primary">Open Recruitment</a>
                  </c:otherwise>
                </c:choose>
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/myTeam?action=leaveTeam"
                   class="btn btn_red"
                   onclick="return confirm('Are you sure you want to leave this team?')">
                  Leave Team
                </a>
              </c:otherwise>
            </c:choose>
          </div>

        </div>

        <%-- members list --%>
        <div class="profile_card">
          <span class="section_tag">Members</span>
          <c:choose>
            <c:when test="${empty members}">
              <div style="color:#888; font-size:0.9rem;">No members yet.</div>
            </c:when>
            <c:otherwise>
              <c:forEach var="member" items="${members}">
                <div class="member_row">
                  <div class="member_avatar">
                    ${member.firstName.substring(0,1)}
                  </div>
                  <div style="flex:1;">
                    <div class="member_name">${member.firstName} ${member.lastName}</div>
                    <div class="member_meta">
                      ${member.role} &nbsp;·&nbsp; Joined ${member.joinedAt}
                    </div>
                  </div>
                  <c:choose>
                    <c:when test="${member.role eq 'captain'}">
                      <span class="badge badge_blue">Captain</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge_green">Member</span>
                    </c:otherwise>
                  </c:choose>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>

        <%-- join requests — captain only --%>
        <c:if test="${isCaptain}">
          <div class="profile_card">
            <span class="section_tag">
              Join Requests
              <c:if test="${not empty joinRequests}">
                (${joinRequests.size()})
              </c:if>
            </span>
            <c:choose>
              <c:when test="${empty joinRequests}">
                <div style="color:#888; font-size:0.9rem;">No pending requests.</div>
              </c:when>
              <c:otherwise>
                <c:forEach var="req" items="${joinRequests}">
                  <div class="request_row">
                    <div>
                      <div class="request_name">${req.firstName} ${req.lastName}</div>
                      <div class="request_sport">
                        ${req.sport} &nbsp;·&nbsp; ${req.skillLevel}
                      </div>
                    </div>
                    <div class="request_actions">
                      <a href="${pageContext.request.contextPath}/myTeam?action=acceptRequest&requestId=${req.requestId}"
                         class="btn btn_primary">Accept</a>
                      <a href="${pageContext.request.contextPath}/myTeam?action=rejectRequest&requestId=${req.requestId}"
                         class="btn btn_red">Reject</a>
                    </div>
                  </div>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </div>
        </c:if>

      </c:if>

      <%-- ===== USER HAS NO TEAM ===== --%>
      <c:if test="${empty team}">
        <c:choose>

          <%-- show create form --%>
          <c:when test="${showCreate}">
            <div class="create_form_card">
              <span class="section_tag">Create a New Team</span>
              <form action="${pageContext.request.contextPath}/myTeam" method="post">
                <input type="hidden" name="action" value="createTeam"/>

                <div class="form_row_2">
                  <div class="form_group">
                    <label>Team Name</label>
                    <input type="text" name="name" required placeholder="e.g. Thunder FC"/>
                  </div>
                  <div class="form_group">
                    <label>Sport Type</label>
                    <select name="sportType">
                      <option value="Football">Football</option>
                      <option value="Cricket">Cricket</option>
                      <option value="Basketball">Basketball</option>
                      <option value="Volleyball">Volleyball</option>
                      <option value="Tennis">Tennis</option>
                    </select>
                  </div>
                </div>

                <div class="form_row_2">
                  <div class="form_group">
                    <label>Location</label>
                    <input type="text" name="location" required placeholder="e.g. Kathmandu"/>
                  </div>
                  <div class="form_group">
                    <label>Skill Level</label>
                    <select name="skillLevel">
                      <option value="beginner">Beginner</option>
                      <option value="intermediate">Intermediate</option>
                      <option value="advanced">Advanced</option>
                    </select>
                  </div>
                </div>

                <div class="form_group">
                  <label>Max Players</label>
                  <input type="number" name="maxPlayers" required
                         min="2" max="30" placeholder="e.g. 11"/>
                </div>

                <div class="team_actions">
                  <button type="submit" class="btn btn_primary">Create Team</button>
                  <a href="${pageContext.request.contextPath}/myTeam"
                     class="btn btn_outline">Cancel</a>
                </div>

              </form>
            </div>
          </c:when>

          <%-- no team --%>
          <c:otherwise>
            <div class="no_team_card">
              <div class="no_team_icon"> </div>
              <div class="no_team_title">
              You are not part of any team yet </div>
              <div class="no_team_sub">
                Join an existing team or create your own and start recruiting players.
              </div>
              <div class="no_team_actions">
                <a href="${pageContext.request.contextPath}/teams"
                   class="btn btn_outline">Browse Teams</a>
                <a href="${pageContext.request.contextPath}/myTeam?action=showCreate"
                   class="btn btn_primary">Create Team</a>
              </div>
            </div>
          </c:otherwise>

        </c:choose>
      </c:if>

    </main>
  </div>

</body>
</html>