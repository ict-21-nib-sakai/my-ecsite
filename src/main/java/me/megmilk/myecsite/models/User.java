package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import me.megmilk.myecsite.base.ModelMethods;

import java.sql.*;
import java.util.HashMap;

public class User extends ModelMethods {
    /**
     * テーブル名
     */
    public final static String TABLE_NAME = "users";

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
     * email によるユーザー検索
     */
    public static User find(String email) throws SQLException {
        final String sql = buildSelectSql(TABLE_NAME, columns)
            + " AND email = ?";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
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
     * @return ResultSet から User オブジェクトにする
     */
    public static User make(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.properties = makeProperties(resultSet, columns);

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
