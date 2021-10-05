<%@ page pageEncoding="UTF-8" %>
<%--@elvariable id="categories" type="java.util.List<me.megmilk.myecsite.models.Category>"--%>
<nav class="navbar navbar-expand-lg navbar-light fixed-top bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">くじらカフェ Online Store</a>
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
                        カテゴリ
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="categoryDropdown">
                        <c:forEach var="category" items="${categories}">
                            <li>
                                <a class="dropdown-item"
                                   href="<c:out value="${pageContext.request.contextPath}/category/"/><c:out value="${category.id}"/>">
                                    <c:out value="${category.name}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="#">カート</a>
                </li>
            </ul>

            <ul class="navbar-nav d-flex">
                <li class="nav-item">
                    <a class="nav-link disabled"
                       href="#" tabindex="-1"
                       aria-disabled="true">ログイン</a>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle"
                       href="#" id="navbarDropdown"
                       role="button"
                       data-bs-toggle="dropdown"
                       aria-expanded="false">
                        ●●○○さん
                    </a>
                    <ul class="dropdown-menu dropdown-menu-lg-end"
                        aria-labelledby="navbarDropdown">
                        <li>
                            <a class="dropdown-item" href="#">注文履歴</a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="#">アカウント情報</a>
                        </li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li>
                            <a class="dropdown-item" href="#">
                                ログアウト
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
