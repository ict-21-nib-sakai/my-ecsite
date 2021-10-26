package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class Order extends ModelMethods {
    /**
     * テーブル名
     */
    public final static String TABLE_NAME = "orders";

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
    private static final HashMap<Integer, User> cachedUser = new HashMap<>();

    /**
     * 冗長なクエリを実行しないためのキャッシュ
     */
    private static final HashMap<Integer, List<OrderDetail>> cachedOrderDetail = new HashMap<>();

    final static String SQL_TEMPLATE =
        "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " FROM " + TABLE_NAME
            + " WHERE 1 = 1";

    /**
     * プライマリキーによる注文検索
     */
    public static Order find(int id) throws SQLException {
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
     * @return ResultSet から Order オブジェクトにする
     */
    public static Order make(ResultSet resultSet) throws SQLException {
        final Order order = new Order();
        order.properties = makeProperties(resultSet, columns);

        return order;
    }

    /**
     * @return ResultSet から Order オブジェクトにする
     * @implNote このメソッドは、循環参照を避けるため order_details プロパティはセットしません。
     */
    public static Order makeWithoutOrderDetails(ResultSet resultSet) throws SQLException {
        final Order order = new Order();
        final User user = User.make(resultSet);

        order.properties = makeProperties(resultSet, columns);
        order.properties.put("user", user);

        return order;
    }

    /**
     * 注文確定のレコードを追加する
     *
     * @param user                注文者の User インスタンス
     * @param paymentMethod       CartService.PAYMENT_METHOD_***
     * @param shippingAddressType CartService.DELIVERY_***
     * @param shippingAddress     配達先の住所
     */
    public static Order add(
        User user,
        String paymentMethod,
        String shippingAddressType,
        String shippingAddress
    ) throws SQLException {
        final String sql = "INSERT INTO orders ("
            + "   user_id"
            + " , payment_method"
            + " , shipping_address_type"
            + " , shipping_address"
            + " , user_name"
            + " , created_at"
            + " , updated_at"
            + " )"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)"
            + " RETURNING id";

        try (final PreparedStatement statement = prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, paymentMethod);
            statement.setString(3, shippingAddressType);
            statement.setString(4, shippingAddress);
            statement.setString(5, user.getName());

            final Timestamp now = new Timestamp(System.currentTimeMillis());

            statement.setTimestamp(6, now);
            statement.setTimestamp(7, now);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int primaryKey = resultSet.getInt(1);

                return Order.find(primaryKey);
            }
        }
    }

    public int getId() {
        return (int) properties.get("id");
    }

    public int getUser_id() {
        return (int) properties.get("user_id");
    }

    public String getPayment_method() {
        return (String) properties.get("payment_method");
    }

    public String getShipping_address_type() {
        return (String) properties.get("shipping_address_type");
    }

    public String getShipping_address() {
        return (String) properties.get("shipping_address");
    }

    public String getUser_name() {
        return (String) properties.get("user_name");
    }

    public Timestamp getCreated_at() {
        return (Timestamp) properties.get("created_at");
    }

    public Timestamp getUpdated_at() {
        return (Timestamp) properties.get("updated_at");
    }

    public User getUser() throws SQLException {
        // 冗長なクエリを実行しないために User インスタンスをキャッシュする
        if (cachedUser.size() == 0
            || null == cachedUser.get(this.getUser_id())
        ) {
            User user = User.find(this.getUser_id());
            cachedUser.put(this.getUser_id(), user);
        }

        return cachedUser.get(this.getUser_id());
    }

    public List<OrderDetail> getOrderDetails() throws SQLException {
        // 冗長なクエリを実行しないために OrderDetail インスタンスをキャッシュする
        if (cachedOrderDetail.size() == 0
            || null == cachedOrderDetail.get(this.getId())
        ) {
            List<OrderDetail> orderDetail = OrderDetail.enumerate(this.getId());
            cachedOrderDetail.put(this.getId(), orderDetail);
        }

        return cachedOrderDetail.get(this.getId());
    }
}
