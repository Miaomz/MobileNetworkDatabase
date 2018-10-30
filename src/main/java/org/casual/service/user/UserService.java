package org.casual.service.user;

import org.casual.entity.User;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public interface UserService {

    ResultMessage addUser(User user);

    ResultMessage deleteUser(long uid);

    ResultMessage updateUser(User user);

    User getUser(long uid);

    List<User> getUserList();

    ResultMessage spendMoney(long uid, double money);

    ResultMessage refundMoney(long uid, double money);

    /**
     * froze the users who have debt
     * it should only be called at the beginning of one month
     * @return the user frozen
     */
    List<User> frozeUser();
}
