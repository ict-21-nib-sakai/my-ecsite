<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="flash_info_title" type="String"--%>
<%--@elvariable id="flash_info_messages" type="String[]"--%>
<%--@elvariable id="flash_error_title" type="String"--%>
<%--@elvariable id="flash_error_messages" type="String[]"--%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta content="text/html" charset="UTF-8">
    <title>
        ログイン | くじらカフェ Online Store
    </title>
    <%@ include file="partial/html_head.jsp" %>
</head>
<body>
<header>
    <%@ include file="partial/body_header_nav.jsp" %>
</header>
<main>
    <div class="album py-5 bg-light mt-3">
        <div class="container">

            <c:if test="${not empty flash_error_title}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-circle"></i>
                    <c:out value="${flash_error_title}"/>
                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="alert"
                            aria-label="Close">
                    </button>
                </div>
            </c:if>

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
        </div>
    </div>
</main>
</body>
</html>
