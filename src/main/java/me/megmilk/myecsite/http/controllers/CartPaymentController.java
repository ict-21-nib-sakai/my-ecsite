package me.megmilk.myecsite.http.controllers;

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

/**
 * 配達先・お支払い方法入力ページ
 */
@WebServlet(name = "cart_payment", urlPatterns = {"/cart/payment"})
public class CartPaymentController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * 入力ページ表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        // このページはWebブラウザにキャシュさせない
        response.addHeader("Cache-Control", "no-cache, no-store");

        List<Cart> carts = new ArrayList<>();
        int sum = 0;
        int totalQuantity = 0;

        try {
            carts = CartService.enumerate(request);
            sum = CartService.sum(carts);
            totalQuantity = CartService.totalQuantity(request);
        } catch (SQLException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        // カートに商品が1つもない場合、カートページにリダイレクトする
        if (0 == carts.size()) {
            response.sendRedirect(
                request.getContextPath() + "/cart"
            );

            return;
        }

        request.setAttribute("carts", carts);
        request.setAttribute("totalQuantity", totalQuantity);
        request.setAttribute("sum", sum);

        request
            .getRequestDispatcher("/WEB-INF/views/cart_payment.jsp")
            .forward(request, response);
    }

    /**
     * 入力ページからのフォーム送信
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        // TODO implements
    }
}
