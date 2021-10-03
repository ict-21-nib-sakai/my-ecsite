package me.megmilk.myecsite.models;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    /**
     * プライマリキーを指定した検索ができること
     */
    @Test
    void findTest3() throws SQLException {
        final int TEST_ID = 1;
        final Item item = Item.find(TEST_ID);

        assertNotNull(item);
        assertEquals(TEST_ID, item.getId());
    }

    /**
     * 存在しないプライマリキーを指定した場合 null が返ってくること
     */
    @Test
    void findTest4() throws SQLException {
        final int INVALID_ID = Integer.MAX_VALUE;
        final Item item = Item.find(INVALID_ID);

        assertNull(item);
    }
}
