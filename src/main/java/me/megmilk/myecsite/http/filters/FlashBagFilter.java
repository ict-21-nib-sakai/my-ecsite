package me.megmilk.myecsite.http.filters;

import me.megmilk.myecsite.http.FlashBag;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "FlashBagFilter")
public class FlashBagFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        FlashBag flashBag = null;

        try {
            flashBag = new FlashBag((HttpServletRequest) request);
        } catch (SQLException e) {
            // TODO ログ, エラーページを表示
            e.printStackTrace();
        }

        request.setAttribute("flashBag", flashBag);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
