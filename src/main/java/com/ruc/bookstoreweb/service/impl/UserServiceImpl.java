package com.ruc.bookstoreweb.service.impl;

import com.ruc.bookstoreweb.dao.UserDao;
import com.ruc.bookstoreweb.dao.impl.UserDaoImpl;
import com.ruc.bookstoreweb.pojo.User;
import com.ruc.bookstoreweb.service.UserService;

/**
 * @Author 3590
 * @Date 2023/11/5 23:46
 * @Description
 * @Version
 */
public class UserServiceImpl implements UserService {
    // 需要用 Dao 操作数据库
    UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.verifyLogin(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.queryUserByUsername(username) == null;
    }
}
