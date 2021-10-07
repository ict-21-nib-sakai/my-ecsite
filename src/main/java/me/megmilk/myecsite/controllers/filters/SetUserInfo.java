package me.megmilk.myecsite.controllers.filters;

import me.megmilk.myecsite.models.User;
import me.megmilk.myecsite.services.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 一般利用者のログイン情報をリクエストスコープに代入する。
 * <p>
 * セッションスコープには、ユーザー ID のみ代入されている。
 * リクエストスコープは、ユーザー ID から得た常に最新の最新のユーザー情報を代入する。
 *
 * <ol>
 * <li>int userId セッションスコープに代入されているユーザーID</li>
 * <li>User user ユーザーIDから得た User インスタンス</li>
 * </ol>
 */
@WebFilter(filterName = "SetUserInfo")
public class SetUserInfo implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        final HttpServletRequest http_request = (HttpServletRequest) request;

        // セッション変数に記録されているユーザー ID を参照する
        //  なければリクエストスコープに null を代入して、次のフィルターに引き継ぐ。
        if (null == http_request.getSession().getAttribute("userId")) {
            request.setAttribute("user", null);

            chain.doFilter(request, response);

            return;
        }

        final int userId = (int) http_request
            .getSession()
            .getAttribute("userId");

        // ユーザー ID から User オブジェクトを得る
        User user;
        try {
            user = UserService.find(userId);
        } catch (SQLException e) {
            // TODO ログに記録、エラーページを表示
            user = null;
        }

        // リクエストスコープに User のインスタンスを渡す
        request.setAttribute("user", user);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
