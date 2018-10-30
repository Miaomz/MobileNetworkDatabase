package org.casual.service.order;

import org.casual.Main;
import org.casual.service.util.ServiceFactory;
import org.casual.util.ResultMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class OrderServiceTest {

    private OrderService orderService;

    @Before
    public void setup(){
        Main.main(new String[0]);
        orderService = ServiceFactory.getInstance().getOrderService();
    }

    @Test
    public void renewOrder() {
        assertEquals(ResultMessage.SUCCESS, orderService.renewOrder());
    }
}