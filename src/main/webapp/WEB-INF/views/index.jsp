<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="items" type="java.util.List<me.megmilk.myecsite.models.Item>"--%>
<c:import url="layout/app.jsp">
    <c:param name="content">
        <div class="container">
            <c:if test="${not empty flashBag.flashErrorTitle}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-circle"></i>
                    <c:out value="${flashBag.flashErrorTitle}"/>
                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="alert"
                            aria-label="Close">
                    </button>
                </div>
            </c:if>

            <c:if test="${not empty flashBag.flashInfoTitle}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-info-circle"></i>
                    <c:out value="${flashBag.flashInfoTitle}"/>
                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="alert"
                            aria-label="Close">
                    </button>
                </div>
            </c:if>

            <c:if test="${items.size() == 0}">
                <div class="card">
                    <div class="card-body">
                        <p class="card-text">該当する商品が見つかりませんでした。</p>
                        <p class="card-text">キーワードを変えて検索してみてください。</p>
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
        </div>
    </c:param>
</c:import>
