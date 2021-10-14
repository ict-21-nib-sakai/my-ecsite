package me.megmilk.myecsite.services;

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
        final User user = (User) request.getAttribute("user");

        System.out.println(user);

        return Cart.enumerate(user.getId());
    }
}
