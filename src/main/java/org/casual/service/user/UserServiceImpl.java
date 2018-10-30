package org.casual.service.user;

import org.casual.dao.user.UserDAO;
import org.casual.dao.user.UserDAOImpl;
import org.casual.entity.User;
import org.casual.util.ResultMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public ResultMessage addUser(User user) {
        return userDAO.addUser(user);
    }

    @Override
    public ResultMessage deleteUser(long uid) {
        return userDAO.deleteUser(uid);
    }

    @Override
    public ResultMessage updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public User getUser(long uid) {
        return userDAO.getUser(uid);
    }

    @Override
    public List<User> getUserList() {
        return userDAO.getUserList();
    }

    @Override
    public ResultMessage spendMoney(long uid, double money) {
        User user = getUser(uid);
        user.setBalance(user.getBalance() - money);
        return updateUser(user);
    }

    @Override
    public ResultMessage refundMoney(long uid, double money) {
        User user = getUser(uid);
        user.setBalance(user.getBalance() + money);
        return updateUser(user);
    }

    @Override
    public List<User> frozeUser() {
        List<User> frozenUsers = new LinkedList<>();

        List<User> userList = userDAO.getUserList();
        for (User user : userList) {
            if (user.getBalance() >= 0 && user.isFrozen()){
                user.setFrozen(false);
            } else if (user.getBalance() < 0 && user.isFrozen()){
                user.setFrozen(true);
                frozenUsers.add(user);
            }
        }
        return frozenUsers;
    }
}
