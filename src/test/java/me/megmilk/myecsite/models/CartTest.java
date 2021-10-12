package me.megmilk.myecsite.models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

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
}
