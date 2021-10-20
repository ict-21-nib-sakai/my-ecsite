package me.megmilk.myecsite.http.responses;

import me.megmilk.myecsite.models.Cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * カート数量変更で戻り値として返す JSON の外側
 * <p>
 * 下記の構成
 * <ul>
 *     <li>CartsJson ← <strong>当クラス</strong><ul>
 *         <li>CartJson</li>
 *     </ul></li>
 * </ul>
 */
public class CartsJson {
    private final List<CartJson> carts = new ArrayList<>();

    public List<CartJson> getCarts() {
        return carts;
    }

    public static CartsJson make(List<Cart> carts) throws SQLException {
        CartsJson instance = new CartsJson();

        for (Cart cart : carts) {
            final CartJson cartJson = new CartJson();
            cartJson
                .setId(cart.getId())
                .setItemId(cart.getItem_id())
                .setColor(cart.getItem().getColor())
                .setMaker(cart.getItem().getMaker())
                .setPrice(cart.getItem().getPrice())
                .setQuantity(cart.getQuantity());

            instance
                .getCarts()
                .add(cartJson);
        }

        return instance;
    }
}
