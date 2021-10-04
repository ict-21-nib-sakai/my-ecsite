package me.megmilk.myecsite.models;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    /**
     * プライマリキーを指定した検索ができること
     */
    @Test
    void findTest1() throws SQLException {
        final int TEST_ID = 1;
        final Item item = Item.find(TEST_ID);

        assertNotNull(item);
        assertEquals(TEST_ID, item.getId());
    }

    /**
     * 存在しないプライマリキーを指定した場合 null が返ってくること
     */
    @Test
    void findTest2() throws SQLException {
        final int INVALID_ID = Integer.MAX_VALUE;
        final Item item = Item.find(INVALID_ID);

        assertNull(item);
    }

    /**
     * Category オブジェクトも参照できること
     */
    @Test
    void findTest3() throws SQLException {
        final int TEST_ID = 1;
        final Item item = Item.find(TEST_ID);

        assert item != null;
        assertNotNull(item.getCategory());
        assertEquals(item.getCategory_id(), item.getCategory().getId());
    }

    /**
     * 商品名の部分一致による商品検索
     */
    @Test
    void searchTest1() throws SQLException {
        final String TEST_ITEM_NAME = "猫";
        final int TEST_LIMIT = 3;
        final int TEST_OFFSET = 0;

        final List<Item> items = Item.search(
            TEST_ITEM_NAME,
            TEST_LIMIT,
            TEST_OFFSET
        );

        assertTrue(items.size() >= 1);
        assertTrue(items.size() <= TEST_LIMIT);
        for (Item item : items) {
            assertTrue(
                item.getName().contains(TEST_ITEM_NAME)
            );
        }
    }

    /**
     * 商品名の部分一致による商品検索 (1件もヒットしない場合)
     */
    @Test
    void searchTest2() throws SQLException {
        final String TEST_INVALID_ITEM_NAME = "無効な商品名";
        final int TEST_LIMIT = 3;
        final int TEST_OFFSET = 0;

        final List<Item> items = Item.search(
            TEST_INVALID_ITEM_NAME,
            TEST_LIMIT,
            TEST_OFFSET
        );

        assertEquals(0, items.size());
    }

    /**
     * カテゴリによる検索
     */
    @Test
    void searchTest3() throws SQLException {
        final int TEST_CATEGORY_ID = 2;
        final int TEST_LIMIT = 3;
        final int TEST_OFFSET = 0;

        final List<Item> items = Item.search(
            TEST_CATEGORY_ID,
            TEST_LIMIT,
            TEST_OFFSET
        );

        assertTrue(items.size() >= 1);
        assertTrue(items.size() <= TEST_LIMIT);
        for (Item item : items) {
            assertEquals(TEST_CATEGORY_ID, item.getCategory_id());
        }
    }

    /**
     * カテゴリによる検索 (1件もヒットしない場合)
     */
    @Test
    void searchTest4() throws SQLException {
        final int TEST_INVALID_CATEGORY_ID = Integer.MAX_VALUE;
        final int TEST_LIMIT = 3;
        final int TEST_OFFSET = 0;

        final List<Item> items = Item.search(
            TEST_INVALID_CATEGORY_ID,
            TEST_LIMIT,
            TEST_OFFSET
        );

        assertEquals(0, items.size());
    }

    /**
     * 商品名の一部とカテゴリによる検索
     */
    @Test
    void searchTest5() throws SQLException {
        final String TEST_ITEM_NAME = "猫";
        final int TEST_CATEGORY_ID = 4;
        final int TEST_LIMIT = 3;
        final int TEST_OFFSET = 0;

        final List<Item> items = Item.search(
            TEST_ITEM_NAME,
            TEST_CATEGORY_ID,
            TEST_LIMIT,
            TEST_OFFSET
        );

        assertTrue(items.size() >= 1);
        assertTrue(items.size() <= TEST_LIMIT);
        for (Item item : items) {
            assertTrue(item.getName().contains(TEST_ITEM_NAME));
            assertEquals(TEST_CATEGORY_ID, item.getCategory_id());
        }
    }

    /**
     * 商品名の一部とカテゴリによる検索 (1件もヒットしない場合 その1)
     */
    @Test
    void searchTest6() throws SQLException {
        final String TEST_INVALID_ITEM_NAME = "無効な商品名";
        final int TEST_CATEGORY_ID = 4;
        final int TEST_LIMIT = 3;
        final int TEST_OFFSET = 0;

        final List<Item> items = Item.search(
            TEST_INVALID_ITEM_NAME,
            TEST_CATEGORY_ID,
            TEST_LIMIT,
            TEST_OFFSET
        );

        assertEquals(0, items.size());
    }

    /**
     * 商品名の一部とカテゴリによる検索 (1件もヒットしない場合 その2)
     */
    @Test
    void searchTest7() throws SQLException {
        final String TEST_ITEM_NAME = "猫";
        final int TEST_INVALID_CATEGORY_ID = Integer.MAX_VALUE;
        final int TEST_LIMIT = 3;
        final int TEST_OFFSET = 0;

        final List<Item> items = Item.search(
            TEST_ITEM_NAME,
            TEST_INVALID_CATEGORY_ID,
            TEST_LIMIT,
            TEST_OFFSET
        );

        assertEquals(0, items.size());
    }
}
