<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="prevRequest" type="javax.servlet.http.HttpServletRequest"--%>
<%--@elvariable id="items" type="java.util.List<me.megmilk.myecsite.models.Item>"--%>
<%--@elvariable id="category" type="me.megmilk.myecsite.models.Category"--%>
<c:import url="layout/app.jsp">
    <c:param name="title" value="${category.name} | "/>
    <c:param name="content">
        <h2><c:out value="${category.name}"/></h2>

        <c:if test="${items.size() == 0}">
            <div class="card">
                <div class="card-body">
                    <p class="card-text">該当する商品が見つかりませんでした。</p>
                    <p class="card-text">キーワードを変えて検索してみてください。</p>
                    <p class="card-text">
                        <c:url value="/" var="url">
                            <c:param name="keyword" value="${prevRequest.getParameter('keyword')}"/>
                        </c:url>
                        または、すべてのカテゴリから
                        <a href="<c:out value="${url}"/>">
                            &quot;<c:out value="${prevRequest.getParameter('keyword')}"/>&quot;
                            を検索します。
                        </a>
                    </p>
                </div>
            </div>
        </c:if>

        <c:if test="${items.size() >= 1}">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                <c:forEach var="item" items="${items}">
                    <div class="col">
                        <div class="card shadow-sm">
                            <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
                                <title>Placeholder</title>
                                <rect width="100%" height="100%" fill="#55595c"></rect>
                                <text x="50%" y="50%" fill="#eceeef" dy=".3em">
                                    Thumbnail
                                </text>
                            </svg>

                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${item.name}"/>
                                </p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <a href="<c:out value="${pageContext.request.contextPath}/item/${item.id}"/>"
                                           class="btn btn-sm btn-outline-secondary">
                                            詳しく見る
                                        </a>
                                    </div>
                                    <small class="text-muted">
                                        <c:out value="${item.maker}"/>
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </c:param>
</c:import>
