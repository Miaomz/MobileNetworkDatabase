package org.casual.dao.user;

import org.casual.Main;
import org.casual.entity.User;
import org.junit.Before;
import org.junit.Test;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
public class UserDAOImplTest {

    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        Main.main(new String[0]);
        userDAO = new UserDAOImpl();
    }

    @Test
    public void addUser() {
        userDAO.addUser(new User(0, "second", 1200, false));
    }

    @Test
    public void deleteUser() {
        userDAO.deleteUser(0);
    }

    @Test
    public void updateUser() {
        userDAO.updateUser(new User(1, "first", 900, false));
    }

    @Test
    public void getUser() {
        User u = userDAO.getUser(1);
    }

    @Test
    public void getUserList() {
        userDAO.getUserList().forEach(user -> System.out.println(user.getUid() + " " + user.getUname() + " " + user.getBalance()));
    }
}
