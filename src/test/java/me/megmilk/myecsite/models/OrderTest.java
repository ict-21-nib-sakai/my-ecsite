package me.megmilk.myecsite.models;

import me.megmilk.myecsite.services.CartService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest extends AbstractTest {
    /**
     * プライマリキーを指定した注文検索ができること
     */
    @Test
    void findTest1() throws SQLException, IOException {
        seed();

        final int TEST_ID = 1;
        final Order order = Order.find(TEST_ID);

        assertNotNull(order);
        assertNotNull(order.getUser());
        assertEquals(order.getUser_id(), order.getUser().getId());
        assertTrue(order.getOrderDetails().size() >= 1);
    }

    /**
     * 注文確定のレコードが追加できること
     */
    @Test
    void addTest1() throws SQLException, IOException {
        seed();

        final int TEST_USER_ID = 1;
        final User TEST_USER = User.find(TEST_USER_ID);
        final String TEST_PAYMENT_METHOD = CartService.PAYMENT_METHOD_CARD;
        final String TEST_SHIPPING_ADDRESS_TYPE = CartService.DELIVERY_OPTIONAL;
        final String TEST_SHIPPING_ADDRESS = "鳥取県○○市○○町○○123";

        assert TEST_USER != null;
        Order order = Order.add(
            TEST_USER,
            TEST_PAYMENT_METHOD,
            TEST_SHIPPING_ADDRESS_TYPE,
            TEST_SHIPPING_ADDRESS
        );

        assertNotNull(order);
        assertEquals(TEST_USER_ID, order.getUser_id());
        assertEquals(TEST_PAYMENT_METHOD, order.getPayment_method());
        assertEquals(TEST_SHIPPING_ADDRESS_TYPE, order.getShipping_address_type());
        assertEquals(TEST_SHIPPING_ADDRESS, order.getShipping_address());
    }
}
