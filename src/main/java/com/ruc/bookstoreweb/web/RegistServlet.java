package com.ruc.bookstoreweb.web;
/**
 * @Author 3590
 * @Date 2023/11/6 0:01
 * @Description WEB层，用于处理用户的注册
 * @Version
 */

import com.ruc.bookstoreweb.dao.UserDao;
import com.ruc.bookstoreweb.dao.impl.UserDaoImpl;
import com.ruc.bookstoreweb.pojo.User;
import com.ruc.bookstoreweb.service.UserService;
import com.ruc.bookstoreweb.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 注意：页面如果没有写是 FORM的Method是POST，则默认是 GET 方法
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 首先获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        // 注意：为了获取输入的验证码，需要在验证码标签中加入 name="code"
        // 注意：为了防止页面在重新部署服务器后没有更新，应该选择[STOP CACHE][停用快取]选项
        String verifyCode = request.getParameter("code"); // 获取输入的验证码

        // 2. 检查验证码是否正确？ 写死，要求验证码为abcde
        if (verifyCode.equalsIgnoreCase("abcde")) {
            // 3.检查用户名是否可用，调用服务层进行检查
            UserServiceImpl userService = new UserServiceImpl();
            // 检查用户名是否可用，true 代表用户名可用，此时注册该用户
            if (userService.checkExistUsername(username)) {
                userService.registUser(new User(null, username, password, email));
                // 此时跳转到注册成功页面
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/regist_success.jsp");
                dispatcher.forward(request, response);
            } else {
                // 若不正确，则跳回注册页面(也就是请求转发)
                request.setAttribute("errorMsg", "用户名 [" + username + "] 已经存在");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/regist.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            System.out.println("验证码[" + verifyCode +"]错误");
            // 若不正确，则跳回注册页面(也就是请求转发)
            request.setAttribute("errorMsg", "验证码[" + verifyCode +"]错误");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/regist.jsp");
            dispatcher.forward(request, response);
        }
    }
}