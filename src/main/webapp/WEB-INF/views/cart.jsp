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
<main>
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
                            <f:formatNumber value="${cart.quantity}" pattern="###,###"/>
                        </td>
                        <td class="text-end">
                            ¥<f:formatNumber value="${cart.item.price}" pattern="###,###"/>
                        </td>
                        <td class="text-center">
                            <c:url value="/cart/del/${cart.item_id}" var="cart_del_url"/>
                            <form action="${cart_del_url}"
                                  method="post"
                                  id="<c:out value="cart_del_url_${cart.id}"/>">
                                    <%-- TODO CSRF トークン --%>
                            </form>
                            <button type="button"
                                    class="btn btn-danger">
                                <i class="bi bi-cart-x"></i>
                                削除
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" class="text-end">
                        合計数量 <c:out value="${flashBag.totalQuantity}"/>
                    </td>
                    <td colspan="2" class="text-end">
                        合計金額 ¥<f:formatNumber value="${sum}" pattern="###,###"/>
                    </td>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>
</main>

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
                    <dd>{{ item_name }}</dd>
                    <dt>メーカー</dt>
                    <dd>{{ manufacturer }}</dd>
                    <dt>単価</dt>
                    <dd>{{ price | numberFormat }}円</dd>
                    <dt>数量</dt>
                    <dd>{{ amount }}個</dd>
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
                    @click="sendDeletionRequest"
                    :disabled="!buttonEnabled"
                    type="button"
                    class="btn btn-danger">
                    <i class="bi bi-cart-x"></i>
                    削除する
                </button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14"></script>
</body>
</html>
