<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
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
            <tr>
                <td>商品名○○○</td>
                <td data-title="色">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="メーカー">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="数量" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            2
                        </span>
                </td>
                <td data-title="単価" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            ¥12,345
                        </span>
                </td>
            </tr>
            <tr>
                <td>商品名○○○</td>
                <td data-title="色">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="メーカー">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="数量" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            2
                        </span>
                </td>
                <td data-title="単価" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            ¥12,345
                        </span>
                </td>
            </tr>
            <tr>
                <td>商品名○○○</td>
                <td data-title="色">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="メーカー">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="数量" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            2
                        </span>
                </td>
                <td data-title="単価" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            ¥12,345
                        </span>
                </td>
            </tr>
            <tr>
                <td>商品名○○○</td>
                <td data-title="色">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="メーカー">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="数量" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            2
                        </span>
                </td>
                <td data-title="単価" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            ¥12,345
                        </span>
                </td>
            </tr>
            <tr>
                <td>商品名○○○</td>
                <td data-title="色">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="メーカー">
                        <span class="resp-flex-8">
                            ○○○
                        </span>
                </td>
                <td data-title="数量" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            2
                        </span>
                </td>
                <td data-title="単価" class="text-end resp-text-start">
                        <span class="resp-flex-8">
                            ¥12,345
                        </span>
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="4" class="text-end">
                    合計数量 10
                </td>
                <td class="text-end">
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
            ご自宅
            <address>
                鳥取県○○市○○町○○123
            </address>
        </div>
    </c:param>
</c:import>
