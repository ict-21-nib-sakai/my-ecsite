<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="carts" type="java.util.List<me.megmilk.myecsite.models.Cart>"--%>
<%--@elvariable id="totalQuantity" type="int"--%>
<%--@elvariable id="sum" type="int"--%>
<%--@elvariable id="CartService$DELIVERY_OPTIONS" type="String[][]"--%>
<%--@elvariable id="CartService$DELIVERY_HOME" type="String"--%>
<%--@elvariable id="CartService$DELIVERY_OPTIONAL" type="String"--%>
<%--@elvariable id="mySession" type="me.megmilk.myecsite.http.MySession"--%>
<c:import url="layout/app.jsp">
    <c:param name="title" value="お買い物前の確認 | "/>
    <c:param name="content">
        <h3>
            <i class="bi bi-cart4"></i>
            ご注文の商品
        </h3>
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

            <c:forEach var="cart" items="${carts}">
                <tr>
                    <td><c:out value="${cart.item.name}"/></td>
                    <td data-title="色">
                        <span class="resp-flex-8">
                            <c:out value="${cart.item.color}"/>
                        </span>
                    </td>
                    <td data-title="メーカー">
                        <span class="resp-flex-8">
                            <c:out value="${cart.item.maker}"/>
                        </span>
                    </td>
                    <td data-title="数量" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            <c:out value="${cart.quantity}"/>
                        </span>
                    </td>
                    <td data-title="単価" class="text-end resp-text-start">
                        <span class="resp-flex-8">
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

        <div class="mb-3">
            <h3>
                <i class="bi bi-box-seam"></i>
                配達先
            </h3>
            <c:choose>
                <c:when test="${mySession.getFormValue('delivery_option') == CartService$DELIVERY_HOME}">
                    ご自宅
                    <address>
                        <c:out value="${flashBag.user.home_address}"/>
                    </address>
                </c:when>
                <c:when test="${mySession.getFormValue('delivery_option') == CartService$DELIVERY_OPTIONAL}">
                    ご指定の配達先
                    <address>
                        <c:out value="${mySession.getFormValue('optional_address')}"/>
                    </address>
                </c:when>
            </c:choose>
        </div>

        <c:url value="/cart/thanks" var="cart_thanks_url"/>
        <form action="${cart_thanks_url}" method="post">
                <%-- TODO CSRF トークン --%>
            <div class="d-flex justify-content-center gap-3">
                <c:url value="/cart/payment" var="cart_payment_url"/>
                <a href="${cart_payment_url}" class="btn btn-secondary">
                    <i class="bi bi-caret-left"></i>
                    配達先を変更する
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-cart-check"></i>
                    注文する
                </button>
            </div>
        </form>
    </c:param>
</c:import>
