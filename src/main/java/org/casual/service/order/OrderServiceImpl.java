package org.casual.service.order;

import org.casual.dao.datautil.DaoFactory;
import org.casual.dao.order.OrderDAO;
import org.casual.entity.Order;
import org.casual.entity.PackOffer;
import org.casual.service.pack.PackService;
import org.casual.service.usage.UsageService;
import org.casual.service.user.UserService;
import org.casual.util.JsonUtil;
import org.casual.util.ResultMessage;
import org.casual.util.UsageType;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO = DaoFactory.getInstance().getOrderDAO();

    private UserService userService;
    private PackService packService;
    private UsageService usageService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPackService(PackService packService) {
        this.packService = packService;
    }

    public void setUsageService(UsageService usageService) {
        this.usageService = usageService;
    }

    @Override
    public List<Order> queryOrders(long uid) {
        List<Order> orders = orderDAO.getOrderList();
        orders.removeIf(order -> order.getUid() != uid);

        orders.forEach(order -> System.out.println(JsonUtil.toJson(order)));//presentation
        return orders;
    }

    @Override
    public ResultMessage makeOrder(long uid, long pid) {
        ResultMessage message = orderDAO.addOrder(new Order(0, uid, pid, LocalDate.now(), true));
        if (message == ResultMessage.SUCCESS){
            userService.spendMoney(uid, packService.getPack(pid).getFee());
        }
        return message;
    }

    @Override
    public ResultMessage cancelOrderImmediately(long orderId) {
        Order order = orderDAO.getOrder(orderId);
        ResultMessage message = orderDAO.deleteOrder(orderId);
        if (message == ResultMessage.SUCCESS){
            userService.refundMoney(order.getUid(), packService.getPack(order.getPid()).getFee());
        }
        return message;
    }

    @Override
    public ResultMessage cancelOrderNextMonth(long orderId) {
        Order order = orderDAO.getOrder(orderId);
        order.setRenewing(false);
        return orderDAO.updateOrder(order);
    }

    @Override
    public ResultMessage renewOrder() {
        List<Order> orderList = orderDAO.getOrderList();
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        orderList.removeIf(order -> !order.isRenewing() || !isSameMonth(lastMonth, order.getMonth()));

        ResultMessage message = ResultMessage.SUCCESS;
        for (Order order : orderList) {
            if (makeOrder(order.getUid(), order.getPid()) == ResultMessage.FAILURE){
                message = ResultMessage.FAILURE;
            }
        }
        return message;
    }

    @Override
    public Double getFreeQuantity(long uid, UsageType type, LocalDate date) {
        List<Order> orders = orderDAO.getOrderList();
        orders.removeIf(order -> order.getUid() != uid || !isSameMonth(date, order.getMonth()));

        List<PackOffer> offers = new LinkedList<>();
        for (Order order : orders) {
            for (PackOffer packOffer : packService.getPackOfferByPack(packService.getPack(order.getPid()))) {
                if (packOffer.getOfferType() == type.ordinal()){
                    offers.add(packOffer);
                }
            }
        }

        double sum = 0;
        for (PackOffer offer : offers) {
            sum += offer.getQuantity();
        }
        return sum;
    }

    /**
     * check if two local dates have the same year and month values
     * @param date1 local date
     * @param date2 local date
     * @return is the same month
     */
    private boolean isSameMonth(LocalDate date1, LocalDate date2){
        return date1.getYear() == date2.getYear() && date1.getMonthValue() == date2.getMonthValue();
    }
}
