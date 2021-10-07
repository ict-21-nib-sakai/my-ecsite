<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
