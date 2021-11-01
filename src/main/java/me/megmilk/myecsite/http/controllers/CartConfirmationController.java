package me.megmilk.myecsite.http.controllers;

import me.megmilk.myecsite.http.MySession;
import me.megmilk.myecsite.http.validators.CartPaymentValidator;
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

        List<Cart> carts = new ArrayList<>();
        int sum = 0;
        int totalQuantity = 0;

        try {
            carts = CartService.enumerate(request);
            totalQuantity = CartService.totalQuantity(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // カートが空っぽの場合、このページは見せない。
        //  カートページにリダイレクトする。
        if (0 == carts.size()) {
            response.sendRedirect(
                request.getContextPath() + "/cart"
            );

            return;
        }

        request.setAttribute("carts", carts);
        request.setAttribute("totalQuantity", totalQuantity);
        request.setAttribute("sum", sum);
        request.setAttribute("mySession", new MySession(request));

        request
            .getRequestDispatcher("/WEB-INF/views/cart_confirmation.jsp")
            .forward(request, response);
    }

    /**
     * 入力ページからのフォーム送信
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        MySession.setFormValue(request, "delivery_option");
        MySession.setFormValue(request, "optional_address");
        MySession.setFormValue(request, "payment_method");

        // バリデーション
        boolean isValid = CartPaymentValidator.validate(request);

        // 不備がなければ cart/confirmation (同じURLにGETリクエスト) にリダイレクト
        if (isValid) {
            response.sendRedirect(
                request.getContextPath() + "/cart/confirmation"
            );

            return;
        }

        // 不備があれば cart/payment にリダイレクト
        response.sendRedirect(
            request.getContextPath() + "/cart/payment"
        );
    }
}
