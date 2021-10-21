package me.megmilk.myecsite.http.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * カート 最終確認ページ
 */
@WebServlet(name = "cart_confirmation", urlPatterns = {"/cart/confirmation"})
public class CartConfirmationController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * 確認ページ表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        // このページはWebブラウザにキャシュさせない
        response.addHeader("Cache-Control", "no-cache, no-store");

        // TODO implements
    }

    /**
     * 入力ページからのフォーム送信
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        // TODO implements
        //  - バリデーション
        //    - 不備があれば cart/payment にリダイレクト。
        //    - 不備がなければ cart/confirmation (同じURLにGETリクエスト) にリダイレクト
    }
}
