package com.ruc.bookstoreweb.dao;

import com.ruc.bookstoreweb.pojo.User;
import com.ruc.bookstoreweb.utils.JdbcUtils;

import java.sql.Connection;
import java.util.jar.JarEntry;

/**
 * @Author 3590
 * @Date 2023/11/5 23:15
 * @Description
 * @Version
 */
public interface UserDao {

    /**
     * @param username 用户的用户名
     * @return 返回 User 类型的 JavaBean ，如果没有查到，返回 null
     * */
    public User queryUserByUsername(String username);

    /**
     * 根据用户名和密码查询用户，如果没有该用户，则返回的是 null，说明用户名和密码错误
     * @param username 用户的用户名
     * @param password 用户输入的密码
     * */
    public User verifyLogin(String username, String password);

    /**
     * 保存用户信息
     * @param user user 的 JavaBean
     * @return 返回影响的行数，如果失败，则返回-1
     * */
    public int saveUser(User user);



}
