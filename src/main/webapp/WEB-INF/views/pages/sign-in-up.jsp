<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600&display=swap"
        rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/bootstrap/custom/sign-in-up.css">
  <title>Account login</title>
</head>
<body>
<div class="container">
<%--  <h3 style="text-align: center; color: #fff;">${msg }</h3>--%>
<%--  <h3 style="text-align: center; color: #fff;">${unknownError }</h3>--%>
  <div class="dark-bg">
    <div class="box sign-in">
      <h2>Already have an Account?</h2>
      <button class="signInBtn">Sign in</button>
    </div>
    <div class="box sign-up">
      <h2>Don't have an Account?</h2>
      <button class="signUpBtn">Sign up</button>
    </div>
  </div>
  <div class="frmBox ${active}">
    <div class="form signInForm">
      <form:form action="/account/login" modelAttribute="user" method="post">
        <h3>Sign In</h3>
        <div class="input-container">
          <form:input path="email" type="email" placeholder="Email" />
        </div>
        <div class="input-container">
          <form:input path="password" type="password" placeholder="Password"/>
        </div>
        <div class="input-container">
          <span class="input-error">
              <c:if test="${not empty errors}">
                <c:out value="${errors[0]}"></c:out>
              </c:if>
          </span>
        </div>
        <div class="input-container">
          <input type="submit" value="Login">
        </div>
        <div>
          <a href="#" class="forgot">Forgot Password?</a>
          <a href="${pageContext.servletContext.contextPath}/" class="home">Home</a>
        </div>
      </form:form>
    </div>
    <div class="form signUpForm">
      <form:form action="/account/signup" method="post" modelAttribute="user">
        <h3>Sign Up</h3>
        <div class="input-container">
          <form:input type="text" placeholder="Username" path="tenKH" />
          <span class="input-error">${signUpIdError}</span>
        </div>
        <div class="input-container">
          <form:input path="email" type="email" placeholder="Email Address" />
          <span class="input-error">${signUpEmailError}</span>
        </div>
        <div class="input-container">
          <form:input path="diaChi" type="text" placeholder="Address"/>
          <span class="input-error">${signUpNameError}</span>
        </div>
        <div class="input-container">
          <form:input path="sdt" type="text" placeholder="Phone number"/>
          <span class="input-error">${signUpPwdError}</span>
        </div>
        <div class="input-container">
          <form:input path="password" type="password" placeholder="Password" />
          <span class="input-error">${signUpConfirmError}</span>
        </div>
        <div class="input-container">
          <input name="confirmPassword" type="password" placeholder="Confirm Password">
          <span class="input-error">${signUpConfirmError}</span>
        </div>
        <div class="input-container">
          <input type="submit" value="Register">
        </div>
        <div class="input-container">
          <span class="input-error">${unknownError}</span>
        </div>
      </form:form>
    </div>
  </div>
</div>
<script>
  const signInBtn = document.querySelector('.signInBtn');
  const signUpBtn = document.querySelector('.signUpBtn')
  const frmBox = document.querySelector('.frmBox')
  const body = document.querySelector('body')
  signUpBtn.addEventListener('click', (e) => {
    frmBox.classList.add('active')
    body.classList.add('active')
  })
  signInBtn.addEventListener('click', e => {
    frmBox.classList.remove('active')
    body.classList.remove('active')
  })
</script>
</body>
</html>