<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us – KickOff</title>

    <!-- Google fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>

    <!-- External CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/AboutUs.css"/>
</head>

<body>

    <!-- Top navigation -->
    <nav class="navbar">

        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/home" class="navbar_logo">
            Kick<span>Off</span>
        </a>

        <!-- Navigation links -->
        <ul class="navbar_links">
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
            <li><a href="${pageContext.request.contextPath}/about" class="active">About</a></li>
            <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
        </ul>

        <!-- Login / dashboard section -->
        <div class="navbar_actions">

            <c:choose>

                <c:when test="${sessionScope.loggedIn eq true}">

                    <span class="welcome_text">
                        Hi, ${sessionScope.firstName}
                    </span>

                    <c:choose>

                        <c:when test="${sessionScope.role eq 'admin'}">
                            <a href="${pageContext.request.contextPath}/admin" class="login_btn">
                                Dashboard
                            </a>
                        </c:when>

                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/profile" class="login_btn">
                                Dashboard
                            </a>
                        </c:otherwise>

                    </c:choose>

                    <a href="${pageContext.request.contextPath}/logout" class="register_btn">
                        Logout
                    </a>

                </c:when>

                <c:otherwise>

                    <a href="${pageContext.request.contextPath}/login" class="login_btn">
                        Login
                    </a>

                    <a href="${pageContext.request.contextPath}/register" class="register_btn">
                        Register
                    </a>

                </c:otherwise>

            </c:choose>

        </div>

    </nav>

    <!-- Main about section -->
    <section class="about_section">

        <!-- Left content -->
        <div class="about_content">

            <h1>
                About <span class="highlight">KickOff</span>
            </h1>

            <p class="intro_text">
                KickOff is a modern futsal ground booking platform designed to
                make sports more accessible, organized, and enjoyable for everyone.
            </p>

            <div class="about_box">

                <h3>Who We Are</h3>

                <p>
                    We are a team of passionate football lovers who wanted to
                    simplify the process of finding and booking futsal grounds
                    in Kathmandu. Our platform helps players discover available
                    grounds, check schedules, and reserve slots instantly.
                </p>

            </div>

            <div class="about_box">

                <h3>Our Mission</h3>

                <p>
                    Our mission is to connect sports enthusiasts with quality
                    playing venues while making the booking process quick,
                    simple, and reliable.
                </p>

            </div>

            <div class="about_box">

                <h3>Why Choose KickOff?</h3>

                <ul class="features_list">
                    <li>Easy online booking system</li>
                    <li>Fast and secure reservations</li>
                    <li>Modern and user-friendly interface</li>
                    <li>Access to multiple futsal grounds</li>
                    <li>Real-time slot availability</li>
                </ul>

            </div>

        </div>

        <!-- Right side card -->
        <div class="about_card">

            <h2>Kick<span>Off</span></h2>

            <p>
                Play More. Wait Less.
            </p>

            <div class="card_info">

                <div class="info_item">
                    <h4>Location</h4>
                    <p>Kathmandu, Nepal</p>
                </div>

                <div class="info_item">
                    <h4>Email</h4>
                    <p>support@kickoff.com</p>
                </div>

                <div class="info_item">
                    <h4>Support Hours</h4>
                    <p>9:00 AM – 6:00 PM</p>
                </div>

            </div>

        </div>

    </section>

    <!-- Footer -->
    <footer class="footer">

        <a href="${pageContext.request.contextPath}/home" class="footer_logo">
            Kick<span>Off</span>
        </a>

        <ul class="footer_links">
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/grounds">Grounds</a></li>
            <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
        </ul>

        <p class="footer_copy">
            © 2026 KickOff. All rights reserved.
        </p>

    </footer>

</body>
</html>