<%@ page pageEncoding="UTF-8" %>
<%--@elvariable id="prevRequest" type="javax.servlet.http.HttpServletRequest"--%>
<%--@elvariable id="flashBag" type="me.megmilk.myecsite.http.FlashBag"--%>
<nav class="navbar navbar-expand-lg navbar-light fixed-top bg-light">
    <div class="container-fluid">
        <a class="navbar-brand"
           href="${pageContext.request.contextPath}">
            くじらカフェ Online Store
        </a>
        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent"
                aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item d-flex">
                    <form class="d-flex">
                        <div class="input-group w-100">
                            <span class="input-group-text" id="basic-addon1">
                                <i class="bi bi-search"></i>
                            </span>

                            <input type="text"
                                   name="keyword"
                                   value="<c:out value="${prevRequest.getParameter('keyword')}"/>"
                                   class="form-control"
                                   placeholder="(例) キャットフード"
                                   aria-label="Input group example"
                                   aria-describedby="basic-addon1">
                        </div>
                    </form>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle"
                       href="#" id="categoryDropdown"
                       role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        <i class="bi bi-card-heading"></i>
                        カテゴリ
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="categoryDropdown">
                        <c:forEach var="category" items="${flashBag.categories}">
                            <li>
                                <a class="dropdown-item"
                                   href="<c:out value="${pageContext.request.contextPath}/category/"/><c:out value="${category.id}"/>">
                                    <c:out value="${category.name}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>

                <c:if test="${not empty flashBag.user}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="<c:out value="${pageContext.request.contextPath}/cart"/>">
                            <i class="bi bi-cart"></i>
                            カート
                            <c:if test="${flashBag.totalQuantity >= 1}">
                                <span class="translate-middle badge rounded-pill bg-danger ms-3">
                                    <c:choose>
                                        <c:when test="${flashBag.totalQuantity <= 99}">
                                            <c:out value="${flashBag.totalQuantity}"/>
                                        </c:when>
                                        <c:otherwise>
                                            +99
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="visually-hidden">
                                        カート内の商品数量
                                    </span>
                                </span>
                            </c:if>
                        </a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav d-flex">
                <c:if test="${empty flashBag.user}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="<c:out value="${pageContext.request.contextPath}/login"/>"
                           tabindex="-1">
                            <i class="bi bi-lock"></i>
                            ログイン
                        </a>
                    </li>
                </c:if>

                <c:if test="${not empty flashBag.user}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle"
                           href="#" id="navbarDropdown"
                           role="button"
                           data-bs-toggle="dropdown"
                           aria-expanded="false">
                            <c:out value="${flashBag.user.name}"/>さん
                        </a>
                        <ul class="dropdown-menu dropdown-menu-lg-end"
                            aria-labelledby="navbarDropdown">
                            <li>
                                <a class="dropdown-item" href="#">
                                    <i class="bi bi-clock-history"></i>注文履歴
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="#">
                                    <i class="bi bi-person-circle"></i>
                                    アカウント情報
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="dropdown-item" href="#" id="logout_link">
                                    <i class="bi bi-unlock"></i>
                                    ログアウト
                                </a>
                                <form action="${pageContext.request.contextPath}/logout"
                                      method="post"
                                      id="logout_form">
                                        <%-- TODO CSRF トークン --%>
                                </form>
                                <script>
                                    const logout_link = document.getElementById('logout_link')

                                    logout_link.addEventListener('click', event => {
                                        event.preventDefault()

                                        document
                                            .getElementById('logout_form')
                                            .submit()
                                    })
                                </script>
                            </li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
