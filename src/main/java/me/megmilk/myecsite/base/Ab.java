package me.megmilk.myecsite.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

abstract public class Ab extends Model {
    /**
     *
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
