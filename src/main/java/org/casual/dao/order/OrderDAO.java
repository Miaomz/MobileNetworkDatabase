package org.casual.dao.order;

import org.casual.entity.Order;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
public interface OrderDAO {

    ResultMessage addOrder(Order order);

    ResultMessage deleteOrder(long oid);

    ResultMessage updateOrder(Order order);

    Order getOrder(long uid);

    List<Order> getOrderList();
}