package me.megmilk.myecsite.models;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

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

        // 循環参照を防ぐため order_details プロパティは null であること
        assertNull(orderDetail.getOrder().getOrderDetails());
    }
}
