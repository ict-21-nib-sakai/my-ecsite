<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="carts" type="java.util.List<me.megmilk.myecsite.models.Cart>"--%>
<%--@elvariable id="totalQuantity" type="int"--%>
<%--@elvariable id="sum" type="int"--%>
<%--@elvariable id="DELIVERY_OPTIONS" type="String[][]"--%>
<%--@elvariable id="DELIVERY_OPTIONAL" type="String"--%>
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
<main id="app">
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
                    <h3>配達先</h3>
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

                    <input type="text"
                           name="optional_address"
                           class="form-control"
                           placeholder="ご自宅以外の配達先"
                           v-model="optionalAddress"
                           :disabled="enabledOptionalAddress">
                </div>

                <div class="text-center">
                    <c:url value="/cart" var="cart_url"/>
                    <a href="<c:out value="${cart_url}"/>"
                       class="btn btn-secondary me-3">
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
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14"></script>

<script>
    const app = new Vue({
        el: '#app',
        data: {
            selectedRadio: '',
            optionalAddress: '',
            radioOptions: [
                <c:forEach var="radioOption" items="${DELIVERY_OPTIONS}">
                {
                    value: '<c:out value="${radioOption[0]}"/>',
                    text: '<c:out value="${radioOption[1]}"/>',
                },
                </c:forEach>
            ],
        },
        computed: {
            enabledOptionalAddress: function () {
                return this.selectedRadio !== '<c:out value="${DELIVERY_OPTIONAL}"/>'
            },
        },
    })
</script>
</body>
</html>
