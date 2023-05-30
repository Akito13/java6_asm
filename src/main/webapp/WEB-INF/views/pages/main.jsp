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
    <div class="col rounded-3 mb-3">
      <div class="card mx-auto border-light text-white bg-transparent" style="width: 18rem;">
        <a href="#" class="card-header p-0 border-bottom border-light overflow-hidden" style="height: 280px; z-index: 1000;">
          <img src="${pageContext.servletContext.contextPath}/images/${book.img}" class="book-img-md" alt="${book.tenSach}">
        </a>
        <div class="card-body">
          <h5 class="card-title custom-truncator" style="height: 50px;">${book.tenSach}</h5>
          <p class="card-text fs-5" style="height: 42px;">
            <span class="float-start ">Còn lại: <b class="text-success">${book.soLuong}</b></span>
            <span class="float-end text-danger fw-bold">$${book.gia}</span>
          </p>
          <div>
            <button href="#" class="btn btn-outline-info">Details</button>
            <a href="${pageContext.servletContext.contextPath}/cart/add/${book.maSach}?quantity=1" class="btn btn-outline-light" id="addToCart">Add to cart</a>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>
</div>
