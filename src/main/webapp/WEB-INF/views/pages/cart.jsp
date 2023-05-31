<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 5/25/2023
  Time: 12:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
  <c:when test="${not empty sessionScope.cart.orders}">
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
      <tbody id="cartTableBody">
        <c:forEach var="book" items="${sessionScope.cart.orders}" varStatus="status">
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
  </c:when>
  <c:otherwise>
    <div class="container p-4">
      <p class="fs-3 mx-auto text-warning">Nothing is here, even dust...</p>
    </div>
  </c:otherwise>
</c:choose>

