package me.megmilk.myecsite.http.controllers;

import me.megmilk.myecsite.models.Cart;
import me.megmilk.myecsite.services.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * カート内の1つの商品の数量を変更する。
 *
 * @implNote 複数の商品の数量を変更は想定していません。
 */
@WebServlet(name = "cart_change_quantity", urlPatterns = {"/cart/change_quantity"})
public class CartChangeQuantityController extends HttpServlet {
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
        try {
            final List<Cart> carts = CartService.changeQuantity(request);

            // TODO JSON Vue.js でそのまま使える JSON 形式を返す
            StringBuilder json = new StringBuilder();
            for (Cart cart : carts) {
                if (json.length() >= 1) {
                    json.append(",");
                }

                // FIXME JSON はエンコードしないといけない。
                //  商品名にマルチバイト文字とか、'{' や ':' や ',' などが含まれていると
                //  ブラウザ解釈できないし
                //  null が文字列の "null" で返ってきている。
                json.append(String.format(
                    "{id: %d, itemId: %d, name:\"%s\", color:\"%s\", maker:\"%s\", quantity: %d, price: %d}",
                    cart.getId(),
                    cart.getItem_id(),
                    cart.getItem().getName(),
                    cart.getItem().getColor(),
                    cart.getItem().getMaker(),
                    cart.getQuantity(),
                    cart.getItem().getPrice()
                ));
            }

            response.addHeader("Cache-Control", "no-cache, no-store");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println(
                "[" + json.toString() + "]"
            );

        } catch (SQLException | NumberFormatException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }
    }
}
