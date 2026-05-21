<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>404 – Page Not Found | KickOff</title>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@400;500;600&display=swap" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/error.css"/>
</head>
<body>

  <div class="error_wrapper">

    <p class="error_label">ERROR</p>
    <div class="error_code">404</div>

    <h1 class="error_title">Page Not Found</h1>
    <p class="error_subtitle">
      Looks like this page went out of bounds.<br/>
      The page you are looking for does not exist or has been moved.
    </p>

    <div class="error_actions">
      <a href="${pageContext.request.contextPath}/home" class="btn btn_primary">
        Go to Homepage
      </a>
      <a href="javascript:history.back()" class="btn btn_outline">
        Go Back
      </a>
    </div>

  </div>

</body>
</html>