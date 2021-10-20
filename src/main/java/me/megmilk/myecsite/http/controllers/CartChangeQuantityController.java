package me.megmilk.myecsite.http.controllers;

import com.google.gson.Gson;
import me.megmilk.myecsite.http.responses.CartsJson;
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

            // JSON 形式を返す
            response.addHeader("Cache-Control", "no-cache, no-store");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            final PrintWriter writer = response.getWriter();
            final Gson gson = new Gson();
            final CartsJson cartsJson = CartsJson.make(carts);

            writer.println(
                gson.toJson(cartsJson.getCarts())
            );
        } catch (SQLException | NumberFormatException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }
    }
}
