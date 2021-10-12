package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart extends ModelMethods {
    /**
     * テーブル名
     */
    public final static String TABLE_NAME = "carts";

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
     * プライマリキーを指定したカート検索
     */
    public static Cart find(int id) throws SQLException {
        final String sql = "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " FROM " + TABLE_NAME
            + " WHERE id = ?";

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
     * ユーザーIDを指定したカート内 商品一覧
     */
    public static List<Cart> enumerate(int userId) throws SQLException {
        final String sql = "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " FROM " + TABLE_NAME
            + " WHERE user_id = ?"
            + " ORDER BY created_at";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                return makeList(resultSet);
            }
        }
    }

    /**
     * @return ResultSet から Cart オブジェクトにする
     */
    public static Cart make(ResultSet resultSet) throws SQLException {
        final Cart cart = new Cart();
        cart.properties = makeProperties(resultSet, columns);

        return cart;
    }

    /**
     * @return ResultSet から List<Cart> オブジェクトにする
     */
    public static List<Cart> makeList(ResultSet resultSet) throws SQLException {
        List<Cart> carts = new ArrayList<>();
        while (resultSet.next()) {
            final Cart cart = make(resultSet);
            carts.add(cart);
        }

        return carts;
    }

    public int getId() {
        return (int) properties.get("id");
    }

    public int getUser_id() {
        return (int) properties.get("user_id");
    }

    public int getQuantity() {
        return (int) properties.get("quantity");
    }

    public int getItem_id() {
        return (int) properties.get("item_id");
    }

    public Timestamp getCreated_at() {
        return (Timestamp) properties.get("created_at");
    }

    public Timestamp getUpdated_at() {
        return (Timestamp) properties.get("updated_at");
    }

    public User getUser() throws SQLException {
        // Eager Loading せず、動的に取得してみる
        //  長文になる SQL は避けたい一方で、パフォーマンス低下が気になるが。
        //  やはり簡潔な SQL も捨てがたい。
        return User.find(this.getUser_id());
    }

    public Item getItem() throws SQLException {
        // Eager Loading せず、動的に取得してみる
        //  長文になる SQL は避けたい一方で、パフォーマンス低下が気になるが。
        //  やはり簡潔な SQL も捨てがたい。
        return Item.find(this.getItem_id());
    }
}
