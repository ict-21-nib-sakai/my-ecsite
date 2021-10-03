package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.Model;

import java.sql.*;
import java.util.HashMap;

public class User extends Model {
    /**
     * テーブル名
     */
    private final static String TABLE_NAME = "users";

    /**
     * カラム名, エイリアス, 型名
     */
    private static HashMap<String, HashMap<String, String>> columns = new HashMap<>();

    /**
     * カラム名とその値
     */
    private final HashMap<String, Object> properties = new HashMap<>();

    /**
     * カラム名、そのエイリアス、その型を代入する
     */
    public static void setupColumns() throws SQLException {
        if (columns.size() >= 1) {
            return;
        }

        columns = enumColumns(TABLE_NAME);
    }

    /**
     * email によるユーザー検索
     */
    public static User find(String email) throws SQLException {
        setupColumns();

        final String sql = buildSelectSql(TABLE_NAME, columns)
            + " AND email = ?";

        try (final PreparedStatement statement = Model.prepareStatement(sql)) {
            statement.setString(1, email);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                return make(resultSet);
            }
        }
    }

    /**
     * プライマリキーによるユーザー検索
     */
    public static User find(int id) throws SQLException {
        setupColumns();

        final String sql = buildSelectSql(TABLE_NAME, columns)
            + " AND id = ?";

        try (final PreparedStatement statement = Model.prepareStatement(sql)) {
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
     * @return ResultSet から User オブジェクトにする
     */
    public static User make(ResultSet resultSet) throws SQLException {
        User user = new User();

        for (String columnName : columns.keySet()) {
            switch (columns.get(columnName).get("data_type")) {
                case "integer":
                    user.properties.put(
                        columnName,
                        resultSet.getInt(columns.get(columnName).get("alias"))
                    );
                    break;

                case "character varying":
                case "text":
                    user.properties.put(
                        columnName,
                        resultSet.getString(columns.get(columnName).get("alias"))
                    );
                    break;

                case "boolean":
                    user.properties.put(
                        columnName,
                        resultSet.getBoolean(columns.get(columnName).get("alias"))
                    );
                    break;

                case "timestamp without time zone":
                    user.properties.put(
                        columnName,
                        resultSet.getTimestamp(columns.get(columnName).get("alias"))
                    );
                    break;
            }
        }

        return user;
    }

    public int getId() {
        return (int) properties.get("id");
    }

    public String getEmail() {
        return (String) properties.get("email");
    }

    public String getName() {
        return (String) properties.get("name");
    }

    public boolean getSuspended() {
        return (boolean) properties.get("suspended");
    }

    public Timestamp getCreated_at() {
        return (Timestamp) properties.get("created_at");
    }

    public Timestamp getUpdated_at() {
        return (Timestamp) properties.get("getUpdated_at");
    }

    public Timestamp getDeleted_at() {
        return (Timestamp) properties.get("deleted_at");
    }

    public String getPassword() {
        return (String) properties.get("password");
    }
}
