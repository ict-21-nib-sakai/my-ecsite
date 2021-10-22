<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<%--@elvariable id="carts" type="java.util.List<me.megmilk.myecsite.models.Cart>"--%>
<%--@elvariable id="sum" type="int"--%>
<c:import url="layout/app.jsp">
    <c:param name="title" value="カート | "/>
    <c:param name="content">
        <div class="card text-dark bg-light my-5" v-show="0 === totalQuantity">
            <div class="card-body">
                <p class="card-text mt-3 mb-3">カートに商品は入っていません。</p>
            </div>
        </div>

        <table class="table table-hover responsive" v-show="totalQuantity">
            <thead class="table-light">
            <tr>
                <th scope="col">商品名</th>
                <th scope="col" class="col-2">色</th>
                <th scope="col" class="col-2">メーカー</th>
                <th scope="col" class="col-2 text-end">数量</th>
                <th scope="col" class="text-end">単価</th>
                <th scope="col" class="text-center">削除</th>
            </tr>
            </thead>
            <tbody>
            <c:url value="/item/" var="item_parent_path"/>
            <c:url value="/cart/delete/" var="cart_parent_path"/>
            <tr v-for="cart in carts" v-bind:key="cart.id">
                <td>
                    <a :href="'<c:out value="${item_parent_path}"/>' + cart.itemId">
                        {{ cart.name }}
                    </a>
                </td>
                <td data-title="色">
                    <span class="resp-flex-8">{{ cart.color }}</span>
                </td>
                <td data-title="メーカー">
                    <span class="resp-flex-8">{{ cart.maker }}</span>
                </td>
                <td data-title="数量" class="text-end resp-text-start">
                    <span class="resp-flex-8">
                        <input type="number"
                               min="1"
                               class="form-control text-end"
                               :name="'cart_quantity_' + cart.id"
                               :value="cart.quantity"
                               v-model="cart.quantity"
                               @change="quantityChanged(cart)">
                    </span>
                </td>
                <td data-title="単価" class="text-end resp-text-start">
                    <span class="resp-flex-8">¥{{ cart.price | numberFormat }}</span>
                </td>
                <td class="text-center resp-flex-end">
                    <form :action="'${cart_parent_path}' + cart.id"
                          :id="'cart_del_' + cart.id"
                          method="post">
                            <%-- TODO CSRF トークン --%>
                    </form>
                    <button type="button"
                            class="btn btn-danger text-nowrap"
                            @click="openModal(cart)"
                            :disabled="!trafficFree">
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

        <div class="d-flex justify-content-center gap-3">
            <a href="<c:out value="${pageContext.request.contextPath}"/>"
               class="btn btn-secondary">
                <i class="bi bi-caret-left"></i>
                商品検索に戻る
            </a>

            <c:url value="/cart/payment" var="cart_payment_url"/>
            <a href="${cart_payment_url}"
               :class="classAttrNextButton"
               v-show="totalQuantity">
                <i class="bi bi-arrow-right-circle"></i>
                配達先・お支払い方法
            </a>
        </div>
    </c:param>
    <c:param name="extended_content">
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
                            class="btn btn-danger"
                            @click="sendDeletionRequest(cartOnModal)"
                            :disabled="!buttonStatus">
                            <i class="bi bi-cart-x"></i>
                            削除する
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </c:param>
    <c:param name="script">
        <script>
            const app = new Vue({
                el: '#app',
                data: {
                    // 一覧表示や、ページ下部のボタンの状態
                    //  通信中はボタンを押せない / 通信完了後にボタンが押せる
                    trafficFree: true,

                    // モーダルウィンドウ内の [削除] ボタンの状態
                    buttonStatus: true,

                    // モーダルウィンドウ表示するカートの内容
                    cartOnModal: {},

                    // カート内の一覧表示用
                    carts: [
                        <c:forEach var="cart" items="${carts}" varStatus="cart_loop">
                        {
                            id: <c:out value="${cart.id}"/>,
                            itemId: <c:out value="${cart.item_id}"/>,
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
                        this.buttonStatus = true
                        this.cartOnModal = cart

                        // モーダルウィンドウを表示する
                        const confirmationModal = new bootstrap.Modal(
                            document.getElementById('confirmationModal'),
                            {backdrop: 'static', keyboard: true, focus: true}
                        )

                        confirmationModal.show()
                    },
                    quantityChanged: function (cart) {
                        // 数値以外は「1」に矯正する
                        if (0 === cart.quantity.toString().length) {
                            cart.quantity = 1
                        }

                        // 非同期通信のまえに、通信中フラグを設定する
                        app.trafficFree = false

                        // 非同期通信でカート数量を送信する
                        <c:url value="/cart/change_quantity" var="cart_change_quantity_url"/>
                        axios({
                            method: 'post',
                            url: '<c:out value="${cart_change_quantity_url}"/>',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                            },
                            responseType: 'json',
                            data: new URLSearchParams(
                                {
                                    cartId: cart.id.toString(),
                                    quantity: cart.quantity.toString(),
                                }
                            ).toString(),
                        }).then(
                            response => {
                                // Vue.js の data を更新する
                                app.carts = response.data

                                // 通信が完了したら、ボタンを有効化する。
                                app.trafficFree = true
                            }
                        )
                    },
                    sendDeletionRequest: function (cart) {
                        // モーダルウィンドウ内の [削除] ボタンを無効化する
                        this.buttonStatus = false

                        // 削除のリクエストを送信する
                        const form = document.getElementById('cart_del_' + cart.id)
                        form.submit()
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
                            quantities += parseInt(cart.quantity, 10)
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
                    },

                    /**
                     * @return {String} [配達先・お支払い方法] 要素のクラス
                     *
                     * 通信状態の有無に応じてクラスを使い分ける
                     */
                    classAttrNextButton: function () {
                        if (this.trafficFree) {
                            return 'btn btn-primary'
                        }

                        return 'btn btn-primary disabled'
                    }
                },
            })
        </script>
    </c:param>
</c:import>
