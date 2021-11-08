<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="paginator" type="me.megmilk.myecsite.http.PaginationManager"--%>
<nav aria-label="Page navigation example" class="mb-5">
    <ul class="pagination justify-content-center">
        <c:if test="${paginator.currentPage == 1}">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">前へ</a>
            </li>
        </c:if>

        <c:if test="${paginator.currentPage >= 2}">
            <li class="page-item">
                <a class="page-link"
                   href="<c:out value="${paginator.buildUrl(paginator.currentPage - 1)}"/>"
                   tabindex="-1"
                   aria-disabled="false">
                    前へ
                </a>
            </li>
        </c:if>

        <c:forEach begin="${paginator.calcStartPageNumber()}"
                   end="${paginator.calcLastPageNumber()}"
                   var="pageNumber">
            <c:if test="${paginator.currentPage == pageNumber}">
                <li class="page-item active">
                    <span class="page-link" href="#">
                        <c:out value="${pageNumber}"/>
                    </span>
                </li>
            </c:if>

            <c:if test="${paginator.currentPage != pageNumber}">
                <li class="page-item">
                    <a class="page-link"
                       href="<c:out value="${paginator.buildUrl(pageNumber)}"/>">
                        <c:out value="${pageNumber}"/>
                    </a>
                </li>
            </c:if>
        </c:forEach>

        <c:if test="${paginator.currentPage == paginator.calcLastPageNumber()}">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">次へ</a>
            </li>
        </c:if>

        <c:if test="${paginator.currentPage != paginator.calcLastPageNumber()}">
            <li class="page-item">
                <a class="page-link"
                   href="<c:out value="${paginator.buildUrl(paginator.currentPage + 1)}"/>">
                    次へ
                </a>
            </li>
        </c:if>
    </ul>
</nav>
