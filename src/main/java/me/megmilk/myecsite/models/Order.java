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

    final static String SQL_TEMPLATE =
        "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " ," + User.buildAllColumns(User.TABLE_NAME, User.COLUMNS())
            + " ," + OrderDetail.buildAllColumns(OrderDetail.TABLE_NAME, OrderDetail.COLUMNS())
            + " ," + Item.buildAllColumns(Item.TABLE_NAME, Item.COLUMNS())
            + " ," + Category.buildAllColumns(Category.TABLE_NAME, Category.COLUMNS())
            + " FROM " + TABLE_NAME
            + " INNER JOIN " + User.TABLE_NAME
            + " ON " + User.TABLE_NAME + ".id = " + TABLE_NAME + ".user_id"
            + " INNER JOIN " + OrderDetail.TABLE_NAME
            + " ON " + OrderDetail.TABLE_NAME + ".order_id = " + TABLE_NAME + ".id"
            + " INNER JOIN " + Item.TABLE_NAME
            + " ON " + Item.TABLE_NAME + ".id = " + OrderDetail.TABLE_NAME + ".item_id"
            + " INNER JOIN " + Category.TABLE_NAME
            + " ON " + Category.TABLE_NAME + ".id = " + Item.TABLE_NAME + ".category_id"
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
        final User user = User.make(resultSet);

        order.properties = makeProperties(resultSet, columns);
        order.properties.put("user", user);

        final List<OrderDetail> orderDetail = OrderDetail.makeListWithoutOrder(resultSet);
        order.properties.put("order_detail", orderDetail);

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

    public User getUser() {
        return (User) properties.get("user");
    }

    public List<OrderDetail> getOrderDetails() {
        return (List<OrderDetail>) properties.get("order_detail");
    }
}
