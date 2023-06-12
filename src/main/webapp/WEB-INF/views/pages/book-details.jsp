<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 6/10/2023
  Time: 1:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${not empty book}">
        <article class="row row-cols-lg-2 row-cols-1 px-lg-3 px-4 mt-5
                    border-bottom border-1 border-secondary pb-5 mx-auto" style="width: 90%">
            <aside class="col col-lg-5">
                <div class="w-75 float-lg-end mx-auto text-center text-lg-start" style="height: 420px;">
                    <img src="/images/${book.img}" alt="Book thumbnail" class="h-100 img-fluid">
                </div>
            </aside>
            <main class="col col-lg-7 text-white py-3 px-0">
                <h2 class="text-center text-lg-start" id="book-${book.maSach}">${book.tenSach}</h2>
                <p class="text-secondary fw-semibold fs-5">Author: ${book.tacGia}</p>
                <p class="text-secondary fw-semibold fs-5">Publish date: ${book.nxb}</p>
                <p class="fs-3 fw-semibold mt-3" style="color: #73BBC9">
                    Price: <fmt:formatNumber type="currency" value="${book.gia}" pattern="$##.##"/>
                    <span class="fs-6 fw-normal text-white">(Remaining: ${book.soLuong})</span>
                </p>
                <form action="/cart/add/${book.maSach}" method="get">
                    <div class="d-flex w-50 justify-content-start align-items-center">
                        <label class="fs-5 " for="qt">Amount: </label>
                        <input class="form-control w-25 ms-3" id="qt" pattern="[0-9]*" type="text" name="quantity" inputmode="numeric" placeholder="1">
                        <input type="hidden" name="from" value="${from}">
                        <button type="submit" class="btn btn-outline-light ms-3">Add to cart</button>
                    </div>
                </form>
                <c:if test="${not empty sessionScope.user && sessionScope.user.admin}">
                    <div class="d-flex admin mt-5" id="removable">
                        <a href="/admin/book/update/${book.maSach}" class="btn btn-primary me-3">Update</a>
                        <button id="brbtn-${book.maSach}" type="button" data-bs-toggle="modal" data-bs-target="#confirmModal" class="btn btn-danger">Remove</button>
                        <jsp:include page="../modals/confirm-modal.jsp">
                            <jsp:param name="modalTitle" value="Deleting book..."/>
                        </jsp:include>
                    </div>
                </c:if>
            </main>
        </article>
        <h3 class="fw-semibold text-info mt-4 text-center ps-5 text-lg-start mx-lg-auto" style="width: 90%">Related books:</h3>
        <div id="carouselExampleFade" class="carousel slide ">
            <div class="carousel-inner px-5">
                <c:forEach begin="0" end="${books.size()-1}" var="item" items="${books}" varStatus="loop" step="3">
                    <div class="carousel-item ${loop.count == 1 ? "active" : ''} p-4">
                        <div class="d-flex justify-content-center">
                            <jsp:include page="../common/book.jsp" >
                                <jsp:param name="maSach" value="${item.maSach}"/>
                                <jsp:param name="img" value="${item.img}"/>
                                <jsp:param name="tenSach" value="${item.tenSach}"/>
                                <jsp:param name="nxb" value="${item.nxb}"/>
                                <jsp:param name="soLuong" value="${item.soLuong}"/>
                                <jsp:param name="gia" value="${item.gia}"/>
                            </jsp:include>
                            <c:if test="${loop.index <= books.size()-2}">
                                <jsp:include page="../common/book.jsp" >
                                    <jsp:param name="maSach" value="${books[loop.index+1].maSach}"/>
                                    <jsp:param name="img" value="${books[loop.index+1].img}"/>
                                    <jsp:param name="tenSach" value="${books[loop.index+1].tenSach}"/>
                                    <jsp:param name="nxb" value="${books[loop.index+1].nxb}"/>
                                    <jsp:param name="soLuong" value="${books[loop.index+1].soLuong}"/>
                                    <jsp:param name="gia" value="${books[loop.index+1].gia}"/>
                                </jsp:include>
                            </c:if>
                            <c:if test="${loop.index <= books.size()-3}">
                                <jsp:include page="../common/book.jsp" >
                                    <jsp:param name="maSach" value="${books[loop.index+2].maSach}"/>
                                    <jsp:param name="img" value="${books[loop.index+2].img}"/>
                                    <jsp:param name="tenSach" value="${books[loop.index+2].tenSach}"/>
                                    <jsp:param name="nxb" value="${books[loop.index+2].nxb}"/>
                                    <jsp:param name="soLuong" value="${books[loop.index+2].soLuong}"/>
                                    <jsp:param name="gia" value="${books[loop.index+2].gia}"/>
                                </jsp:include>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </c:when>
    <c:otherwise>
        <div class="w-100 text-center pt-3">
            <div>
                <img src="${pageContext.request.contextPath}/images/notfound.jpg" alt="Book not found image." style="width: 300px;"/>
            </div>
            <h3 class="fw-bold fs-2 mx-auto rounded-pill w-50" style="color: #73BBC9">No book found</h3>
            <a href="${pageContext.servletContext.contextPath}/books" class="btn btn-outline-secondary mt-3">Search the books you want!</a>
        </div>
    </c:otherwise>
</c:choose>

