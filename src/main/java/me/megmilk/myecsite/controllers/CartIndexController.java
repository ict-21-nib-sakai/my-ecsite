package me.megmilk.myecsite.controllers;

import me.megmilk.myecsite.models.Cart;
import me.megmilk.myecsite.services.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "cart", urlPatterns = {"/cart"})
public class CartIndexController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * カート内一覧表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        List<Cart> carts = new ArrayList<>();

        try {
            carts = CartService.enumerate(request);
        } catch (SQLException e) {
            // TODO ログ, エラーページを表示
            e.printStackTrace();
        }

        request.setAttribute("carts", carts);

        request
            .getRequestDispatcher("/WEB-INF/views/cart.jsp")
            .forward(request, response);
    }
}
