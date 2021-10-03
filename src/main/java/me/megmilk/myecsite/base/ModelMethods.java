package me.megmilk.myecsite.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

abstract public class ModelMethods extends Model {
    /**
     * 引数の内容から properties を組み立てて返す
     *
     * @param resultSet SQL 実行結果。事前に .next() してあるオブジェクトを渡してください。
     * @param columns   カラム名, エイリアス, 型名
     */
    protected static HashMap<String, Object> setProperties(
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
}
