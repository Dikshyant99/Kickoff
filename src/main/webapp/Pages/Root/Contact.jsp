# contact.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contact Us - KickOff</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Contact.css"/>
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="nav-links">

        <a href="index.jsp">Home</a>
        <a href="#">Grounds</a>
        <a href="#">Teams</a>
        <a href="#">Find Players</a>
        <a href="contact.jsp">Contact</a>
    </div>
</div>

<!-- MAIN -->
<div class="container">

    <!-- LEFT SIDE INFO -->
    <div class="info-panel">
        <h3>Kick<span class="highlight">Off</span></h3>

        <div class="info-block">
            <p class="info-label">Address</p>
            <p class="info-value">
                Kamalpokhari, Kathmandu<br>
                Nepal 44600
            </p>
        </div>

        <div class="info-block">
            <p class="info-label">Email</p>
            <p class="info-value">
                support@kickoff.com
            </p>
        </div>

        <div class="info-block">
            <p class="info-label">Phone</p>
            <p class="info-value">
                +977 9818674581
            </p>
        </div>

        <div class="info-block">
            <p class="info-label">Office Hours</p>
            <p class="info-value">
                Monday – Friday<br>
                9:00 AM – 6:00 PM
            </p>
        </div>
    </div>

    <!-- RIGHT SIDE FORM -->
    <div class="card">

        <h2>Contact <span class="highlight">Us</span></h2>
        <p>We usually respond within 24 hours</p>

        <div class="message success" id="successMessage">
            Your message has been submitted successfully.
        </div>

        <div class="contact-form">

            <div class="row">
                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" name="firstName" required>
                </div>

                <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" name="lastName" required>
                </div>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>

            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone">
            </div>

            <div class="form-group">
                <label>Subject</label>
                <select name="subject" required>
                    <option value="">Select a topic</option>
                    <option value="general">General Inquiry</option>
                    <option value="partnership">Partnership</option>
                    <option value="support">Support</option>
                </select>
            </div>

            <div class="form-group">
                <label>Message</label>
                <textarea name="message" rows="4" required></textarea>
            </div>

            <button type="button" class="btn" onclick="showMessage()">
                Send Message
            </button>

        </div>

    </div>

</div>

</body>
</html>
