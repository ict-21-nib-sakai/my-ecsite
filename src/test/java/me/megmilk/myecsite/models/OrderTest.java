package me.megmilk.myecsite.models;

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
}
