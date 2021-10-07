package me.megmilk.myecsite.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SessionStart")
public class SessionStart implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        final HttpServletRequest http_request = (HttpServletRequest) request;

        // セッション開始
        http_request.getSession(true);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
