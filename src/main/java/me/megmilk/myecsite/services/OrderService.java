package me.megmilk.myecsite.services;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.models.Order;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class OrderService {
    /**
     * パラメーターに該当する注文1レコードぶんの Order のインスタンスを返す
     *
     * @return 存在しないプライマリキーや、他者の注文を参照した場合は null を返します。
     */
    public static Order find(HttpServletRequest request) throws SQLException, NumberFormatException {
        final int orderId = Integer.parseInt(
            request.getPathInfo().substring(1)
        );

        // プライマリキー (注文ID) で注文を検索
        final Order order = Order.find(orderId);

        // 存在しない注文IDを渡されたときは null を返す。
        if (null == order) {
            return null;
        }

        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");

        // 他者の注文は参照させない。
        if (flashBag.getUser().getId() != order.getUser_id()) {
            return null;
        }

        return order;
    }
}
