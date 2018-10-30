package org.casual.dao.order;

import org.casual.dao.util.Templar;
import org.casual.entity.Order;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
public class OrderDAOImpl implements OrderDAO {

    @Override
    public ResultMessage addOrder(Order order) {
        return Templar.update("INSERT INTO USER_ORDER(uid, pid, month) VALUES(?, ?, ?)", order.getUid(), order.getPid(), order.getMonth());
    }

    @Override
    public ResultMessage deleteOrder(long oid) {
        return Templar.update("DELETE FROM USER_ORDER WHERE orderId = ?", oid);
    }

    @Override
    public ResultMessage updateOrder(Order order) {
        return Templar.update("UPDATE USER_ORDER SET uid = ?, pid = ?, month = ?, renewing = ? WHERE orderId = ?",
                order.getUid(), order.getPid(), order.getMonth(), order.isRenewing(), order.getOrderId());
    }

    @Override
    public Order getOrder(long orderId) {
        return (Order) Templar.getOne("SELECT * FROM USER_ORDER WHERE orderId = ?", Order.class, orderId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getOrderList() {
        return Templar.getList("SELECT * FROM USER_ORDER", Order.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getOrderByUser(long uid) {
        return Templar.getList("SELECT * FROM USER_ORDER WHERE uid = ?", Order.class, uid);
    }
}
