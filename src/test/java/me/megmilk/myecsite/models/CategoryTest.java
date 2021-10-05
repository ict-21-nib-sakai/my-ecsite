package me.megmilk.myecsite.models;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CategoryTest {
    /**
     * プライマリキーを指定した検索ができること
     */
    @Test
    void findTest1() throws SQLException {
        final int TEST_ID = 1;
        final Category category = Category.find(TEST_ID);

        assertNotNull(category);
        assertEquals(TEST_ID, category.getId());
    }

    /**
     * 存在しないプライマリキーを指定した場合 null が返ってくること
     */
    @Test
    void findTest2() throws SQLException {
        final int INVALID_ID = Integer.MAX_VALUE;
        final Category category = Category.find(INVALID_ID);

        assertNull(category);
    }

    /**
     * カテゴリが列挙されること
     */
    @Test
    void enumerateTest1() throws SQLException {
        final List<Category> categories = Category.enumerate();

        assertNotNull(categories);
        assertTrue(categories.size() >= 1);
    }

    /**
     * カテゴリは設定された順番で列挙されること
     */
    @Test
    void enumerateTest2() throws SQLException {
        final List<Category> categories = Category.enumerate();

        int prevSequence = Integer.MIN_VALUE;
        for (Category category: categories) {
            assertTrue(category.getSequence() > prevSequence);
            prevSequence = category.getSequence();
        }
    }
}
