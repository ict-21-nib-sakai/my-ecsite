package me.megmilk.myecsite.services;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.http.MySession;
import me.megmilk.myecsite.models.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class CartService {
    /** 配送先：ご自宅 */
    public static final String DELIVERY_HOME = "home";

    /** 配送先：配達先を指定 */
    public static final String DELIVERY_OPTIONAL = "optional";

    public static final String[][] DELIVERY_OPTIONS = {
        {DELIVERY_HOME, "ご自宅"},
        {DELIVERY_OPTIONAL, "配達先を指定"}
    };

    /** お支払い方法：クレジットカード */
    public static final String PAYMENT_METHOD_CARD = "card";

    /** お支払い方法：代金引換 */
    public static final String PAYMENT_METHOD_CASH_ON_DELIVERY = "cod";

    /** お支払い方法：銀行振込 */
    public static final String PAYMENT_METHOD_BANK = "bank";

    public static final String[][] PAYMENT_METHODS = {
        {PAYMENT_METHOD_CARD, "クレジットカード"},
        {PAYMENT_METHOD_CASH_ON_DELIVERY, "代金引換"},
        {PAYMENT_METHOD_BANK, "銀行振込"}
    };

    /**
     * パラメーターに該当するカート1レコードぶんの Cart のインスタンスを返す
     *
     * @return 存在しないプライマリキーや、他者のカートを参照した場合は null を返します。
     */
    public static Cart find(HttpServletRequest request) throws SQLException, NumberFormatException {
        final int cartId = Integer.parseInt(
            request.getParameter("cartId")
        );

        final Cart cart = Cart.find(cartId);

        // 存在しないカートを参照した場合は null を返す
        if (null == cart) {
            return null;
        }

        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        final User user = flashBag.getUser();

        // 他者のカートを参照した場合は null を返す
        if (cart.getUser_id() != user.getId()) {
            return null;
        }

        return cart;
    }

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

    /**
     * カートに商品を数量1で追加する
     */
    public static Cart add(HttpServletRequest request
    ) throws SQLException, NumberFormatException {
        final int itemId = Integer.parseInt(
            request.getPathInfo().substring(1)
        );

        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        final User user = flashBag.getUser();

        Cart.add(user.getId(), itemId, 1);

        return Cart.find(user.getId(), itemId);
    }

    /**
     * カート内の1つの商品の数量を変更する。
     *
     * @return 数量を変更した商品に関係なくカートの全内容
     * @implNote 複数の商品の数量を変更は想定していません。
     */
    public static List<Cart> changeQuantity(HttpServletRequest request
    ) throws SQLException, NumberFormatException {
        final Cart cart = find(request);

        if (null == cart) {
            FlashBag.setErrorTitle(request, "数量を変更できませんでした。");

            return enumerate(request);
        }

        final int quantity = Integer.parseInt(
            request.getParameter("quantity")
        );
        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        final User user = flashBag.getUser();

        // カート内の数量を変更
        Cart.change(user.getId(), cart.getItem_id(), quantity);

        FlashBag.setInfoTitle(
            request,
            "「" + cart.getItem().getName() + "」の数量を変更しました。"
        );

        return enumerate(request);
    }

    /**
     * カート内の商品を削除
     *
     * @return 削除前の Cart インスタンス
     */
    public static Cart deleteCartItem(HttpServletRequest request
    ) throws SQLException, NumberFormatException {
        final int cartId = Integer.parseInt(
            request.getPathInfo().substring(1)
        );

        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        final User user = flashBag.getUser();
        final Cart cart = Cart.find(cartId);

        if (null == cart) {
            return null;
        }

        // 他者のカート内容は削除させない
        if (cart.getUser_id() != user.getId()) {
            return null;
        }

        return Cart.delete(cartId);
    }

    /**
     * カート 最終確認フォームから注文確定
     */
    public static Order placeAnOrder(HttpServletRequest request) throws SQLException {
        // TODO トランザクション処理で行うこと。

        final MySession session = new MySession(request);
        final String paymentMethod = session.getFormValue("payment_method");
        final String deliveryOption = session.getFormValue("delivery_option");

        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        final User user = flashBag.getUser();
        String shippingAddress = user.getHome_address();

        if (DELIVERY_OPTIONAL.equals(deliveryOption)) {
            shippingAddress = session.getFormValue("optional_address");
        }

        // 'orders' テーブルにレコードを追加
        final Order order = Order.add(user, paymentMethod, deliveryOption, shippingAddress);

        // 'order_details' テーブルにレコードを追加
        final List<Cart> carts = CartService.enumerate(request);
        OrderDetail.add(order, carts);

        // 'items' テーブルから在庫を減らす
        for (Cart cart : carts) {
            final Item item = Item.subtract(
                cart.getItem_id(),
                cart.getQuantity()
            );

            if (null == item) {
                throw new SQLException("商品ID: " + cart.getItem_id() + ". この商品は存在しません。");
            }
        }

        // 'carts' テーブルを空っぽにする
        Cart.empty(user.getId());

        // セッション変数に保持していたすべての入力値を削除する
        MySession.expungeAllValue(request);

        return Order.find(order.getId());
    }
}
