package me.megmilk.myecsite.models;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailTest extends AbstractTest {
    /**
     * プライマリキーを指定した注文詳細が検索できること
     */
    @Test
    void findTest1() throws SQLException, IOException {
        seed();

        final int TEST_ID = 1;
        final OrderDetail orderDetail = OrderDetail.find(TEST_ID);

        assertNotNull(orderDetail);
        assertEquals(TEST_ID, orderDetail.getOrder_id());
        assertNotNull(orderDetail.getOrder());
        assertNotNull(orderDetail.getItem());

        // 循環参照も可能である
        assertNotNull(orderDetail.getOrder().getOrderDetails());
    }

    /**
     * 注文IDを指定した注文詳細が検索できること
     */
    @Test
    void enumerateTest1() throws SQLException, IOException {
        seed();

        final int TEST_ORDER_ID = 1;
        final List<OrderDetail> orderDetails = OrderDetail.enumerate(TEST_ORDER_ID);

        assertNotNull(orderDetails);
        assertTrue(orderDetails.size() >= 1);
        assertNotNull(orderDetails.get(0).getOrder());
        assertNotNull(orderDetails.get(0).getItem());
    }
}
