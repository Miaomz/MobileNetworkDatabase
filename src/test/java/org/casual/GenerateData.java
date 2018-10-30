package org.casual;

import org.casual.entity.Pack;
import org.casual.entity.User;
import org.casual.service.order.OrderService;
import org.casual.service.usage.UsageService;
import org.casual.service.user.UserService;
import org.casual.service.util.ServiceFactory;
import org.casual.util.ResultMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class GenerateData {
    private static final int USER_NUM = 100;
    private static final int USAGE_NUM = 10000;

    private static final String stringSrc = "hogjk2nafgkslvk23nsfguoij2n3fjekfifijeownjv423oeiwjlnelnvsdvnsvknvsavs3qieqiob654354vxzvm";

    @Before
    public void setup(){
        Main.main(new String[0]);
    }

    @Test
    public void generate(){
        UserService userService = ServiceFactory.getInstance().getUserService();
        for (int i = 0; i < USER_NUM; i++) {
            int beginIndex = new Random().nextInt(stringSrc.length()-2);
            userService.addUser(new User(0, stringSrc.substring(beginIndex, beginIndex+2), 10000+beginIndex*100, false));
        }

        List<Pack> packs = ServiceFactory.getInstance().getPackService().getPackList();
        OrderService orderService = ServiceFactory.getInstance().getOrderService();
        for (int i = 0; i < USER_NUM*4; i++) {
            orderService.makeOrder(i/4+1, packs.get((int)(Math.random()*4)).getPid());
        }

        UsageService usageService = ServiceFactory.getInstance().getUsageService();
        for (int i=0; i < USAGE_NUM; i++){
            long uid = (long)(USER_NUM*Math.random()+1);
            if (usageService.addCallUsage(uid, Math.random()*5)== ResultMessage.FAILURE){ System.out.println(uid);}
            usageService.addMesUsage((long)(USER_NUM*Math.random())+1);
            usageService.addLocalTraffic((long)(USER_NUM*Math.random())+1, Math.random()*20);
            usageService.addDomesticTraffic((long)(USER_NUM*Math.random())+1, Math.random()*20);
        }
    }
}
