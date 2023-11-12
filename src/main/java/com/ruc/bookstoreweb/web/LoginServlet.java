package com.ruc.bookstoreweb.web;
/**
 * @Author 3590
 * @Date 2023/11/6 10:15
 * @Description 实现用户登录功能
 * @Version
 */

import com.ruc.bookstoreweb.pojo.User;
import com.ruc.bookstoreweb.service.UserService;
import com.ruc.bookstoreweb.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 2. 调用 service 层处理业务
        UserService userService = new UserServiceImpl();
        if (userService.login(new User(null, username, password, null)) != null) {
            System.out.println("尊敬的" + username + "，您已经登录成功！");
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request, response);
        } else {
            System.out.println("登入失败，用户名或者密码错误！");
            // 登录失败，需要将原来的表单内容填写回去，保存到 request 域中(所谓 “请求转发”)，但是它们本来就在 request域中...
            // 我们填入错误信息
            request.setAttribute("errorMsg", "用户名或者密码输入错误！");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        }
    }
}