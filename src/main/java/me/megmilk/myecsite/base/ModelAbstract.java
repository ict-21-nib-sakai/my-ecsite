package me.megmilk.myecsite.base;

import java.sql.*;

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
}
