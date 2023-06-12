<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
  <c:when test="${not empty sessionScope.get(cartId).orders}">
    <h3 class="text-danger text-center mb-3">${error}</h3>
    <table class="table table-bordered table-dark table-striped table-responsive-lg container-lg">
      <thead>
      <tr class="text-center">
        <th scope="col">#</th>
        <th scope="col">Book's name</th>
        <th scope="col">Quantity</th>
        <th scope="col">Price</th>
        <th scope="col">Total</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody id="removable">
        <c:forEach var="book" items="${sessionScope.get(cartId).orders}" varStatus="status">
          <tr>
              <td class="text-center">${status.count}</td>
              <td class="w-50">
                  <span class="d-block w-100" style="word-break: break-all" id="book-${book.value.maSach}">${book.value.tenSach}</span>
              </td>
              <td class="text-center w-10">${book.value.soLuongMua}</td>
              <td class="text-center w-10">${book.value.gia}</td>
              <td class="text-center">
                <fmt:setLocale value="en_US"/>
                <fmt:formatNumber type="currency" value="${book.value.soLuongMua*book.value.gia}" pattern="$#.##" />
              </td>
              <td class="w-10 text-center">
                  <button id="brbtn-${book.value.maSach}" type="button" data-bs-toggle="modal" data-bs-target="#confirmModal" class="btn btn-danger">Remove</button>
              </td>
          </tr>
        </c:forEach>
        <jsp:include page="../modals/confirm-modal.jsp">
            <jsp:param name="modalTitle" value="Remove book from cart"/>
        </jsp:include>
      <tr>
        <td colspan="4"></td>
        <td class="fs-5 text-warning text-center fw-bold">
            <fmt:formatNumber type="currency" value="${sessionScope.totalAmount}" pattern="$#.##" />
        </td>
      </tr>
      </tbody>
    </table>
    <div class="container-lg d-flex justify-content-end">
        <a href="/cart/clear" class="btn btn-danger">Remove all</a>
        <a href="/cart/checkout" class="btn btn-primary ms-5">Check out</a>
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
<%--    <div class="container p-4">--%>
<%--      <p class="fs-3 mx-auto text-warning">Nothing is here, even dust...</p>--%>
<%--    </div>--%>
  </c:otherwise>
</c:choose>

