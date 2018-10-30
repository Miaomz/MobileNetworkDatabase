package org.casual.service.usage;

import org.casual.dao.datautil.DaoFactory;
import org.casual.dao.usage.UsageDAO;
import org.casual.entity.*;
import org.casual.service.order.OrderService;
import org.casual.service.pack.PackService;
import org.casual.service.user.UserService;
import org.casual.util.ResultMessage;
import org.casual.util.UsageType;
import org.casual.vo.MonthlyBill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.casual.util.DateTimeUtil.isSameMonth;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class UsageServiceImpl implements UsageService {

    private UsageDAO usageDAO = DaoFactory.getInstance().getUsageDAO();

    private OrderService orderService;
    private UserService userService;
    private PackService packService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPackService(PackService packService) {
        this.packService = packService;
    }

    @Override
    public ResultMessage addCallUsage(long uid, double callTime) {
        double currentSum = usageDAO.currentCallUsage(uid);
        double expense = calcExpense(uid, currentSum, callTime, UsageType.CALL_USAGE, CallUsage.getPrice());
        userService.spendMoney(uid, expense);

        CallUsage callUsage = new CallUsage(0, uid, LocalDateTime.now(), callTime);
        System.out.println("通话花费" + expense + "元");
        return usageDAO.addCallUsage(callUsage);
    }

    @Override
    public ResultMessage addMesUsage(long uid) {
        double expense = calcExpense(uid, usageDAO.currentMesUsage(uid), 1, UsageType.MES_USAGE, MesUsage.getPrice());
        userService.spendMoney(uid, expense);
        System.out.println("短信花费" + expense + "元");
        return usageDAO.addMesUsage(new MesUsage(0, uid, LocalDateTime.now()));
    }

    @Override
    public ResultMessage addLocalTraffic(long uid, double traffic) {
        double expense = calcExpense(uid, usageDAO.currentLocalTraffic(uid), traffic, UsageType.LOCAL_TRAFFIC, LocalTraffic.getPrice());
        userService.spendMoney(uid, expense);
        System.out.println("流量花费" + expense + "元");
        return usageDAO.addLocalTraffic(new LocalTraffic(0, uid, LocalDateTime.now(), traffic));
    }

    @Override
    public ResultMessage addDomesticTraffic(long uid, double traffic) {
        double expense = calcExpense(uid, usageDAO.currentDomesticTraffic(uid), traffic, UsageType.DOMESTIC_TRAFFIC, DomesticTraffic.getPrice());
        userService.spendMoney(uid, expense);
        System.out.println("流量花费" + expense + "元");
        return usageDAO.addDomesticTraffic(new DomesticTraffic(0, uid, LocalDateTime.now(), traffic));
    }

    @Override
    public MonthlyBill getMonthlyBill(long uid, LocalDate date) {
        //raw data
        List<CallUsage> callUsages = usageDAO.getCallUsageList();
        List<MesUsage> mesUsages = usageDAO.getMesUsageList();
        List<LocalTraffic> localTraffics = usageDAO.getLocalTrafficList();
        List<DomesticTraffic> domesticTraffics = usageDAO.getDomesticTrafficList();
        callUsages.removeIf(callUsage -> callUsage.getUid()!=uid || !isSameMonth(date, callUsage.getFinishDatetime().toLocalDate()));
        mesUsages.removeIf(mesUsage -> mesUsage.getUid()!=uid || !isSameMonth(date, mesUsage.getFinishDatetime().toLocalDate()));
        localTraffics.removeIf(localTraffic -> localTraffic.getUid()!=uid || !isSameMonth(date, localTraffic.getFinishDatetime().toLocalDate()));
        domesticTraffics.removeIf(domesticTraffic -> domesticTraffic.getUid()!=uid || !isSameMonth(date, domesticTraffic.getFinishDatetime().toLocalDate()));

        //calculate the usage
        double callSum = 0;
        int mesSum = mesUsages.size();
        double localTraffic = 0;
        double domeTraffic = 0;
        for (CallUsage callUsage : callUsages) {
            callSum += callUsage.getCallTime();
        }
        for (LocalTraffic traffic : localTraffics) {
            localTraffic += traffic.getTraffic();
        }
        for (DomesticTraffic domesticTraffic : domesticTraffics) {
            domeTraffic += domesticTraffic.getTraffic();
        }

        //calculate the cost
        List<Order> orders = orderService.queryOrders(uid);
        orders.removeIf(order -> !isSameMonth(order.getMonth(), date));
        List<PackOffer> offers = new ArrayList<>();
        for (Order order : orders) {
            offers.addAll(packService.getPackOfferByPack(packService.getPack(order.getPid())));
        }
        double callQuota = 0;
        double mesQuota = 0;
        double localQuota = 0;
        double domeQuota = 0;
        for (PackOffer offer : offers) {
            switch (UsageType.values()[offer.getOfferType()]){
                case CALL_USAGE:
                    callQuota += offer.getQuantity();
                    break;
                case MES_USAGE:
                    mesQuota += offer.getQuantity();
                    break;
                case LOCAL_TRAFFIC:
                    localQuota += offer.getQuantity();
                    break;
                case DOMESTIC_TRAFFIC:
                    domeQuota += offer.getQuantity();
                    break;
            }
        }
        double cost = calcExpense(callQuota, callSum, CallUsage.getPrice())
                + calcExpense(mesQuota, mesSum, MesUsage.getPrice())
                + calcExpense(localQuota, localTraffic, LocalTraffic.getPrice())
                + calcExpense(domeQuota, domeTraffic, DomesticTraffic.getPrice());
        return new MonthlyBill(date.withDayOfMonth(1), cost, callSum, mesSum, localTraffic, domeTraffic);
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

    private double calcExpense(double quantity, double sum, double price){
        if (quantity >= sum){
            return 0;
        } else {
            return (sum-quantity) * price;
        }
    }
}
