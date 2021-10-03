package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.*;
import java.util.HashMap;

public class Category extends ModelMethods {
    /**
     * テーブル名
     */
    public final static String TABLE_NAME = "categories";

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
     * プライマリキーによる検索
     */
    public static Category find(int id) throws SQLException {
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
     * @return ResultSet から Category オブジェクトにする
     */
    public static Category make(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.properties = makeProperties(resultSet, columns);

        return category;
    }

    public int getId() {
        return (int) properties.get("id");
    }

    public String getName() {
        return (String) properties.get("name");
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
