package org.casual.dao.usage;

import org.casual.Main;
import org.casual.entity.CallUsage;
import org.casual.entity.MesUsage;
import org.casual.util.ResultMessage;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class UsageDAOImplTest {

    private UsageDAO usageDAO;

    @Before
    public void setUp() throws Exception {
        Main.main(new String[0]);
        usageDAO = new UsageDAOImpl();
    }

    @Test
    public void addCallUsage() {
        assertEquals(ResultMessage.SUCCESS, usageDAO.addCallUsage(new CallUsage(0, 1, LocalDateTime.now(), 10)));
    }

    @Test
    public void addMesUsage() {
        assertEquals(ResultMessage.SUCCESS, usageDAO.addMesUsage(new MesUsage(0, 1, LocalDateTime.now())));
    }

    @Test
    public void addLocalTraffic() {
    }

    @Test
    public void addDomesticTraffic() {
    }

    @Test
    public void getCallUsage() {
    }

    @Test
    public void getMesUsage() {
    }

    @Test
    public void getLocalTraffic() {
    }

    @Test
    public void getDomesticTraffic() {
    }

    @Test
    public void getCallUsageList() {
    }

    @Test
    public void getMesUsageList() {
    }

    @Test
    public void getLocalTrafficList() {
    }

    @Test
    public void getDomesticTrafficList() {
    }

    @Test
    public void currentCallUsage() {
        System.out.println(usageDAO.currentCallUsage(1));
    }

    @Test
    public void currentMesUsage() {
        System.out.println(usageDAO.currentMesUsage(1));
    }

    @Test
    public void currentLocalTraffic() {
    }

    @Test
    public void currentDomesticTraffic() {
    }
}