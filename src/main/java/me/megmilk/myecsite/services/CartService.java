package me.megmilk.myecsite.services;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.models.Cart;
import me.megmilk.myecsite.models.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class CartService {
    /**
     * カート内の商品を列挙
     */
    public static List<Cart> enumerate(HttpServletRequest request) throws SQLException {
        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        final User user = flashBag.getUser();

        return Cart.enumerate(user.getId());
    }

    /**
     * カート内の合計金額を計算
     */
    public static int sum(List<Cart> carts) throws SQLException {
        if (null == carts || 0 == carts.size()) {
            return 0;
        }

        int sum = 0;
        for (Cart cart : carts) {
            sum += cart.getItem().getPrice() * cart.getQuantity();
        }

        return sum;
    }

    /**
     * カート内の商品数量の合計を計算
     */
    public static int totalQuantity(HttpServletRequest request) throws SQLException {
        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        final User user = flashBag.getUser();

        if (null == user) {
            return 0;
        }

        final List<Cart> carts = Cart.enumerate(user.getId());
        int totalQuantity = 0;

        for (Cart cart : carts) {
            totalQuantity += cart.getQuantity();
        }

        return totalQuantity;
    }
}
