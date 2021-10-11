package me.megmilk.myecsite.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 前回の HttpServletRequest オブジェクトを次のビューに引き継ぐ
 */
@WebFilter(filterName = "InheritPreviousRequest")
public class InheritPreviousRequest implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        request.setAttribute("prevRequest", request);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
