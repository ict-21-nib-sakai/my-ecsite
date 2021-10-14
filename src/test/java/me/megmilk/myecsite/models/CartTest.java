package me.megmilk.myecsite.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CartTest extends AbstractTest {
    /**
     * プライマリキーを指定した検索ができること
     */
    @Test
    void findTest1() throws SQLException, IOException {
        seed();

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
    void findTest2() throws SQLException, IOException {
        seed();

        final int TEST_INVALID_CART_ID = Integer.MAX_VALUE;
        final Cart cart = Cart.find(TEST_INVALID_CART_ID);

        assertNull(cart);
    }

    /**
     * ユーザーIDと商品IDを指定したカート検索
     */
    @Test
    void findTest3() throws SQLException, IOException {
        seed();

        final int TEST_USER_ID = 1;
        final int TEST_ITEM_ID = 9;

        final Cart cart = Cart.find(TEST_USER_ID, TEST_ITEM_ID);

        assertNotNull(cart);
        assertEquals(TEST_USER_ID, cart.getUser_id());
        assertEquals(TEST_ITEM_ID, cart.getItem_id());
    }

    /**
     * ユーザーIDと商品IDを指定したカート検索 (無効なユーザーIDの場合)
     */
    @Test
    void findTest4() throws SQLException, IOException {
        seed();

        final int TEST_INVALID_USER_ID = Integer.MAX_VALUE;
        final int TEST_ITEM_ID = 9;

        final Cart cart = Cart.find(TEST_INVALID_USER_ID, TEST_ITEM_ID);

        assertNull(cart);
    }

    /**
     * ユーザーIDと商品IDを指定したカート検索 (無効な商品IDの場合)
     */
    @Test
    void findTest5() throws SQLException, IOException {
        seed();

        final int TEST_USER_ID = 1;
        final int TEST_INVALID_ITEM_ID = Integer.MAX_VALUE;

        final Cart cart = Cart.find(TEST_USER_ID, TEST_INVALID_ITEM_ID);

        assertNull(cart);
    }

    /**
     * ユーザーIDを指定したカート内一覧取得
     */
    @Test
    void enumerateTest1() throws SQLException, IOException {
        seed();

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
    void enumerateTest2() throws SQLException, IOException {
        seed();

        final int TEST_INVALID_USER_ID = Integer.MAX_VALUE;
        final List<Cart> carts = Cart.enumerate(TEST_INVALID_USER_ID);

        assertEquals(0, carts.size());
    }

    /**
     * カートに商品を追加 (カートに未投入の商品の場合)
     */
    @Test
    void addTest1() throws SQLException, IOException {
        seed();

        final int TEST_USER_ID = 1;
        final int TEST_ITEM_ID = 8;
        final int TEST_QUANTITY = 3;

        Cart.add(TEST_USER_ID, TEST_ITEM_ID, TEST_QUANTITY);

        Cart cart = Cart.find(TEST_USER_ID, TEST_ITEM_ID);

        assert cart != null;
        assertEquals(TEST_QUANTITY, cart.getQuantity());
        assertEquals(TEST_ITEM_ID, cart.getItem_id());
    }

    /**
     * カートに商品を追加 (すでにカート内に入っている数量を加算)
     */
    @Test
    void addTest2() throws SQLException, IOException {
        seed();

        final int TEST_USER_ID = 1;
        final List<Cart> carts = Cart.enumerate(TEST_USER_ID);

        final int TEST_ITEM_ID = 9;
        Cart preUpdateCart = null;

        for (Cart cart : carts) {
            if (TEST_ITEM_ID != cart.getItem_id()) {
                continue;
            }

            preUpdateCart = cart;
        }

        if (null == preUpdateCart) {
            fail("テスト用の seeder.sql を見直してください。");

            return;
        }

        // カート内の商品数量を追加
        final int TEST_QUANTITY = 5;
        Cart.add(TEST_USER_ID, TEST_ITEM_ID, TEST_QUANTITY);

        final Cart postUpdatedCart = Cart.find(preUpdateCart.getId());

        assert postUpdatedCart != null;

        assertEquals(
            TEST_QUANTITY + preUpdateCart.getQuantity(),
            postUpdatedCart.getQuantity()
        );
    }
}
