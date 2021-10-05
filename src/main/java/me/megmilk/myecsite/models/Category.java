package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * すべてのカテゴリを列挙
     */
    public static List<Category> enumerate() throws SQLException {
        final String sql =
            "SELECT " + buildAllColumns(TABLE_NAME, columns)
            + " FROM " + TABLE_NAME
            + " WHERE " + TABLE_NAME + ".deleted_at is null"
            + " ORDER BY sequence";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
            try (final ResultSet resultSet = statement.executeQuery()) {
                List<Category> categories = new ArrayList<>();
                while (resultSet.next()) {
                    final Category category = make(resultSet);
                    categories.add(category);
                }

                return categories;
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

    public int getSequence() {
        return (int) properties.get("sequence");
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
