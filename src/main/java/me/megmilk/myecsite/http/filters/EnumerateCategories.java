package me.megmilk.myecsite.http.filters;
import me.megmilk.myecsite.models.Category;
import me.megmilk.myecsite.services.CategoryService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "EnumerateCategories")
public class EnumerateCategories implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        List<Category> categories = new ArrayList<>();

        try {
            categories = CategoryService.enumerate();
        } catch (SQLException e) {
            // TODO ログに記録, エラーページを表示
            e.printStackTrace();
        }

        request.setAttribute("categories", categories);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
