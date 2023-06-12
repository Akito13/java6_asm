<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 6/11/2023
  Time: 1:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="py-4 my-4 row">
    <aside class="col-12 col-lg-4">
        <div class="w-75 float-lg-end mx-auto text-center text-white" style="height: 420px;">
            <div id="pickImg" class="w-100 border border-3 border-primary h-100
                 d-flex justify-content-center align-items-center flex-column ${not empty book.img ? "d-none":""}"
                 style="cursor: pointer; color: #73BBC9; font-size: 3rem">
                <i class="bi bi-plus-circle-dotted"></i>
                <p class="fs-3 text-white">Add an image</p>
            </div>
            <img id="bookImg" src="${pageContext.servletContext.contextPath}/images/${book.img}"
                 alt="Book thumbnail" class="h-100 img-fluid pointer-event ${not empty book.img ? "":"d-none"}" style="cursor: pointer"/>
<%--            <img id="bookImg1" src="${pageContext.servletContext.contextPath}/images/${book.img}" alt="Book thumbnail" class="h-100 img-fluid pointer-event" style="cursor: pointer"/>--%>
        </div>
    </aside>
    <%--@elvariable id="book" type="sof3021.ca4.nhom1.asm.qls.model.Book"--%>
    <div class="col-12 col-lg-8">
        <div class="mx-auto rounded-3 border-1 border-light border w-75 py-4" style="padding-left: 3rem; padding-right:  3rem;">
            <form:form action="/admin/book/edit?action=${action}" modelAttribute="book" cssClass="position-relative" enctype="multipart/form-data" method="post">
                <c:choose>
                    <c:when test="${not empty message}">
                        <h3 class="text-success mb-3">${message}</h3>
                    </c:when>
                    <c:when test="${not empty error}">
                        <h3 class="text-danger mb-3">${error}</h3>
                    </c:when>
                </c:choose>
                <c:if test="${action.equals('edit')}">
                    <div class="mb-3">
                        <form:label path="maSach" for="maSach" cssClass="form-label text-white">Book ID</form:label>
                        <form:input path="maSach" type="text" cssClass="form-control" id="maSach" readonly="true"/>
                    </div>
                </c:if>
                <div class="mb-3">
                    <form:label path="tenSach" for="tenSach" cssClass="form-label text-white">Book name</form:label>
                    <form:input path="tenSach" type="text" cssClass="form-control" id="tenSach" />
                    <form:errors path="tenSach" cssClass="text-danger"/>
                </div>
                <div class="mb-3">
                    <form:label path="tacGia" for="tacGia" cssClass="form-label text-white">Author</form:label>
                    <form:input path="tacGia" type="text" cssClass="form-control" id="tacGia" />
                    <form:errors path="tacGia" cssClass="text-danger"/>
                </div>
                <div class="mb-3">
                    <form:label path="tacGia" for="tacGia" cssClass="form-label text-white">Category</form:label>
                    <form:select path="loai.maLoai" cssClass="form-select" aria-label="Default select example">
                        <form:options items="${categories}" itemValue="maLoai" itemLabel="tenLoai"/>
                    </form:select>
                </div>
                <div class="mb-3">
                    <form:label path="gia" for="gia" cssClass="form-label text-white">Price</form:label>
                    <div class="input-group">
                        <span class="input-group-text">$</span>
                        <form:input path="gia" type="text" cssClass="form-control" id="gia" />
                    </div>
                    <form:errors path="gia" cssClass="text-danger"/>
                </div>
                <div class="mb-3">
                    <form:label path="nxb" for="nxb" cssClass="form-label text-white">Publish Date</form:label>
                    <form:input path="nxb" type="text" cssClass="form-control" id="nxb" />
                    <form:errors path="nxb" cssClass="text-danger"/>
                </div>
                <div class="mb-3">
                    <form:label path="soLuong" for="soLuong" cssClass="form-label text-white">Quantity</form:label>
                    <form:input path="soLuong" type="text" cssClass="form-control" id="soLuong" />
                    <form:errors path="soLuong" cssClass="text-danger"/>
                </div>
                <div class="mb-3">
                    <form:label path="img" for="springImg" cssClass="form-label text-white">Image</form:label>
                    <form:input path="img" id="springImg" cssClass="form-control" readonly="true"/>
                    <form:errors path="img" cssClass="text-danger"/>
                    <input type="file" class="d-none" id="img" name="hiddenImgInput" accept="image/png,image/jpeg,image/jpg,image/webp"/>
                </div>
                <div class="mb-2">
                    <button class="btn btn-outline-primary">${fn:toUpperCase(action)}</button>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script src="${pageContext.servletContext.contextPath}/bootstrap/custom/upload.js"></script>