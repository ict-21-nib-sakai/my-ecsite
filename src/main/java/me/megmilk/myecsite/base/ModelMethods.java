package me.megmilk.myecsite.base;

import me.megmilk.myecsite.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

abstract public class ModelMethods extends ModelAbstract {
    /**
     * 引数の内容から properties を組み立てて返す
     *
     * @param resultSet SQL 実行結果。事前に .next() してあるオブジェクトを渡してください。
     * @param columns   カラム名, エイリアス, 型名
     */
    protected static HashMap<String, Object> makeProperties(
        ResultSet resultSet,
        HashMap<String, HashMap<String, String>> columns
    ) throws SQLException {
        HashMap<String, Object> properties = new HashMap<>();

        for (String columnName : columns.keySet()) {
            switch (columns.get(columnName).get("data_type")) {
                case "integer":
                    properties.put(
                        columnName,
                        resultSet.getInt(columns.get(columnName).get("alias"))
                    );
                    break;

                case "character varying":
                case "text":
                    properties.put(
                        columnName,
                        resultSet.getString(columns.get(columnName).get("alias"))
                    );
                    break;

                case "boolean":
                    properties.put(
                        columnName,
                        resultSet.getBoolean(columns.get(columnName).get("alias"))
                    );
                    break;

                case "timestamp without time zone":
                    properties.put(
                        columnName,
                        resultSet.getTimestamp(columns.get(columnName).get("alias"))
                    );
                    break;
            }
        }

        return properties;
    }

    /**
     * @return SELECT で全カラムを取得する文字列
     */
    protected static String buildAllColumns(String tableName, HashMap<String, HashMap<String, String>> columns) {
        final StringBuilder sql = new StringBuilder();

        for (final String columnName : columns.keySet()) {
            if (sql.length() >= 1) {
                sql.append(", ");
            }

            sql.append(
                String.format(
                    "%s.%s as %s",
                    tableName,
                    columnName,
                    columns.get(columnName).get("alias")
                )
            );
        }

        return sql.toString();
    }

    /**
     * カラム名とその型を返す
     */
    protected static HashMap<String, HashMap<String, String>> enumColumns(String tableName) throws SQLException {
        final String sql =
            "SELECT"
                + "   column_name as real_name"
                + " , '" + tableName + "_' || column_name as alias"
                + " , data_type"
                + " FROM information_schema.columns"
                + " WHERE table_name = ?";

        try (final PreparedStatement statement = prepareStatement(sql)) {
            statement.setString(1, tableName);

            try (ResultSet resultSet = statement.executeQuery()) {
                final HashMap<String, HashMap<String, String>> columns = new HashMap<>();
                while (resultSet.next()) {
                    final HashMap<String, String> property = new HashMap<>();
                    property.put("alias", resultSet.getString("alias"));
                    property.put("data_type", resultSet.getString("data_type"));

                    columns.put(
                        resultSet.getString("real_name"),
                        property
                    );
                }

                return columns;
            }
        }
    }

    /**
     * 論理削除のカラムがある場合、論理削除されたレコードを除外する WHERE 文を付ける
     */
    protected static String buildSelectSql(String tableName, HashMap<String, HashMap<String, String>> columns) {
        final String sql =
            "SELECT " + User.buildAllColumns(tableName, columns)
                + " FROM " + tableName
                + " WHERE 1 = 1 ";

        if (columns.get("deleted_at").isEmpty()) {
            return sql;
        }

        return sql + " AND deleted_at is null";
    }
}
