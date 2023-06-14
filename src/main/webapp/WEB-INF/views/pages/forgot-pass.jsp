<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 6/13/2023
  Time: 9:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot password</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/bootstrap-icons-1.10.3/bootstrap-icons.css">
<%--    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/custom/style.css">--%>
</head>
<body class="bg-dark">
    <div class="d-flex justify-content-center py-4">
        <div class="card text-center mb-3 bg-dark text-white border border-1 border-light rounded-3" style="width: 22rem;">
            <div class="card-body">
                <h5 class="card-title">
                    <c:choose>
                        <c:when test="${to.contains('confirm')}">Update your password</c:when>
                        <c:otherwise>Enter your email</c:otherwise>
                    </c:choose>
                </h5>
                <p class="card-text"></p>
                <c:if test="${not empty error}">
                    <p class="text-danger">${error}</p>
                </c:if>
                <c:choose>
                    <c:when test="${to.contains('confirm')}">
                        <form:form method="post" action="/account/forgot/confirm" modelAttribute="forgotUser">
                            <div class="mb-3">
                                <form:input path="email" cssClass="form-control d-none" />
                                <form:errors path="email" cssClass="fs-6 text-danger d-none" />
                            </div>
                            <div class="mb-3">
                                <form:label path="password" cssClass="float-start form-label">New password</form:label>
                                <form:input path="password" type="password" cssClass="form-control" />
                                <form:errors path="password" cssClass="fs-6 text-danger" />
                            </div>
                            <div class="mb-3">
                                <label for="confirmPassword" class="float-start form-label">Confirm password</label>
                                <input name="confirmPassword" type="password" class="form-control" id="confirmPassword"/>
                                <s:hasBindErrors name="forgotUser">
                                    <c:forEach var="error" items="${errors.globalErrors}">
                                        <c:if test="${not empty error && error.code.equals('user.confirm.password')}">
                                            <span class="text-danger fs-6">${error.defaultMessage}</span>
                                        </c:if>
                                    </c:forEach>
                                </s:hasBindErrors>
                            </div>
                            <div class="mb-3">
                                <label for="code" class="form-label float-start">Confirm code</label>
                                <input name="code" class="form-control" id="code"/>
                                <s:hasBindErrors name="forgotUser">
                                    <c:forEach var="error" items="${errors.globalErrors}">
                                        <c:if test="${not empty error && error.code.equals('user.confirm.code')}">
                                            <span class="text-danger fs-6">${error.defaultMessage}</span>
                                        </c:if>
                                    </c:forEach>
                                </s:hasBindErrors>
                            </div>
                            <button class="btn btn-primary float-end">Submit</button>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <form action="/account/forgot" method="post">
                            <input name="email" type="text" inputmode="numeric" class="form-control">
<%--                            <span class="text-danger fs-5">${error}</span>--%>
                            <button class="btn btn-primary float-end mt-3">Confirm</button>
                        </form>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/bootstrap/js/jquery.min.js"></script>
<%--    <script src="${pageContext.servletContext.contextPath}/bootstrap/custom/index.js"></script>--%>
</body>
</html>
