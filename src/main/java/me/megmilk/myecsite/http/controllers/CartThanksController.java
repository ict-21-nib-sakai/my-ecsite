package me.megmilk.myecsite.http.controllers;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.models.Order;
import me.megmilk.myecsite.services.CartService;
import me.megmilk.myecsite.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * ご注文完了ページ
 */
@WebServlet(name = "cart_thanks", urlPatterns = {"/cart/thanks", "/cart/thanks/*"})
public class CartThanksController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * ご注文完了ページ表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        Order order = null;

        try {
            order = OrderService.find(request);
        } catch (SQLException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        // 存在しない注文IDや他者の注文は参照させない
        if (null == order) {
            FlashBag.setErrorTitle(request, "ご注文が見つかりませんでした。");

            response.sendRedirect(
                request.getContextPath()
            );

            return;
        }

        request.setAttribute("order", order);

        request
            .getRequestDispatcher("/WEB-INF/views/cart_thanks.jsp")
            .forward(request, response);
    }

    /**
     * カート 最終確認ページからのフォーム送信
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        // TODO カートが空っぽでないことを検査する
        //  カートの内容は DB テーブルなので、他のタブや他のブラウザで数量が変更されることを考慮する。
        //  POST リクエスト内に内容を含めて送信させ、コントローラー側で整合性を検査する。

        Order order = new Order();

        try {
            order = CartService.placeAnOrder(request);
        } catch (SQLException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        // TODO implements
        response.sendRedirect(
            request.getContextPath() + "/cart/thanks/" + order.getId()
        );
    }
}
