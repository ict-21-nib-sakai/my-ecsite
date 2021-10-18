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
 * カート削除コントローラー
 */
@WebServlet(name = "cart_delete", urlPatterns = {"/cart/delete/*"})
public class CartDeleteController extends HttpServlet {
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
        Cart deletedCart = null;

        try {
            deletedCart = CartService.deleteCartItem(request);
        } catch (SQLException | NumberFormatException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        try {
            if (null == deletedCart) {
                FlashBag.setErrorTitle(request, "カートから削除できませんでした。");
            } else {
                FlashBag.setInfoTitle(request, "「" + deletedCart.getItem().getName() + "」をカートから削除しました。");
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
