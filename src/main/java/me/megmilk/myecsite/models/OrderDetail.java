package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDetail extends ModelMethods {
    /**
     * テーブル名
     */
    public final static String TABLE_NAME = "order_details";

    /**
     * カラム名, エイリアス, 型名
     */
    private static HashMap<String, HashMap<String, String>> columns = new HashMap<>();

    public static HashMap<String, HashMap<String, String>> COLUMNS() {
        return columns;
    }

    /**
     * カラム名とその値
     */
    private HashMap<String, Object> properties;

    // カラム名、そのエイリアス、その型を代入する
    static {
        try {
            columns = enumColumns(TABLE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 冗長なクエリを実行しないためのキャッシュ
     */
    private static final HashMap<Integer, Item> cachedItem = new HashMap<>();

    /**
     * 冗長なクエリを実行しないためのキャッシュ
     */
    private static final HashMap<Integer, Order> cachedOrder = new HashMap<>();

    final static String SQL_TEMPLATE =
        "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " FROM " + TABLE_NAME
            + " WHERE 1 =1";

    /**
     * プライマリキーよる注文詳細検索
     */
    public static OrderDetail find(int id) throws SQLException {
        final String sql =
            SQL_TEMPLATE
                + " AND " + TABLE_NAME + ".id = ?";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                return make(resultSet);
            }
        }
    }

    /**
     * 注文IDから注文詳細を検索
     */
    public static List<OrderDetail> enumerate(int orderId) throws SQLException {
        final String sql = "SELECT"
            + " " + buildAllColumns(TABLE_NAME, columns)
            + " FROM " + TABLE_NAME
            + " WHERE order_id = ?"
            + " ORDER BY id";

        try (final PreparedStatement statement = prepareStatement(sql)) {
            statement.setInt(1, orderId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                return makeList(resultSet);
            }
        }
    }

    /**
     * @return ResultSet から OrderDetail オブジェクトにする
     */
    public static OrderDetail make(ResultSet resultSet) throws SQLException {
        final OrderDetail orderDetail = new OrderDetail();
        orderDetail.properties = makeProperties(resultSet, columns);

        return orderDetail;
    }

    /**
     * @return ResultSet から List &lt;OrderDetail&gt; オブジェクトにする
     */
    public static List<OrderDetail> makeList(ResultSet resultSet) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (resultSet.next()) {
            final OrderDetail orderDetail = make(resultSet);
            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }

    /**
     * 注文確定のレコードを追加する
     */
    public static void add(Order order, List<Cart> carts) throws SQLException {
        for (Cart cart : carts) {
            final String sql = "INSERT INTO order_details ("
                + "   order_id"
                + " , item_id"
                + " , item_name"
                + " , item_price"
                + " , quantity"
                + " ) "
                + " VALUES (?, ?, ?, ?, ?)";

            try (final PreparedStatement statement = prepareStatement(sql)) {
                statement.setInt(1, order.getId());
                statement.setInt(2, cart.getItem_id());
                statement.setString(3, cart.getItem().getName());
                statement.setInt(4, cart.getItem().getPrice());
                statement.setInt(1, cart.getQuantity());
                statement.executeUpdate();
            }
        }
    }

    public int getId() {
        return (int) properties.get("id");
    }

    public int getOrder_id() {
        return (int) properties.get("order_id");
    }

    public int getItem_id() {
        return (int) properties.get("item_id");
    }

    public String getItem_name() {
        return (String) properties.get("item_name");
    }

    public int getItem_price() {
        return (int) properties.get("item_price");
    }

    public int getQuantity() {
        return (int) properties.get("quantity");
    }

    public Timestamp getCanceled_at() {
        return (Timestamp) properties.get("canceled_at");
    }

    public Item getItem() throws SQLException {
        // 冗長なクエリを実行しないために Order インスタンスをキャッシュする
        if (cachedItem.size() == 0
            || null == cachedItem.get(this.getItem_id())
        ) {
            Item item = Item.find(this.getItem_id());
            cachedItem.put(this.getItem_id(), item);
        }

        return cachedItem.get(this.getItem_id());
    }

    public Order getOrder() throws SQLException {
        // 冗長なクエリを実行しないために Order インスタンスをキャッシュする
        if (cachedOrder.size() == 0
            || null == cachedOrder.get(this.getOrder_id())
        ) {
            Order order = Order.find(this.getOrder_id());
            cachedOrder.put(this.getOrder_id(), order);
        }

        return cachedOrder.get(this.getOrder_id());
    }
}
