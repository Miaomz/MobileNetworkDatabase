package org.casual.dao.util;

import lombok.Getter;
import org.casual.dao.order.OrderDAO;
import org.casual.dao.order.OrderDAOImpl;
import org.casual.dao.pack.PackDAO;
import org.casual.dao.pack.PackDAOImpl;
import org.casual.dao.usage.UsageDAO;
import org.casual.dao.usage.UsageDAOImpl;
import org.casual.dao.user.UserDAO;
import org.casual.dao.user.UserDAOImpl;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
@Getter
public class DaoFactory {

    private UserDAO userDAO = new UserDAOImpl();

    private OrderDAO orderDAO = new OrderDAOImpl();

    private UsageDAO usageDAO = new UsageDAOImpl();

    private PackDAO packDAO = new PackDAOImpl();

    private static DaoFactory ourInstance = new DaoFactory();

    public static DaoFactory getInstance() {
        return ourInstance;
    }

    private DaoFactory() {}
}
