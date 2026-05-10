<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KICKOFF</title>
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Homepage.css"/>
</head>
<body>

<!-- ===== NAVBAR ===== -->
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="navbar_logo">Kick<span>Off</span></a>
    <ul class="navbar_links">
        <li><a href="${pageContext.request.contextPath}/home" class="active">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
        <li><a href="${pageContext.request.contextPath}/about">About</a></li>
    </ul>
    <div class="navbar_actions">
        <c:choose>
            <c:when test="${sessionScope.loggedIn eq true}">
                <span class="welcome_text">Hi, ${sessionScope.firstName}</span>
                <c:choose>
                    <c:when test="${sessionScope.role eq 'admin'}">
                        <a href="${pageContext.request.contextPath}/admin" class="login_btn">Dashboard</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/profile" class="login_btn">Dashboard</a>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/logout" class="register_btn">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login"    class="login_btn">Login</a>
                <a href="${pageContext.request.contextPath}/register" class="register_btn">Register</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>

<!-- ===== HERO ===== -->
<section class="hero">
    <h1 class="hero_title">
        BOOK &nbsp;·&nbsp; PLAY &nbsp;·&nbsp; <span>Connect</span>
    </h1>
    <p class="hero_subtitle">
        Find sports grounds, build your team, and challenge others to a match.
    </p>

    <c:if test="${sessionScope.loggedIn eq true}">
        <div class="hero_welcome">
            Welcome back, ${sessionScope.firstName}!
        </div>
    </c:if>

    <div class="hero_search_bar">
        <form action="${pageContext.request.contextPath}/grounds"
              method="get" style="display:flex; gap:10px; width:100%;">
            <input type="text" name="q"
                   placeholder="Search grounds, sports, location..."/>
            <button type="submit" class="search_btn">Search</button>
        </form>
    </div>

    <div class="hero_tags">
        <a href="${pageContext.request.contextPath}/grounds?sport=Football"   class="tag">Football</a>
        <a href="${pageContext.request.contextPath}/grounds?sport=Cricket"    class="tag">Cricket</a>
        <a href="${pageContext.request.contextPath}/grounds?sport=Basketball" class="tag">Basketball</a>
        <a href="${pageContext.request.contextPath}/grounds?sport=Tennis"     class="tag">Tennis</a>
    </div>
</section>

<hr class="section_divider"/>

<!-- ===== AVAILABLE GROUNDS ===== -->
<div class="section">
    <div class="section_header">
        <h2 class="section_title">Available Grounds</h2>
        <a href="${pageContext.request.contextPath}/grounds" class="section_link">View all →</a>
    </div>
    <div class="card_grid">
        <c:choose>
            <c:when test="${empty grounds}">
                <%-- Static fallback --%>
                <a href="${pageContext.request.contextPath}/grounds" class="ground_card">
                    <div class="ground_card_image">
                        <img src="${pageContext.request.contextPath}/Assets/oldtrff.jpg"
                             style="width:100%; height:100%; object-fit:cover; object-position:bottom;"
                             alt="City Football Arena"/>
                    </div>
                    <div class="ground_card_body">
                        <div class="ground_card_name">City Football Arena</div>
                        <div class="ground_card_meta">Kathmandu · Football</div>
                        <div class="ground_card_footer">
                            <span class="ground_card_price">Rs 800/hr</span>
                            <span class="badge badge_green">Available</span>
                        </div>
                    </div>
                </a>
                <a href="${pageContext.request.contextPath}/grounds" class="ground_card">
                    <div class="ground_card_image">
                        <img src="${pageContext.request.contextPath}/Assets/cricket.jpg"
                             style="width:100%; height:100%; object-fit:cover; object-position:bottom;"
                             alt="Lalitpur Cricket Ground"/>
                    </div>
                    <div class="ground_card_body">
                        <div class="ground_card_name">Lalitpur Cricket Ground</div>
                        <div class="ground_card_meta">Lalitpur · Cricket</div>
                        <div class="ground_card_footer">
                            <span class="ground_card_price">Rs 1200/hr</span>
                            <span class="badge badge_green">Available</span>
                        </div>
                    </div>
                </a>
                <a href="${pageContext.request.contextPath}/grounds" class="ground_card">
                    <div class="ground_card_image">
                        <img src="${pageContext.request.contextPath}/Assets/basketball.jpg"
                             style="width:100%; height:100%; object-fit:cover; object-position:center;"
                             alt="Bhaktapur Sports Court"/>
                    </div>
                    <div class="ground_card_body">
                        <div class="ground_card_name">Bhaktapur Sports Court</div>
                        <div class="ground_card_meta">Bhaktapur · Basketball</div>
                        <div class="ground_card_footer">
                            <span class="ground_card_price">Rs 600/hr</span>
                            <span class="badge badge_yellow">Few Slots</span>
                        </div>
                    </div>
                </a>
            </c:when>
            <c:otherwise>
                <c:forEach var="ground" items="${grounds}">
                    <a href="${pageContext.request.contextPath}/grounds?id=${ground.groundId}"
                       class="ground_card">
                        <div class="ground_card_image">
                            <c:choose>
                                <c:when test="${not empty ground.imageUrl}">
                                    <img src="${pageContext.request.contextPath}/${ground.imageUrl}"
                                         style="width:100%; height:100%; object-fit:cover;"
                                         alt="${ground.name}"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/Assets/oldtrff.jpg"
                                         style="width:100%; height:100%; object-fit:cover;"
                                         alt="${ground.name}"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="ground_card_body">
                            <div class="ground_card_name">${ground.name}</div>
                            <div class="ground_card_meta">${ground.city} · ${ground.sportTypes}</div>
                            <div class="ground_card_footer">
                                <span class="ground_card_price">Rs ${ground.pricePerHour}/hr</span>
                                <c:choose>
                                    <c:when test="${ground.isActive eq true}">
                                        <span class="badge badge_green">Available</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge_red">Unavailable</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<hr class="section_divider"/>

