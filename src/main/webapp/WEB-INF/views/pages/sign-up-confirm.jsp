<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 6/8/2023
  Time: 5:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirm Account</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/css/bootstrap.min.css">
</head>
<body class="bg-dark">
    <div class="d-flex justify-content-center py-4">
        <div class="card text-center mb-3 bg-dark text-white border border-1 border-light rounded-3" style="width: 22rem;">
            <div class="card-body">
                <h5 class="card-title">Email confirmation</h5>
                <p class="card-text">An auto generated code has been sent to email: ${signupUser.email}</p>
                <c:if test="${not empty error}">
                    <p class="text-danger">${error}</p>
                </c:if>
                <form action="/account/signup/confirm" method="post">
                    <input name="code" type="text" inputmode="numeric" class="form-control">
                    <button class="btn btn-primary float-end mt-3">Confirm</button>
                </form>
            </div>
        </div>
    </div>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/jquery.min.js"></script>
</body>
</html>
