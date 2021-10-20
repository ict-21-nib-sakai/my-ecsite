package me.megmilk.myecsite.http.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        // TODO カートに商品が1つもない場合、カートページにリダイレクトする

        // TODO implements
        System.out.println("TODO cart_payment");
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
