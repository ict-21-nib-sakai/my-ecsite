<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flash_info_title" type="String"--%>
<%--@elvariable id="flash_info_messages" type="String[]"--%>
<%--@elvariable id="flash_error_title" type="String"--%>
<%--@elvariable id="flash_error_messages" type="String[]"--%>
<%--@elvariable id="items" type="java.util.List<me.megmilk.myecsite.models.Item>"--%>
<%--@elvariable id="category" type="me.megmilk.myecsite.models.Category"--%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta content="text/html" charset="UTF-8">
    <title>
        <c:out value="${category.name}"/> | くじらカフェ Online Store
    </title>
    <%@ include file="partial/html_head.jsp" %>
</head>
<body>
<header>
    <%@ include file="partial/body_header_nav.jsp" %>
</header>
<main>
    <div class="album py-5 bg-light mt-3">
        <div class="container">
            <h2><c:out value="${category.name}"/></h2>
            <c:if test="${not empty flash_error_title}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-circle"></i>
                    <c:out value="${flash_error_title}"/>
                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="alert"
                            aria-label="Close">
                    </button>
                </div>
            </c:if>

            <c:if test="${not empty flash_info_title}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-info-circle"></i>
                    <c:out value="${flash_info_title}"/>
                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="alert"
                            aria-label="Close">
                    </button>
                </div>
            </c:if>

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
        </div>
    </div>
</main>
</body>
</html>
