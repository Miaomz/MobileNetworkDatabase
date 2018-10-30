package org.casual.service.util;

import lombok.Getter;
import org.casual.service.order.OrderService;
import org.casual.service.order.OrderServiceImpl;
import org.casual.service.pack.PackService;
import org.casual.service.pack.PackServiceImpl;
import org.casual.service.usage.UsageService;
import org.casual.service.usage.UsageServiceImpl;
import org.casual.service.user.UserService;
import org.casual.service.user.UserServiceImpl;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
@Getter
public class ServiceFactory {

    private OrderService orderService = new OrderServiceImpl();

    private UserService userService = new UserServiceImpl();

    private PackService packService = new PackServiceImpl();

    private UsageService usageService = new UsageServiceImpl();

    private static ServiceFactory serviceFactory = new ServiceFactory();

    private ServiceFactory() {
        OrderServiceImpl orderImpl = (OrderServiceImpl)orderService;
        orderImpl.setPackService(packService);
        orderImpl.setUserService(userService);
        orderImpl.setUsageService(usageService);

        UsageServiceImpl usageImpl = (UsageServiceImpl)usageService;
        usageImpl.setOrderService(orderService);
        usageImpl.setUserService(userService);
        usageImpl.setPackService(packService);
    }

    public static ServiceFactory getInstance(){
        return serviceFactory;
    }
}
