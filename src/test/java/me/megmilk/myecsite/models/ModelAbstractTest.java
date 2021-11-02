package me.megmilk.myecsite.models;

import me.megmilk.myecsite.base.ModelAbstract;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ModelAbstractTest extends AbstractTest {
    /**
     * 「トランザクション開始」で自動コミットモードが false になること
     */
    @Test
    void transactionBeginTest() throws SQLException, IOException, NoSuchFieldException, IllegalAccessException {
        seed();

        // private な変数を参照する
        final Field field = ModelAbstract.class.getDeclaredField("connection");
        field.setAccessible(true);

        // テスト対象のメソッドを実行
        ModelAbstract.transactionBegin();

        // テスト対象のクラスは抽象クラスなので、継承して具象クラスにする。
        final class Reflection extends ModelAbstract {
        }
        final Reflection reflection = new Reflection();
        final Connection connection = (Connection) field.get(reflection);

        assertFalse(connection.getAutoCommit());
    }

    /**
     * 「トランザクション ロールバック」で値が更新されないこと
     */
    @Test
    void transactionRollbackTest1() throws SQLException, IOException {
        seed();

        final int TEST_USER_ID = 1;
        final String PSEUDO_USER_NAME = "Jane Doe";

        // ユーザー名を変更する前の user インスタンス
        final User userPreUpdate = User.find(TEST_USER_ID);

        // トランザクション開始
        ModelAbstract.transactionBegin();

        // ユーザー名を更新
        final String sql = "UPDATE users SET name = ? WHERE id = ?";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
            statement.setString(1, PSEUDO_USER_NAME);
            statement.setInt(2, TEST_USER_ID);
            statement.executeUpdate();
        }

        // テスト対象のメソッドを実行 (ロールバック)
        ModelAbstract.transactionRollback();

        // ユーザー名を変更した後の user インスタンス
        final User userPostUpdate = User.find(TEST_USER_ID);

        assert userPreUpdate != null;
        assert userPostUpdate != null;

        // ユーザー名は変更されていないこと
        assertNotEquals(PSEUDO_USER_NAME, userPostUpdate.getName());
        assertEquals(userPreUpdate.getName(), userPostUpdate.getName());
    }

    /**
     * 「トランザクション コミット」で値が更新されること
     */
    @Test
    void transactionCommitTest1() throws SQLException, IOException {
        seed();

        final int TEST_USER_ID = 1;
        final String NEW_USER_NAME = "Jane Doe";

        // ユーザー名を変更する前の user インスタンス
        final User userPreUpdate = User.find(TEST_USER_ID);

        // トランザクション開始
        ModelAbstract.transactionBegin();

        // ユーザー名を更新
        final String sql = "UPDATE users SET name = ? WHERE id = ?";

        try (final PreparedStatement statement = ModelAbstract.prepareStatement(sql)) {
            statement.setString(1, NEW_USER_NAME);
            statement.setInt(2, TEST_USER_ID);
            statement.executeUpdate();
        }

        // テスト対象のメソッドを実行 (コミット)
        ModelAbstract.transactionCommit();

        // ユーザー名を変更した後の user インスタンス
        final User userPostUpdate = User.find(TEST_USER_ID);

        assert userPreUpdate != null;
        assert userPostUpdate != null;

        // ユーザー名が変更されていること
        assertNotEquals(userPreUpdate.getName(), userPostUpdate.getName());
        assertEquals(NEW_USER_NAME, userPostUpdate.getName());
    }
}
