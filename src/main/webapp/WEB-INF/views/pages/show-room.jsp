<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 6/9/2023
  Time: 11:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<article class="row row-cols-md-2 row-cols-1 px-lg-3 px-4">
  <aside class="col-md-3 text-center sort pt-4">
    <div class="list-group mt-5 pt-5 w-75 mx-auto">
      <c:if test="${not empty sessionScope.user && sessionScope.user.admin}">
        <a href="/admin/book/create" class="list-group-item list-group-item-action text-bg-dark">Add a new book</a>
      </c:if>
      <a href="/books?sort=n" class="list-group-item list-group-item-action text-bg-dark">Sort by name</a>
      <a href="/books?sort=p" class="list-group-item list-group-item-action text-bg-dark">Sort by price</a>
      <a href="/books?sort=d" class="list-group-item list-group-item-action text-bg-dark">Sort by date</a>
      <a href="/books?sort=a" class="list-group-item list-group-item-action text-bg-dark">Sort by amount</a>
    </div>
  </aside>
  <main class="col-md-9 col-12">
    <div class="container-fluid mx-auto d-flex justify-content-end px-3 px-lg-4 my-4">
      <form method="get" class="w-25 pe-lg-4">
        <input type="hidden" name="sort" value="${sort}">
        <select name="order" class="form-select bg-dark text-white" onchange="this.form.submit();">
          <option value="h" ${order.equals("h")?"selected":""}>From highest</option>
          <option value="l" ${order.equals("l")?"selected":""}>From lowest</option>
        </select>
      </form>
    </div>
    <div class="border-start border-1 border-secondary-subtle h-85">
      <c:choose>
        <c:when test="${not empty books}">
          <jsp:include page="main.jsp"/>
          <nav aria-label="Page navigation" class="mt-5">
            <c:if test="${not empty order}">
              <c:set var="o" value="&order=${order}"/>
            </c:if>
            <c:if test="${not empty sort}">
              <c:set var="s" value="&sort=${sort}"/>
            </c:if>
            <ul class="pagination justify-content-center">
              <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/books?page=${page.number-1 < 0 ? 0 : page.number-1}${o}${s}" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <c:forEach begin="0" end="${page.totalPages-1 < 0 ? 0:page.totalPages-1}" var="curPage">
                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/books?page=${curPage}${o}${s}">${curPage+1}</a></li>
              </c:forEach>
              <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/books?page=${page.number+1 >= page.totalPages ? page.totalPages-1 : page.number+1}${o}${s}" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
        </c:when>
        <c:otherwise>
          <div class="text-center pt-3">
            <h3 class="fw-bold fs-2 mx-auto rounded-pill w-50" style="color: #73BBC9">No books found</h3>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </main>
</article>
