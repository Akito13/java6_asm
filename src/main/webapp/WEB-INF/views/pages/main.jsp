<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/24/2023
  Time: 10:28 PM
  To change this template use File | Settings | File Templates.
--%>
<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 container-fluid mx-auto">
  <c:forEach items="${books}" var="book">
    <div class="col mb-3">
      <jsp:include page="../common/book.jsp" >
        <jsp:param name="maSach" value="${book.maSach}"/>
        <jsp:param name="img" value="${book.img}"/>
        <jsp:param name="tenSach" value="${book.tenSach}"/>
        <jsp:param name="nxb" value="${book.nxb}"/>
        <jsp:param name="soLuong" value="${book.soLuong}"/>
        <jsp:param name="gia" value="${book.gia}"/>
      </jsp:include>
<%--      <div class="card position-relative mx-auto border-warning text-white bg-transparent book rounded-3 overflow-hidden" style="width: 18rem;">--%>
<%--        <div class="position-absolute w-100 h-100 book-content-blur"></div>--%>
<%--        <a href="${pageContext.servletContext.contextPath}/books/${book.maSach}" class="card-header p-0 border-bottom border-secondary" style="height: 280px; z-index: 1000;">--%>
<%--          <img draggable="false" src="${pageContext.servletContext.contextPath}/images/${book.img}" class="book-img-md " alt="${book.tenSach}">--%>
<%--        </a>--%>
<%--        <div class="card-body">--%>
<%--          <p class="card-text fs-6">NXB: ${book.nxb}</p>--%>
<%--          <a href="${pageContext.servletContext.contextPath}/books/${book.maSach}" class="card-title custom-truncator text-decoration-none"--%>
<%--             style="font-family: Poppins,serif; height: 50px; font-size: 1.2rem !important;">${book.tenSach}</a>--%>
<%--          <p class="card-text fs-5" style="height: 42px;">--%>
<%--            <span class="float-start ">Còn lại: <b class="text-warning">${book.soLuong}</b></span>--%>
<%--            <span class="float-end text-danger fw-bold">$${book.gia}</span>--%>
<%--          </p>--%>
<%--        </div>--%>
<%--        <div class="btn-book">--%>
<%--          <a type="button" href="${pageContext.servletContext.contextPath}/books/${book.maSach}" class="btn fs-4 btn-light d-block mb-3"--%>
<%--             data-bs-toggle="tooltip" data-bs-placement="right"--%>
<%--             data-bs-title="Book info" data-bs-custom-class="custom-tooltip">--%>
<%--            <i class="bi bi-info-circle-fill"></i>--%>
<%--          </a>--%>
<%--          <a type="button" href="${pageContext.servletContext.contextPath}/cart/add/${book.maSach}?quantity=1"--%>
<%--             class="btn d-block fs-4 btn-light" id="addToCart" data-bs-toggle="tooltip"--%>
<%--             data-bs-placement="right" data-bs-title="Add to cart"--%>
<%--             data-bs-custom-class="custom-tooltip">--%>
<%--            <i class="bi bi-cart-plus-fill"></i>--%>
<%--          </a>--%>
<%--        </div>--%>
<%--      </div>--%>
    </div>
  </c:forEach>
</div>
