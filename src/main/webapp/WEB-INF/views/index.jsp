<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/23/2023
  Time: 3:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>QLS</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/bootstrap-icons-1.10.3/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/custom/style.css">
</head>
<body class="bg-dark">
    <%@include file="header_footer/header.jsp"%>
    <jsp:include page="${view}" />
    
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/jquery.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/custom/index.js"></script>
</body>
</html>
