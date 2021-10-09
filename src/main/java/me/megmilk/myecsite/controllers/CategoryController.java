package me.megmilk.myecsite.controllers;

import me.megmilk.myecsite.controllers.filters.FlashMessage;
import me.megmilk.myecsite.models.Category;
import me.megmilk.myecsite.models.Item;
import me.megmilk.myecsite.services.CategoryService;
import me.megmilk.myecsite.services.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリページ
 */
@WebServlet(name = "category", urlPatterns = {"/category/*"})
public class CategoryController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        List<Item> items = new ArrayList<>();
        Category category = null;

        try {
            items = ItemService.enumerate(request);
            category = CategoryService.find(request);
        } catch (SQLException | NumberFormatException e) {
            // TODO ログ, エラーページを表示
            e.printStackTrace();
        }

        if (null == category) {
            // 存在しないカテゴリが指定された場合、Web サイトのトップページを表示する
            request.getSession().setAttribute(
                FlashMessage.FLASH_ERROR_TITLE,
                "ご指定のカテゴリはみつかりませんでした。お手数ですが、検索から商品をお探しください。"
            );

            response.sendRedirect(
                request.getContextPath()
            );

            return;
        }

        request.setAttribute("items", items);
        request.setAttribute("category", category);

        request
            .getRequestDispatcher("/WEB-INF/views/catetory.jsp")
            .forward(request, response);
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
    }
}
