package me.megmilk.myecsite.base;

import me.megmilk.myecsite.models.User;

import java.sql.*;
import java.util.HashMap;

/**
 * モデルのスーパークラス
 */
abstract public class ModelAbstract implements AutoCloseable {
    /**
     * データベース接続URL (サーバー, ポート, データベース名)
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/my-ecsite";

    /**
     * データベース接続時のユーザー名
     */
    private static final String USER = "my-ecsite-role";

    /**
     * データベース接続時のパスワード
     */
    private static final String PASSWORD = "password";

    /**
     * データベース接続のキャッシュ
     */
    private static Connection connection = null;

    /**
     * JDBC ドライバが読み込み済みであるか否か
     */
    private static boolean jdbcDriverLoaded = false;

    @Override
    public void close() throws SQLException {
        if (null == ModelAbstract.connection) {
            // 接続されてなかったら何もしない
            return;
        }

        if (ModelAbstract.connection.isClosed()) {
            // 接続が終了していたら何もしない
            return;
        }

        // 接続を終了する
        ModelAbstract.connection.close();
    }

    /**
     * SQL実行準備
     */
    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        if (null == connection || connection.isClosed()) {
            connection = connect();
        }

        return connection.prepareStatement(sql);
    }

    /**
     * データベース・サーバーへ接続
     */
    private static Connection connect() throws SQLException {
        // JDBC ドライバを読み込む
        ModelAbstract.loadJdbcDriver();

        // データベース接続し、キャッシュする
        if (ModelAbstract.connection == null || ModelAbstract.connection.isClosed()) {
            ModelAbstract.connection = DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
            );
        }

        // キャッシュ済みの接続情報を返す
        return ModelAbstract.connection;
    }

    /**
     * JDBC ドライバを読み込む
     */
    private static void loadJdbcDriver() {
        // JDBCドライバの読み込み済みの場合、何もしない。
        if (ModelAbstract.jdbcDriverLoaded) {
            return;
        }

        // JDBCドライバを読み込み、スタティック変数に「読み込み済み」であること保持しておく。
        try {
            final Class<?> loadedDriver = Class.forName("org.postgresql.Driver");

            ModelAbstract.jdbcDriverLoaded = loadedDriver
                .getName()
                .length() >= 1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
