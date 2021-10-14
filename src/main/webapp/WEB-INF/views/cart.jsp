<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flash_info_title" type="String"--%>
<%--@elvariable id="flash_info_messages" type="String[]"--%>
<%--@elvariable id="flash_error_title" type="String"--%>
<%--@elvariable id="flash_error_messages" type="String[]"--%>
<%--@elvariable id="carts" type="java.util.List<me.megmilk.myecsite.models.Cart>"--%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta content="text/html" charset="UTF-8">
    <title>
        カート | くじらカフェ Online Store
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
            <table class="table table-hover">
                <thead class="table-light">
                <tr>
                    <th scope="col">商品名</th>
                    <th scope="col">色</th>
                    <th scope="col">メーカー</th>
                    <th scope="col" class="text-end">単価</th>
                    <th scope="col" class="text-end">数量</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cart" items="${carts}">
                    <tr>
                        <td>
                            <c:url value="/item/${cart.item_id}" var="item_detail_url"/>
                            <a href="${item_detail_url}">
                                <c:out value="${cart.item.name}"/>
                            </a>
                        </td>
                        <td><c:out value="${cart.item.color}"/></td>
                        <td><c:out value="${cart.item.maker}"/></td>
                        <td class="text-end">
                            ¥<f:formatNumber value="${cart.item.price}" pattern="###,###"/></td>
                        <td class="text-end">
                            <f:formatNumber value="${cart.quantity}" pattern="###,###"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>
