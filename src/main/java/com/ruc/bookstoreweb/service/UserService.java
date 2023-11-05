package com.ruc.bookstoreweb.service;

import com.ruc.bookstoreweb.pojo.User;

/**
 * @Author 3590
 * @Date 2023/11/5 23:42
 * @Description 业务层，一个业务一个方法。登录/注册/检查用户名是否存在
 * @Version
 */
public interface UserService {
    /**
     * 实现用户的注册业务
     * */
    public void registUser(User user);

    /**
     * 实现用户的登录，如果可以登录，就返回user
     * */
    public User login(User user);

    /**
     * 检查用户名是否可用
     * @return true：用户名可用，还没有人使用这个用户名 false：用户名已经被它人注册了
     * */
    public boolean checkExistUsername(String username);
}
