<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="order" type="me.megmilk.myecsite.models.Order"--%>
<%--@elvariable id="CartService$DELIVERY_HOME" type="String"--%>
<%--@elvariable id="CartService$DELIVERY_OPTIONAL" type="String"--%>
<%--@elvariable id="CartService$PAYMENT_METHOD_CARD" type="String"--%>
<%--@elvariable id="CartService$PAYMENT_METHOD_CASH_ON_DELIVERY" type="String"--%>
<%--@elvariable id="CartService$PAYMENT_METHOD_BANK" type="String"--%>
<c:import url="layout/app.jsp">
    <c:param name="title" value="ご注文ありがとうございます | "/>
    <c:param name="content">
        <h2>ご注文ありがとうございます</h2>
        <p>下記の注文を賜りました。発送については追ってお知らせいたします。</p>

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
            <c:forEach var="orderDetail" items="${order.orderDetails}">
                <tr>
                    <td><c:out value="${orderDetail.item.name}"/></td>
                    <td data-title="色">
                        <span class="resp-flex-8">
                            <c:out value="${orderDetail.item.color}"/>
                        </span>
                    </td>
                    <td data-title="メーカー">
                        <span class="resp-flex-8">
                            <c:out value="${orderDetail.item.maker}"/>
                        </span>
                    </td>
                    <td data-title="数量" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            <c:out value="${orderDetail.quantity}"/>
                        </span>
                    </td>
                    <td data-title="単価" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            ¥<f:formatNumber value="${orderDetail.item_price}" pattern="###,###"/>
                        </span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="4" class="text-end">
                    <%--
                    TODO 注文の合計数量は正式なものを表示する
                    --%>
                    合計数量 10
                </td>
                <td class="text-end">
                    <%--
                    TODO 注文の合計金額は、正式なものを表示する
                    --%>
                    合計金額 ¥1,234,567
                </td>
            </tr>
            </tfoot>
        </table>

        <div class="mb-3">
            <h3>
                <i class="bi bi-box-seam"></i>
                配達先
            </h3>
            <c:if test="${CartService$DELIVERY_HOME == order.shipping_address_type}">
                ご自宅
            </c:if>

            <c:if test="${CartService$DELIVERY_OPTIONAL == order.shipping_address_type}">
                配達先を指定
            </c:if>

            <address>
                <c:out value="${order.shipping_address}"/>
            </address>
        </div>

        <%--
        <div class="mb-3">
            TODO お支払い方法を表示する
        </div>
        --%>
    </c:param>
</c:import>
