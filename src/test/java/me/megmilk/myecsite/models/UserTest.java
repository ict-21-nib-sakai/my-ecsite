package me.megmilk.myecsite.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class UserTest extends AbstractTest {
    /**
     * email を指定した検索ができること
     */
    @Test
    void findTest1() throws SQLException, IOException {
        seed();

        final String TEST_EMAIL = "taro@example.com";
        final User user = User.find(TEST_EMAIL);

        assertNotNull(user);
        assertEquals(TEST_EMAIL, user.getEmail());
    }

    /**
     * 存在しない email を指定した場合 null が返ってくること
     */
    @Test
    void findTest2() throws SQLException, IOException {
        seed();

        final String INVALID_EMAIL = "invalid@example.com";
        final User user = User.find(INVALID_EMAIL);

        assertNull(user);
    }

    /**
     * プライマリキーを指定した検索ができること
     */
    @Test
    void findTest3() throws SQLException, IOException {
        seed();

        final int TEST_ID = 1;
        final User user = User.find(TEST_ID);

        assertNotNull(user);
        assertEquals(TEST_ID, user.getId());
    }

    /**
     * 存在しないプライマリキーを指定した場合 null が返ってくること
     */
    @Test
    void findTest4() throws SQLException, IOException {
        seed();

        final int INVALID_ID = Integer.MAX_VALUE;
        final User user = User.find(INVALID_ID);

        assertNull(user);
    }
}
