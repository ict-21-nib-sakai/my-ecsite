<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="item" type="me.megmilk.myecsite.models.Item"--%>
<c:import url="layout/app.jsp">
    <c:param name="title" value="${item.name} | "/>
    <c:param name="content">
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
            <c:url value="/cart/add/${item.id}" var="cart_add_url"/>
            <form method="post" action="<c:out value="${cart_add_url}"/>">
                    <%-- TODO CSRF トークン --%>
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-cart-plus"></i>
                    カートに追加
                </button>
            </form>
        </div>
    </c:param>
</c:import>
