package org.casual.service.order;

import org.casual.dao.order.OrderDAO;
import org.casual.dao.util.DaoFactory;
import org.casual.entity.*;
import org.casual.service.pack.PackService;
import org.casual.service.usage.UsageService;
import org.casual.service.user.UserService;
import org.casual.util.ResultMessage;
import org.casual.util.UsageType;
import org.casual.vo.MonthlyBill;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.min;
import static org.casual.util.DateTimeUtil.isSameMonth;

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
        return orderDAO.getOrderByUser(uid);
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
        if (order == null){
            return ResultMessage.FAILURE;
        }

        ResultMessage message = orderDAO.deleteOrder(orderId);
        if (message == ResultMessage.SUCCESS){
            MonthlyBill monthlyBill = usageService.getMonthlyBill(orderId, LocalDate.now());
            List<PackOffer> offers = packService.getPackOfferByPack(packService.getPack(order.getPid()));
            double call = 0;
            double mes = 0;
            double local = 0;
            double domestic = 0;
            for (PackOffer offer : offers) {
                switch (UsageType.values()[offer.getOfferType()]){
                    case CALL_USAGE:call+=offer.getQuantity();break;
                    case MES_USAGE:mes+=offer.getQuantity();break;
                    case LOCAL_TRAFFIC:local+=offer.getQuantity();break;
                    case DOMESTIC_TRAFFIC:domestic+=offer.getQuantity();break;
                }
            }
            double extraCost = min(call, monthlyBill.getCallSum()) * CallUsage.getPrice()
                    + min(mes, monthlyBill.getMesSum()) * MesUsage.getPrice()
                    + min(local, monthlyBill.getLocalTraffic()) * LocalTraffic.getPrice()
                    + min(domestic, monthlyBill.getDomesticTraffic()) * DomesticTraffic.getPrice();
            userService.spendMoney(order.getUid(), extraCost);

            userService.refundMoney(order.getUid(), packService.getPack(order.getPid()).getFee());
        }
        return message;
    }

    @Override
    public ResultMessage cancelOrderImmediately(long uid, long pid) {
        List<Order> orders = orderDAO.getOrderByUser(uid);
        orders.removeIf(order -> order.getPid()!=pid || !isSameMonth(order.getMonth(), LocalDate.now()));
        if (orders.isEmpty()){
            return ResultMessage.FAILURE;
        }

        ResultMessage message = ResultMessage.SUCCESS;
        for (Order order : orders) {
            if (cancelOrderImmediately(order.getOrderId()) == ResultMessage.FAILURE){
                message = ResultMessage.FAILURE;
            }
        }
        return message;
    }

    @Override
    public ResultMessage cancelOrderNextMonth(long orderId) {
        Order order = orderDAO.getOrder(orderId);
        if (order == null){
            return ResultMessage.FAILURE;
        }

        order.setRenewing(false);
        return orderDAO.updateOrder(order);
    }

    @Override
    public ResultMessage cancelOrderNextMonth(long uid, long pid) {
        List<Order> orders = orderDAO.getOrderByUser(uid);
        orders.removeIf(order -> order.getPid()!=pid || !isSameMonth(order.getMonth(), LocalDate.now()));
        if (orders.isEmpty()){
            return ResultMessage.FAILURE;
        }

        ResultMessage message = ResultMessage.SUCCESS;
        for (Order order : orders) {
            if (cancelOrderNextMonth(order.getOrderId()) == ResultMessage.FAILURE){
                message = ResultMessage.FAILURE;
            }
        }
        return message;
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
        List<Order> orders = orderDAO.getOrderByUser(uid);
        orders.removeIf(order -> !isSameMonth(date, order.getMonth()));

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
}
