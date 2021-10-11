package me.megmilk.myecsite.controllers;

import me.megmilk.myecsite.models.Item;
import me.megmilk.myecsite.services.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 商品詳細ページ
 */
@WebServlet(name = "item", urlPatterns = {"/item/*"})
public class ItemController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        Item item = new Item();

        try {
            item = ItemService.find(request);
        } catch (SQLException | NumberFormatException e) {
            // TODO ログ, エラーページを表示
            e.printStackTrace();
        }

        request.setAttribute("item", item);

        request
            .getRequestDispatcher("/WEB-INF/views/item.jsp")
            .forward(request, response);
    }
}
