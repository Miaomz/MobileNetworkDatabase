package org.casual.dao.user;

import org.casual.dao.datautil.Templar;
import org.casual.entity.User;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/23
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public ResultMessage addUser(User user) {
        return Templar.update("INSERT INTO USER(uname, balance) VALUES(?, ?)", user.getUname(), user.getBalance());
    }

    @Override
    public ResultMessage deleteUser(long uid) {
        return Templar.update("DELETE FROM USER WHERE uid = ?", uid);
    }

    @Override
    public ResultMessage updateUser(User user) {
        return Templar.update("UPDATE USER SET uname = ?, balance = ? WHERE uid = ?", user.getUname(), user.getBalance(), user.getUid());
    }

    @Override
    public User getUser(long uid) {
        return (User)Templar.getOne("SELECT * FROM USER WHERE uid = ?", User.class, uid);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUserList() {
        return Templar.getList("SELECT * FROM USER", User.class);
    }
}
