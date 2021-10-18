<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="carts" type="java.util.List<me.megmilk.myecsite.models.Cart>"--%>
<%--@elvariable id="sum" type="int"--%>
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
<main id="app">
    <div class="py-5 bg-light mt-3">
        <div class="container">
            <table class="table table-hover">
                <thead class="table-light">
                <tr>
                    <th scope="col">商品名</th>
                    <th scope="col">色</th>
                    <th scope="col">メーカー</th>
                    <th scope="col" class="text-end">数量</th>
                    <th scope="col" class="text-end">単価</th>
                    <th scope="col" class="text-center">削除</th>
                </tr>
                </thead>
                <tbody>
                <c:url value="/item/" var="item_parent_path"/>
                <c:url value="/cart/del/" var="cart_parent_path"/>
                <tr v-for="cart in carts" v-bind:key="cart.id">
                    <td>
                        <a :href="'<c:out value="${item_parent_path}"/>' + cart.id">
                            {{ cart.name }}
                        </a>
                    </td>
                    <td>{{ cart.color }}</td>
                    <td>{{ cart.maker }}</td>
                    <td class="text-end">{{ cart.quantity }}</td>
                    <td class="text-end">¥{{ cart.price | numberFormat }}</td>
                    <td class="text-center">
                        <form :action="'${cart_parent_path}' + cart.id"
                              method="post">
                            <%-- TODO CSRF トークン --%>
                        </form>
                        <button type="button"
                                class="btn btn-danger"
                                @click="openModal(cart)">
                            <i class="bi bi-cart-x"></i>
                            削除
                        </button>
                    </td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" class="text-end">
                        合計数量 {{ totalQuantity }}
                    </td>
                    <td colspan="2" class="text-end">
                        合計金額 ¥{{ totalPrice | numberFormat }}
                    </td>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>

    <div class="modal fade"
         id="confirmationModal"
         tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">
                        削除の確認
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>以下の商品をカートから削除してよろしいですか？</p>
                    <dl>
                        <dt>商品名</dt>
                        <dd>{{ cartOnModal.name }}</dd>
                        <dt>メーカー</dt>
                        <dd>{{ cartOnModal.maker }}</dd>
                        <dt>単価</dt>
                        <dd>¥{{ cartOnModal.price | numberFormat }}</dd>
                        <dt>数量</dt>
                        <dd>{{ cartOnModal.quantity }}個</dd>
                    </dl>
                </div>
                <div class="modal-footer">
                    <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">
                        <i class="bi bi-caret-left"></i>
                        やめる
                    </button>

                    <button
                        type="button"
                        class="btn btn-danger">
                        <i class="bi bi-cart-x"></i>
                        削除する
                    </button>
                </div>
            </div>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14"></script>

<script>
    const app = new Vue({
        el: '#app',
        data: {
            cartOnModal: {},
            carts: [
                <c:forEach var="cart" items="${carts}" varStatus="cart_loop">
                {
                    id: <c:out value="${cart.id}"/>,
                    name: "<c:out value="${cart.item.name}"/>",
                    color: "<c:out value="${cart.item.color}"/>",
                    maker: "<c:out value="${cart.item.maker}"/>",
                    quantity: <c:out value="${cart.quantity}"/>,
                    price: <c:out value="${cart.item.price}"/>,
                },
                </c:forEach>
            ]
        },
        methods: {
            openModal: function (cart) {
                this.cartOnModal = cart

                // モーダルウィンドウを表示する
                const confirmationModal = new bootstrap.Modal(
                    document.getElementById('confirmationModal'),
                    {backdrop: 'static', keyboard: true, focus: true}
                )

                confirmationModal.show()
            },
        },
        filters: {
            numberFormat: function (value) {
                if (value === void 0) {
                    return ''
                }

                if (0 === value.toString().length) {
                    return ''
                }

                return new Intl
                    .NumberFormat()
                    .format(value)
            }
        },
        computed: {
            /**
             * @return {Number} 数量の合計
             */
            totalQuantity: function () {
                let quantities = 0
                for (const cart of this.carts) {
                    quantities += cart.quantity
                }

                return quantities
            },

            /**
             * @return {Number} 合計金額
             */
            totalPrice: function () {
                let total = 0
                for (const cart of this.carts) {
                    total += cart.price * cart.quantity
                }

                return total
            }
        },
    })
</script>
</body>
</html>
