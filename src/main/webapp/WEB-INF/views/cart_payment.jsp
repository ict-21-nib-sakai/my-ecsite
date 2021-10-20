<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="carts" type="java.util.List<me.megmilk.myecsite.models.Cart>"--%>
<%--@elvariable id="totalQuantity" type="int"--%>
<%--@elvariable id="sum" type="int"--%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta content="text/html" charset="UTF-8">
    <title>
        配達先・お支払い方法 | くじらカフェ Online Store
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

            <table class="table table-hover responsive">
                <thead class="table-light">
                <tr>
                    <th scope="col">商品名</th>
                    <th scope="col" class="col-2">色</th>
                    <th scope="col" class="col-2">メーカー</th>
                    <th scope="col" class="col-2 text-end">数量</th>
                    <th scope="col" class="text-end">単価</th>
                </tr>
                </thead>
                <tbody>
                <c:url value="/item/" var="item_parent_path"/>
                <c:url value="/cart/delete/" var="cart_parent_path"/>

                <c:forEach var="cart" items="${carts}">
                    <tr>
                        <td><c:out value="${cart.item.name}"/></td>
                        <td data-title="色">
                            <span class="resp-flex-9">
                                <c:out value="${cart.item.color}"/>
                            </span>
                        </td>
                        <td data-title="メーカー">
                            <span class="resp-flex-9">
                                <c:out value="${cart.item.maker}"/>
                            </span>
                        </td>
                        <td data-title="数量" class="text-end resp-text-start">
                            <span class="resp-flex-9">
                                <c:out value="${cart.quantity}"/>
                            </span>
                        </td>
                        <td data-title="単価" class="text-end resp-text-start">
                            <span class="resp-flex-9">
                                ¥<f:formatNumber value="${cart.item.price}" pattern="###,###"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" class="text-end">
                        合計数量 <c:out value="${totalQuantity}"/>
                    </td>
                    <td class="text-end">
                        合計金額 ¥<f:formatNumber value="${sum}" pattern="###,###"/>
                    </td>
                </tr>
                </tfoot>
            </table>

            <%--
            TODO この位置に配達先・お支払い方法の入力フォームを配置する
            --%>

            <div class="text-center">
                <c:url value="/cart" var="cart_url"/>
                <a href="<c:out value="${cart_url}"/>"
                   class="btn btn-secondary me-3">
                    <i class="bi bi-caret-left"></i>
                    数量を変更する
                </a>

                <c:url value="/cart/confirmation" var="cart_confirmation_url"/>
                <a href="<c:out value="${cart_confirmation_url}"/>"
                   class="btn btn-primary">
                    <i class="bi bi-arrow-right-circle"></i>
                    確認へ進む
                </a>
            </div>
        </div>
    </div>
</main>
</body>
</html>
