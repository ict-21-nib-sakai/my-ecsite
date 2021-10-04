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

    final static String SQL_TEMPLATE =
        "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " ," + Order.buildAllColumns(Order.TABLE_NAME, Order.COLUMNS())
            + " ," + Item.buildAllColumns(Item.TABLE_NAME, Item.COLUMNS())
            + " ," + Category.buildAllColumns(Category.TABLE_NAME, Category.COLUMNS())
            + " ," + User.buildAllColumns(User.TABLE_NAME, User.COLUMNS())
            + " FROM " + TABLE_NAME
            + " INNER JOIN " + Item.TABLE_NAME
            + " ON " + Item.TABLE_NAME + ".id = " + TABLE_NAME + ".item_id"
            + " INNER JOIN " + Order.TABLE_NAME
            + " ON " + Order.TABLE_NAME + ".id = " + TABLE_NAME + ".order_id"
            + " INNER JOIN " + Category.TABLE_NAME
            + " ON " + Category.TABLE_NAME + ".id = " + Item.TABLE_NAME + ".category_id"
            + " INNER JOIN " + User.TABLE_NAME
            + " ON " + User.TABLE_NAME + ".id = " + Order.TABLE_NAME + ".user_id"
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
     * @return ResultSet から OrderDetail オブジェクトにする
     */
    public static OrderDetail make(ResultSet resultSet) throws SQLException {
        final OrderDetail orderDetail = new OrderDetail();
        final Item item = Item.make(resultSet);
        final Order order = Order.makeWithoutOrderDetails(resultSet);

        orderDetail.properties = makeProperties(resultSet, columns);
        orderDetail.properties.put("item", item);
        orderDetail.properties.put("order", order);

        return orderDetail;
    }

    /**
     * @return ResultSet から List<OrderDetail> オブジェクトにする
     * @implNote このメソッドは、循環参照を避けるため order プロパティはセットしません。
     * また ResultSet は、このメソッドを呼び出す前に一度 .next() されているものとします。
     */
    public static List<OrderDetail> makeListWithoutOrder(ResultSet resultSet) throws SQLException {
        final List<OrderDetail> orderDetails = new ArrayList<>();

        do {
            final OrderDetail orderDetail = make(resultSet);
            orderDetails.add(orderDetail);
        } while (resultSet.next());

        return orderDetails;
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

    public Item getItem() {
        return (Item) properties.get("item");
    }

    public Order getOrder() {
        return (Order) properties.get("order");
    }
}
