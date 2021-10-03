package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.*;
import java.util.HashMap;

public class Item extends ModelMethods {
    /**
     * テーブル名
     */
    private final static String TABLE_NAME = "items";

    /**
     * カラム名, エイリアス, 型名
     */
    private static HashMap<String, HashMap<String, String>> columns = new HashMap<>();

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
        final String sql = buildSelectSql(TABLE_NAME, columns)
            + " AND id = ?";

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
     * @return ResultSet から Item オブジェクトにする
     */
    public static Item make(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.properties = makeProperties(resultSet, columns);

        return item;
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
}
