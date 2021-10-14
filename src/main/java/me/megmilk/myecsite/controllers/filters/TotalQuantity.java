package me.megmilk.myecsite.controllers.filters;

import me.megmilk.myecsite.services.CartService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter
public class TotalQuantity implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * カート内の商品数量の合計を計算し、リクエストスコープに代入する
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        int totalQuantity = 0;

        try {
            totalQuantity = CartService.totalQuantity(
                (HttpServletRequest) request
            );
        } catch (SQLException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        request.setAttribute("totalQuantity", totalQuantity);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
