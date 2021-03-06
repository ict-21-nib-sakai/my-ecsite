<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="carts" type="java.util.List<me.megmilk.myecsite.models.Cart>"--%>
<%--@elvariable id="totalQuantity" type="int"--%>
<%--@elvariable id="sum" type="int"--%>
<%--@elvariable id="CartService$DELIVERY_OPTIONS" type="String[][]"--%>
<%--@elvariable id="CartService$DELIVERY_OPTIONAL" type="String"--%>
<%--@elvariable id="CartService$PAYMENT_METHODS" type="String[][]"--%>
<%--@elvariable id="CartService$PAYMENT_METHOD_CARD" type="String"--%>
<%--@elvariable id="CartService$PAYMENT_METHOD_CASH_ON_DELIVERY" type="String"--%>
<%--@elvariable id="CartService$PAYMENT_METHOD_BANK" type="String"--%>
<%--@elvariable id="mySession" type="me.megmilk.myecsite.http.MySession"--%>
<%--@elvariable id="optionalAddress" type="String"--%>
<c:import url="layout/app.jsp">
    <c:param name="title" value="配達先・お支払い方法 | "/>
    <c:param name="content">
        <h2 class="border-bottom border-2 border-dark mb-4">
            配達先・お支払い方法
        </h2>

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
            <c:url value="/cart/delete/" var="cart_parent_path"/>

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

        <c:url value="/cart/confirmation" var="cart_confirmation_url"/>
        <form action="${cart_confirmation_url}" method="post">
                <%-- TODO CSRF トークン --%>
            <div class="mb-3">
                <h3>
                    <i class="bi bi-box-seam"></i>
                    配達先
                </h3>
                <div class="form-check mb-2"
                     v-for="radioOption in radioOptions"
                     v-bind:key="radioOption.value">

                    <input type="radio"
                           name="delivery_option"
                           class="form-check-input"
                           :id="'delivery_option_' + radioOption.value"
                           :value="radioOption.value"
                           v-model="selectedRadio">

                    <label class="form-check-label"
                           :for="'delivery_option_' + radioOption.value">
                        {{ radioOption.text }}
                    </label>
                </div>

                <c:if test="${flashBag.hasMessage('delivery_option')}">
                    <p class="text-danger font-weight-bold">
                        <i class="bi bi-exclamation-circle"></i>
                        <c:out value="${flashBag.getMessage('delivery_option')}"/>
                    </p>
                </c:if>

                <input type="text"
                       name="optional_address"
                       class="form-control mb-2"
                       placeholder="ご自宅以外の配達先"
                       v-model="optionalAddress"
                       :disabled="enabledOptionalAddress">

                <c:if test="${flashBag.hasMessage('optional_address')}">
                    <p class="text-danger font-weight-bold">
                        <i class="bi bi-exclamation-circle"></i>
                        <c:out value="${flashBag.getMessage('optional_address')}"/>
                    </p>
                </c:if>
            </div>

            <div class="mb-3">
                <h3>
                    <i class="bi bi-credit-card"></i>
                    お支払い方法
                </h3>
                <select name="payment_method"
                        class="form-select mb-2"
                        v-model="selectedPaymentMethod">
                    <option v-for="paymentMethod in paymentMethods"
                            v-bind:value="paymentMethod.value">
                        {{ paymentMethod.text }}
                    </option>
                </select>

                <c:if test="${flashBag.hasMessage('payment_method')}">
                    <p class="text-danger font-weight-bold">
                        <i class="bi bi-exclamation-circle"></i>
                        <c:out value="${flashBag.getMessage('payment_method')}"/>
                    </p>
                </c:if>
            </div>

            <div class="d-flex justify-content-center gap-3">
                <c:url value="/cart" var="cart_url"/>
                <a href="<c:out value="${cart_url}"/>"
                   class="btn btn-secondary">
                    <i class="bi bi-caret-left"></i>
                    数量を変更する
                </a>

                <button type="submit"
                        class="btn btn-primary">
                    <i class="bi bi-arrow-right-circle"></i>
                    確認へ進む
                </button>
            </div>
        </form>
    </c:param>
    <c:param name="script">
        <script>
            const app = new Vue({
                el: '#app',
                data: {
                    selectedRadio: '<c:out value="${mySession.getFormValue('delivery_option')}"/>',
                    optionalAddress: '<c:out value="${optionalAddress}" escapeXml="false"/>',
                    selectedPaymentMethod: '<c:out value="${mySession.getFormValue('payment_method')}"/>',
                    radioOptions: [
                        <c:forEach var="radioOption" items="${CartService$DELIVERY_OPTIONS}">
                        {
                            value: '<c:out value="${radioOption[0]}"/>',
                            text: '<c:out value="${radioOption[1]}"/>',
                        },
                        </c:forEach>
                    ],
                    paymentMethods: [
                        {
                            value: '',
                            text: '選択してください',
                        },
                        <c:forEach var="paymentMethod" items="${CartService$PAYMENT_METHODS}">
                        {
                            value: '<c:out value="${paymentMethod[0]}"/>',
                            text: '<c:out value="${paymentMethod[1]}"/>',
                        },
                        </c:forEach>
                    ]
                },
                computed: {
                    enabledOptionalAddress: function () {
                        return this.selectedRadio !== '<c:out value="${CartService$DELIVERY_OPTIONAL}"/>'
                    },
                },
            })
        </script>
    </c:param>
</c:import>
