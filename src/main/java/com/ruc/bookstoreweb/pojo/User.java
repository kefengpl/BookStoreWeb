package com.ruc.bookstoreweb.pojo;

/**
 * 这是一个 JavaBean
 * 也就是把数据表读入的数据结构(一行数据)是 JavaBean
 * JavaBean：私有成员 + set方法 + get方法 + toString + 有参构造器 + 无参构造器
 * */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    // 生成get/set的快捷键：alt + insert

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User() {}

    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
