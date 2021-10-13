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
     * 冗長なクエリを実行しないためのキャッシュ
     */
    private static final HashMap<Integer, User> cachedUser = new HashMap<>();

    /**
     * 冗長なクエリを実行しないためのキャッシュ
     */
    private static final HashMap<Integer, Item> cachedItem = new HashMap<>();

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
     * ユーザーIDと商品IDを指定したカート検索
     */
    public static Cart find(int userId, int itemId) throws SQLException {
        final String sql = "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " FROM " + TABLE_NAME
            + " WHERE user_id = ?"
            + " AND item_id = ?";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, itemId);

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
     * カートに商品を追加する
     * <p>
     * すでにカート内に入っている場合は、数量を加算する
     */
    public static void add(int userId, int itemId, int quantity) throws SQLException {
        final String sql =
            "INSERT INTO carts (user_id, item_id, quantity, created_at, updated_at)"
                + " VALUES (?, ?, ?, ?, ?)"
                + " ON CONFLICT (user_id, item_id)"
                + " DO UPDATE SET"
                + "     quantity = ("
                + "         SELECT quantity"
                + "         FROM carts"
                + "         WHERE user_id = ?"
                + "         AND item_id = ?"
                + "     ) + ?"
                + "     , updated_at = ?";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
            final Timestamp now = new Timestamp(System.currentTimeMillis());

            statement.setInt(1, userId);
            statement.setInt(2, itemId);
            statement.setInt(3, quantity);
            statement.setTimestamp(4, now);
            statement.setTimestamp(5, now);
            statement.setInt(6, userId);
            statement.setInt(7, itemId);
            statement.setInt(8, quantity);
            statement.setTimestamp(9, now);

            statement.executeUpdate();
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
        // 冗長なクエリを実行しないために User インスタンスをキャッシュする
        if (cachedUser.size() == 0
            || null == cachedUser.get(this.getUser_id())
        ) {
            User user = User.find(this.getUser_id());
            cachedUser.put(this.getUser_id(), user);
        }

        return cachedUser.get(this.getUser_id());
    }

    public Item getItem() throws SQLException {
        // 冗長なクエリを実行しないために Item インスタンスをキャッシュする
        if (cachedItem.size() == 0
            || null == cachedItem.get(this.getItem_id())
        ) {
            Item item = Item.find(this.getItem_id());
            cachedItem.put(this.getItem_id(), item);
        }

        return cachedItem.get(this.getItem_id());
    }
}
