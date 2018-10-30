package org.casual.dao.user;

import org.casual.entity.User;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/23
 */
public interface UserDAO {

    ResultMessage addUser(User user);

    ResultMessage deleteUser(long uid);

    ResultMessage updateUser(User user);

    User getUser(long uid);

    List<User> getUserList();
}
