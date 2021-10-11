<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flash_info_title" type="String"--%>
<%--@elvariable id="flash_info_messages" type="String[]"--%>
<%--@elvariable id="flash_error_title" type="String"--%>
<%--@elvariable id="flash_error_messages" type="String[]"--%>
<%--@elvariable id="item" type="me.megmilk.myecsite.models.Item"--%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta content="text/html" charset="UTF-8">
    <title>
        <c:out value="${item.name}"/> | くじらカフェ Online Store
    </title>
    <%@ include file="partial/html_head.jsp" %>
</head>
<body>
<header>
    <%@ include file="partial/body_header_nav.jsp" %>
</header>
<main>
    <div class="py-5 bg-light mt-3">
        <div class="container">
            <h2><c:out value="${item.name}"/></h2>

            <i class="bi bi-tag"></i>
            カテゴリ
            <c:url value="/category/${item.category.id}" var="url"/>
            <a href="${url}">
                <c:out value="${item.category.name}"/>
            </a>

            <dl class="mt-3">
                <c:if test="${not empty item.maker}">
                    <dt>メーカー</dt>
                    <dd class="border-1 border-bottom">
                        <c:out value="${item.maker}"/>
                    </dd>
                </c:if>

                <c:if test="${not empty item.color}">
                    <dt>色</dt>
                    <dd class="border-1 border-bottom">
                        <c:out value="${item.color}"/>
                    </dd>
                </c:if>

                <dt>単価</dt>
                <dd class="border-1 border-bottom">
                    ¥<f:formatNumber value="${item.price}" pattern="###,###"/>
                </dd>

                <dt>在庫数</dt>
                <dd class="border-1 border-bottom">
                    残り<c:out value="${item.stock}"/>
                </dd>
            </dl>

            <div class="text-center mt-3">
                <%--
                TODO フォームの送信先のコントローラーや、カートページの実装
                --%>
                <form method="post" action="#">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-cart-plus"></i>
                        カートに追加
                    </button>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>
