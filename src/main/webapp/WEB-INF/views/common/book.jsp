<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 6/10/2023
  Time: 10:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="card position-relative mx-auto border-warning text-white bg-transparent book rounded-3 overflow-hidden mt-3" style="width: 18rem;">
    <div class="position-absolute w-100 h-100 book-content-blur"></div>
    <a href="${pageContext.servletContext.contextPath}/books/${param.maSach}" class="card-header p-0 border-bottom border-secondary" style="height: 280px; z-index: 1000;">
        <img draggable="false" src="${pageContext.servletContext.contextPath}/images/${param.img}" class="book-img-md " alt="${param.tenSach}">
    </a>
    <div class="card-body">
        <p class="card-text fs-6">NXB: ${param.nxb}</p>
        <a href="${pageContext.servletContext.contextPath}/books/${param.maSach}" class="card-title custom-truncator text-decoration-none"
           style="font-family: Poppins,serif; height: 50px; font-size: 1.2rem !important;">${param.tenSach}</a>
        <p class="card-text fs-5" style="height: 42px;">
            <span class="float-start ">Còn lại: <b class="text-warning">${param.soLuong}</b></span>
            <span class="float-end text-danger fw-bold">$${param.gia}</span>
        </p>
    </div>
    <div class="btn-book">
        <a type="button" href="${pageContext.servletContext.contextPath}/books/${param.maSach}" class="btn fs-4 btn-light d-block mb-3"
           data-bs-toggle="tooltip" data-bs-placement="right"
           data-bs-title="Book info" data-bs-custom-class="custom-tooltip">
            <i class="bi bi-info-circle-fill"></i>
        </a>
        <a type="button" href="${pageContext.servletContext.contextPath}/cart/add/${param.maSach}?quantity=1&from=${from}"
           class="btn d-block fs-4 btn-light" id="addToCart" data-bs-toggle="tooltip"
           data-bs-placement="right" data-bs-title="Add to cart"
           data-bs-custom-class="custom-tooltip">
            <i class="bi bi-cart-plus-fill"></i>
        </a>
    </div>
</div>