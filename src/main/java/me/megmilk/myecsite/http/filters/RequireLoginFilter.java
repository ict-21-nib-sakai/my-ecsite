package me.megmilk.myecsite.http.filters;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * ログイン必須検査
 */
@WebFilter(filterName = "RequireLoginFilter")
public class RequireLoginFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        User user = null;

        try {
            user = flashBag.getUser();
        } catch (SQLException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        // ログイン済みであれば次のフィルターに処理を渡す
        if (null != user) {
            chain.doFilter(request, response);

            return;
        }

        // ログインしていない場合は、ログインページにリダイレクトする
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        httpResponse.sendRedirect(
            httpRequest.getContextPath() + "/login"
        );
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
