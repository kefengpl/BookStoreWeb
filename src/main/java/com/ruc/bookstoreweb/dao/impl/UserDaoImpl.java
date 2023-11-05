package com.ruc.bookstoreweb.dao.impl;

import com.ruc.bookstoreweb.dao.BaseDao;
import com.ruc.bookstoreweb.dao.UserDao;
import com.ruc.bookstoreweb.pojo.User;

/**
 * @Author 3590
 * @Date 2023/11/5 23:20
 * @Description UserDao的实现类
 * @Version
 */
public class UserDaoImpl extends BaseDao implements UserDao {
    // 快速创建测试 : Ctrl + Shift + T
    @Override
    public User queryUserByUsername(String username) {
        String sql = "select * from t_user where username = ?";
        return queryForOne(User.class, sql, username);
    }

    @Override
    public User verifyLogin(String username, String password) {
        String sql = "select * from t_user where username = ? and password = ?";
        return queryForOne(User.class, sql, username, password);
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(username, password, email) values(?, ?, ?)";
        return update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}
