<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta content="text/html" charset="UTF-8">
    <title>
        ${param.title}くじらカフェ Online Store
    </title>
    <%@ include file="../partial/html_head.jsp" %>
</head>
<body>

<header>
    <%@ include file="../partial/body_header_nav.jsp" %>
</header>

<main id="app">
    <div class="py-5 bg-light mt-3">
        ${param.content}
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14"></script>
${param.script}
</body>
</html>
