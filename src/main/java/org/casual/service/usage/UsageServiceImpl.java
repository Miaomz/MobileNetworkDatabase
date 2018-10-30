package org.casual.service.usage;

import org.casual.dao.datautil.DaoFactory;
import org.casual.dao.usage.UsageDAO;
import org.casual.entity.CallUsage;
import org.casual.entity.DomesticTraffic;
import org.casual.entity.LocalTraffic;
import org.casual.entity.MesUsage;
import org.casual.service.order.OrderService;
import org.casual.service.user.UserService;
import org.casual.util.ResultMessage;
import org.casual.util.UsageType;
import org.casual.vo.MonthlyBill;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class UsageServiceImpl implements UsageService {

    private UsageDAO usageDAO = DaoFactory.getInstance().getUsageDAO();

    private OrderService orderService;
    private UserService userService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResultMessage addCallUsage(long uid, double callTime) {
        double currentSum = usageDAO.currentCallUsage(uid);
        double expense = calcExpense(uid, currentSum, callTime, UsageType.CALL_USAGE, CallUsage.getPrice());
        userService.spendMoney(uid, expense);

        CallUsage callUsage = new CallUsage(0, uid, LocalDateTime.now(), callTime);
        return usageDAO.addCallUsage(callUsage);
    }

    @Override
    public ResultMessage addMesUsage(long uid) {
        userService.spendMoney(uid,
                calcExpense(uid, usageDAO.currentMesUsage(uid), 1, UsageType.MES_USAGE, MesUsage.getPrice()));
        return usageDAO.addMesUsage(new MesUsage(0, uid, LocalDateTime.now()));
    }

    @Override
    public ResultMessage addLocalTraffic(long uid, double traffic) {
        userService.spendMoney(uid,
                calcExpense(uid, usageDAO.currentLocalTraffic(uid), traffic, UsageType.LOCAL_TRAFFIC, LocalTraffic.getPrice()));
        return usageDAO.addLocalTraffic(new LocalTraffic(0, uid, LocalDateTime.now(), traffic));
    }

    @Override
    public ResultMessage addDomesticTraffic(long uid, double traffic) {
        userService.spendMoney(uid,
                calcExpense(uid, usageDAO.currentDomesticTraffic(uid), traffic, UsageType.DOMESTIC_TRAFFIC, DomesticTraffic.getPrice()));
        return usageDAO.addDomesticTraffic(new DomesticTraffic(0, uid, LocalDateTime.now(), traffic));
    }

    @Override
    public MonthlyBill getMonthlyBill(long uid, LocalDate date) {
        return null;
    }

    private double calcExpense(long uid, double currentSum, double quantity, UsageType type, double price){
        double freeQuantity = orderService.getFreeQuantity(uid, type, LocalDate.now());
        if (currentSum >= freeQuantity){
            return quantity * price;
        } else if (currentSum+quantity > freeQuantity){
            return (quantity+currentSum-freeQuantity) * price;
        } else {//all free
            return 0;
        }
    }
}