<!-- ===== OPEN TEAMS RECRUITING ===== -->
<div class="section">
    <div class="section_header">
        <h2 class="section_title">Open Teams Recruiting</h2>
        <a href="${pageContext.request.contextPath}/teams" class="section_link">View all →</a>
    </div>
    <div class="card_grid">
        <c:choose>
            <c:when test="${empty teams}">
                <%-- Static fallback --%>
                <a href="${pageContext.request.contextPath}/teams" class="team_card">
                    <div class="team_card_name">Thunder FC</div>
                    <div class="team_card_meta">
                        Football · Needs 3 players<br/>
                        Kathmandu · Intermediate
                    </div>
                    <span class="badge badge_blue">Recruiting</span>
                </a>
                <a href="${pageContext.request.contextPath}/teams" class="team_card">
                    <div class="team_card_name">Storm Cricket XI</div>
                    <div class="team_card_meta">
                        Cricket · Needs 2 players<br/>
                        Lalitpur · Advanced
                    </div>
                    <span class="badge badge_blue">Recruiting</span>
                </a>
                <a href="${pageContext.request.contextPath}/teams" class="team_card">
                    <div class="team_card_name">Hoops KTM</div>
                    <div class="team_card_meta">
                        Basketball · Full team<br/>
                        Kathmandu · Beginner
                    </div>
                    <span class="badge badge_red">Closed</span>
                </a>
            </c:when>
            <c:otherwise>
                <c:forEach var="team" items="${teams}">
                    <a href="${pageContext.request.contextPath}/teams?id=${team.teamId}"
                       class="team_card">
                        <div class="team_card_name">${team.name}</div>
                        <div class="team_card_meta">
                            ${team.sportType} · Needs
                            ${team.maxPlayers - team.currentPlayers} players<br/>
                            ${team.location} · ${team.skillLevel}
                        </div>
                        <c:choose>
                            <c:when test="${team.recruitmentStatus eq 'open'}">
                                <span class="badge badge_blue">Recruiting</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge_red">Closed</span>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<hr class="section_divider"/>

<!-- ===== FOOTER ===== -->
<footer class="footer">
    <a href="${pageContext.request.contextPath}/home" class="footer_logo">Kick<span>Off</span></a>
    <ul class="footer_links">
        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/about">About</a></li>
        <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
    </ul>
    <p class="footer_copy">© 2026 KickOff. All rights reserved.</p>
</footer>

</body>
</html>