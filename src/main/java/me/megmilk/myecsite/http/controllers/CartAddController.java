package me.megmilk.myecsite.http.controllers;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.models.Cart;
import me.megmilk.myecsite.services.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * カート追加コントローラー
 */
@WebServlet(name = "cart_add", urlPatterns = {"/cart/add/*"})
public class CartAddController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        Cart cart = null;

        try {
            cart = CartService.add(request);
        } catch (SQLException | NumberFormatException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        try {
            if (null == cart) {
                FlashBag.setErrorTitle(request, "カートに追加できませんでした。");
            } else {
                FlashBag.setInfoTitle(request, "「" + cart.getItem().getName() + "」をカートに追加しました。");
            }
        } catch (SQLException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        response.sendRedirect(
            request.getContextPath() + "/cart"
        );
    }
}
