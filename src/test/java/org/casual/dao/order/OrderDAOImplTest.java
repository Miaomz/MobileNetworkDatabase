package org.casual.dao.order;

import org.casual.Main;
import org.casual.entity.Order;
import org.casual.util.ResultMessage;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class OrderDAOImplTest {

    private OrderDAO orderDAO;

    @Before
    public void setUp() throws Exception {
        Main.main(new String[0]);
        orderDAO = new OrderDAOImpl();
    }

    @Test
    public void addOrder() {
        assertEquals(ResultMessage.SUCCESS, orderDAO.addOrder(new Order(0, 1, 1, LocalDate.now(), true)));
    }

    @Test
    public void deleteOrder() {
        orderDAO.addOrder(new Order(0, 1, 2, LocalDate.now(), true));
        orderDAO.deleteOrder(1);
    }

    @Test
    public void updateOrder() {
        orderDAO.updateOrder(new Order(0, 1, 3, LocalDate.now(), true));
    }

    @Test
    public void getOrder() {
        orderDAO.getOrder(1);
    }

    @Test
    public void getOrderList() {
        for (Order order : orderDAO.getOrderList()) {
            System.out.println(order.getOrderId() + " " + order.getUid() + " " + order.getPid());
        }
    }
}