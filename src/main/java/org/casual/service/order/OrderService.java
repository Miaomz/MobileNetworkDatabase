package org.casual.service.order;

import org.casual.entity.Order;
import org.casual.util.ResultMessage;
import org.casual.util.UsageType;

import java.time.LocalDate;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public interface OrderService {

    /**
     * get the orders that user has made
     * @param uid user id
     * @return the orders that user has made
     */
    List<Order> queryOrders(long uid);

    /**
     * the user orders the pack
     * @param uid user id
     * @param pid pack id
     * @return result message
     */
    ResultMessage makeOrder(long uid, long pid);

    /**
     * cancel order immediately
     * @param orderId order id
     * @return result message
     */
    ResultMessage cancelOrderImmediately(long orderId);

    /**
     * let system not renew the pack automatically
     * @param orderId order id
     * @return result message
     */
    ResultMessage cancelOrderNextMonth(long orderId);

    /**
     * make all orders which is still renewing renewed
     * it should only be called at the beginning of one month
     * @return result message
     */
    ResultMessage renewOrder();

    /**
     * get the free part according to the packs that user has ordered
     * @param uid user id
     * @param type usage type
     * @param date indicator of the month
     * @return the quantity that could be obtained for free
     */
    Double getFreeQuantity(long uid, UsageType type, LocalDate date);
}
