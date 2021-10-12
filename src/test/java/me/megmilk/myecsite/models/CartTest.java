package me.megmilk.myecsite.models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CartTest {
    /**
     * プライマリキーを指定した検索ができること
     */
    @Test
    void findTest1() throws SQLException {
        final int TEST_CART_ID = 1;
        final Cart cart = Cart.find(TEST_CART_ID);

        assertNotNull(cart);
        assertEquals(TEST_CART_ID, cart.getId());

        assertNotNull(cart.getUser());
        assertEquals(
            cart.getUser_id(),
            cart.getUser().getId()
        );

        assertNotNull(cart.getItem());
        assertEquals(
            cart.getItem_id(),
            cart.getItem().getId()
        );
    }

    /**
     * 存在しないプライマリキーで検索した場合 null が返ってくること
     */
    @Test
    void findTest2() throws SQLException {
        final int TEST_INVALID_CART_ID = Integer.MAX_VALUE;
        final Cart cart = Cart.find(TEST_INVALID_CART_ID);

        assertNull(cart);
    }

    /**
     * ユーザーIDを指定したカート内一覧取得
     */
    @Test
    void enumerateTest1() throws SQLException {
        final int TEST_USER_ID = 1;
        final List<Cart> carts = Cart.enumerate(TEST_USER_ID);

        assertTrue(carts.size() >= 1);
        for (Cart cart : carts) {
            assertEquals(
                TEST_USER_ID,
                cart.getUser_id()
            );

            assertEquals(
                TEST_USER_ID,
                cart.getUser().getId()
            );

            assertEquals(
                cart.getItem_id(),
                cart.getItem().getId()
            );
        }
    }

    /**
     * 無効なユーザーIDを指定したカート内一覧取得
     */
    @Test
    void enumerateTest2() throws SQLException {
        final int TEST_INVALID_USER_ID = Integer.MAX_VALUE;
        final List<Cart> carts = Cart.enumerate(TEST_INVALID_USER_ID);

        assertEquals(0, carts.size());
    }
}
