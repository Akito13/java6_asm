<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${not empty orders}">
        <h3 class="text-danger text-center mb-3">${error}</h3>
        <table class="table table-bordered table-dark table-striped table-responsive-lg container-lg">
            <thead>
            <tr class="text-center">
                <th scope="col">#</th>
                <th scope="col">Book's name</th>
                <th scope="col">Quantity</th>
                <th scope="col">Total Amount</th>
                <th scope="col">Order No</th>
            </tr>
            </thead>
            <tbody id="removable">
            <c:forEach var="order" items="${orders}" varStatus="status">
                <tr>
                    <td class="text-center">${order.maDhct}</td>
                    <td class="w-50">
                        <span class="d-block w-100" style="word-break: break-all" id="book-${order.book.maSach}">${order.book.tenSach}</span>
                    </td>
                    <td class="text-center w-10">${order.soLuong}</td>
                    <td class="text-center w-10">
                            <fmt:setLocale value="en_US"/>
                            <fmt:formatNumber type="currency" value="${order.tongTien}" pattern="$#.##" />
                    </td>
                    <td class="text-center">
                        ${order.order.maDH}
                    </td>
                </tr>
            </c:forEach>
<%--            <tr>--%>
<%--                <td colspan="4"></td>--%>
<%--                <td class="fs-5 text-warning text-center fw-bold">--%>
<%--                    <fmt:formatNumber type="currency" value="${sessionScope.totalAmount}" pattern="$#.##" />--%>
<%--                </td>--%>
<%--            </tr>--%>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="w-100 text-center pt-3">
            <div>
                <img src="${pageContext.request.contextPath}/images/notfound.jpg" alt="Book not found image." style="width: 300px;"/>
            </div>
            <h3 class="fw-bold fs-2 mx-auto rounded-pill w-50" style="color: #73BBC9">You have no orders yet</h3>
            <a href="${pageContext.servletContext.contextPath}/books" class="btn btn-outline-secondary mt-3">Search the books you want!</a>
        </div>
        <%--    <div class="container p-4">--%>
        <%--      <p class="fs-3 mx-auto text-warning">Nothing is here, even dust...</p>--%>
        <%--    </div>--%>
    </c:otherwise>
</c:choose>

