package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Item extends ModelMethods {
    /**
     * テーブル名
     */
    public final static String TABLE_NAME = "items";

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
     * プライマリキーによる商品検索
     */
    public static Item find(int id) throws SQLException {
        final String sql = "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " ," + Category.buildAllColumns(Category.TABLE_NAME, Category.COLUMNS())
            + " FROM " + TABLE_NAME
            + " INNER JOIN categories "
            + " ON categories.id = " + TABLE_NAME + ".category_id"
            + " WHERE " + TABLE_NAME + ".deleted_at is null"
            + " AND categories.deleted_at is null"
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
     * 商品名の部分一致による商品検索
     */
    public static List<Item> search(String itemName, int limit, int offset) throws SQLException {
        final String sql = "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " ," + Category.buildAllColumns(Category.TABLE_NAME, Category.COLUMNS())
            + " FROM " + TABLE_NAME
            + " INNER JOIN categories "
            + " ON categories.id = " + TABLE_NAME + ".category_id"
            + " WHERE " + TABLE_NAME + ".deleted_at is null"
            + " AND categories.deleted_at is null"
            + " AND " + TABLE_NAME + ".name LIKE ?"
            + " LIMIT ? OFFSET ?";

        try (final PreparedStatement statement = prepareStatement(sql)) {
            statement.setString(1, "%" + itemName + "%");
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            try (final ResultSet resultSet = statement.executeQuery()) {
                return makeList(resultSet);
            }
        }
    }

    /**
     * カテゴリによる検索
     */
    public static List<Item> search(int categoryId, int limit, int offset) throws SQLException {
        final String sql = "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " ," + Category.buildAllColumns(Category.TABLE_NAME, Category.COLUMNS())
            + " FROM " + TABLE_NAME
            + " INNER JOIN categories "
            + " ON categories.id = " + TABLE_NAME + ".category_id"
            + " WHERE " + TABLE_NAME + ".deleted_at is null"
            + " AND categories.deleted_at is null"
            + " AND " + TABLE_NAME + ".category_id = ?"
            + " LIMIT ? OFFSET ?";

        try (final PreparedStatement statement = prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            try (final ResultSet resultSet = statement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    final Item item = make(resultSet);
                    items.add(item);
                }

                return items;
            }
        }
    }

    /**
     * 商品名の部分一致とカテゴリによる商品検索
     */
    public static List<Item> search(String itemName, int categoryId, int limit, int offset) throws SQLException {
        final String sql = "SELECT "
            + buildAllColumns(TABLE_NAME, columns)
            + " ," + Category.buildAllColumns(Category.TABLE_NAME, Category.COLUMNS())
            + " FROM " + TABLE_NAME
            + " INNER JOIN categories "
            + " ON " + Category.TABLE_NAME + ".id = " + TABLE_NAME + ".category_id"
            + " WHERE " + TABLE_NAME + ".deleted_at is null"
            + " AND " + Category.TABLE_NAME + ".deleted_at is null"
            + " AND " + TABLE_NAME + ".name LIKE ?"
            + " AND " + TABLE_NAME + ".category_id = ?"
            + " LIMIT ? OFFSET ?";

        try (final PreparedStatement statement = prepareStatement(sql)) {
            statement.setString(1, "%" + itemName + "%");
            statement.setInt(2, categoryId);
            statement.setInt(3, limit);
            statement.setInt(4, offset);

            try (final ResultSet resultSet = statement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    final Item item = make(resultSet);
                    items.add(item);
                }

                return items;
            }
        }
    }

    /**
     * @return ResultSet から Item オブジェクトにする
     */
    public static Item make(ResultSet resultSet) throws SQLException {
        final Item item = new Item();
        final Category category = Category.make(resultSet);

        item.properties = makeProperties(resultSet, columns);
        item.properties.put("category", category);

        return item;
    }

    /**
     * @return ResultSet から List<Item> オブジェクトにする
     */
    public static List<Item> makeList(ResultSet resultSet) throws SQLException {
        List<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            final Item item = make(resultSet);
            items.add(item);
        }

        return items;
    }

    public int getId() {
        return (int) properties.get("id");
    }

    public String getName() {
        return (String) properties.get("name");
    }

    public String getMaker() {
        return (String) properties.get("maker");
    }

    public int getCategory_id() {
        return (int) properties.get("category_id");
    }

    public String getColor() {
        return (String) properties.get("color");
    }

    public int getPrice() {
        return (int) properties.get("price");
    }

    public int getStock() {
        return (int) properties.get("stock");
    }

    public boolean isRecommended() {
        return (boolean) properties.get("recommended");
    }

    public boolean isSuspended() {
        return (boolean) properties.get("suspended");
    }

    public Timestamp getCreated_at() {
        return (Timestamp) properties.get("created_at");
    }

    public Timestamp getUpdated_at() {
        return (Timestamp) properties.get("updated_at");
    }

    public Timestamp getDeleted_at() {
        return (Timestamp) properties.get("deleted_at");
    }

    public Category getCategory() {
        return (Category) properties.get("category");
    }
}
