package me.megmilk.myecsite.models;
import me.megmilk.myecsite.base.ModelAbstract;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
abstract public class AbstractTest {
    /**
     * 単体テスト用データの登録
     */
    protected void seed() throws SQLException, IOException {
        final String sql_abs_path = seederAbsolutePath();
        final Path file = Paths.get(sql_abs_path);

        // ファイルを1行ずつ読み、sql 変数に追記する
        final StringBuilder sql = new StringBuilder();
        try (BufferedReader buffered_reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = buffered_reader.readLine()) != null) {
                sql.append(line);
                sql.append("\n");
            }
        }

        // 全テーブルを初期化して、単体テスト用のデータを登録
        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql.toString())) {
            statement.execute();
        }
    }

    /**
     * @return 単体テスト用の SQL ファイルの絶対 Path を返す
     */
    private String seederAbsolutePath() {
        // この単体テストのパッケージ名を取得
        final String package_name = this
            .getClass()
            .getPackage()
            .getName();

        // このプロジェクトの絶対Pathを取得
        final String project_dir_abs_path = new File(".")
            .getAbsoluteFile()
            .getParent();

        return project_dir_abs_path
            + File.separator
            + "src" + File.separator + "test" + File.separator + "java"
            + File.separator
            + (package_name.replace(".", File.separator))
            + File.separator
            + "seeder.sql";
    }
}
