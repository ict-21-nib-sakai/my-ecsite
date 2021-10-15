package me.megmilk.myecsite.http.controllers;

import me.megmilk.myecsite.models.Item;
import me.megmilk.myecsite.services.IndexService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * トップページ
 */
@WebServlet(name = "index", urlPatterns = {""})
public class IndexController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        List<Item> items = new ArrayList<>();

        try {
            items = IndexService.search(request);
        } catch (SQLException e) {
            // TODO ログ, エラーページを表示
            e.printStackTrace();
        }

        request.setAttribute("items", items);

        request
            .getRequestDispatcher("/WEB-INF/views/index.jsp")
            .forward(request, response);
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        response.sendRedirect(
            request.getContextPath()
        );
    }
}
