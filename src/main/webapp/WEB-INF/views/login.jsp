<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<c:import url="layout/app.jsp">
    <c:param name="content">
        <form action="<c:out value="${pageContext.request.contextPath}/login"/>"
              method="post">
                <%-- TODO CSRF トークン --%>
            <div class="mb-3">
                <label for="email" class="form-label">
                    メールアドレス
                </label>
                <input type="email" name="email" class="form-control" id="email">
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">
                    パスワード
                </label>
                <input type="password" name="password" class="form-control" id="password">
            </div>

            <button type="submit" class="btn btn-primary">
                ログイン
            </button>
        </form>
    </c:param>
</c:import>
