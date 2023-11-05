package com.ruc.bookstoreweb.web;
/**
 * @Author 3590
 * @Date 2023/11/6 0:01
 * @Description WEB层，用于处理用户的注册
 * @Version
 */

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
        String verifyCode = request.getParameter("code"); // 获取输入的验证码

        // 2. 检查验证码是否正确？ 写死，要求验证码为abcde
        if (verifyCode.equalsIgnoreCase("abcde")) {
            // 3.检查用户名是否可用
            System.out.println("验证码输入正确！");
        } else {
            System.out.println("验证码[" + verifyCode +"]错误");
            // 若不正确，则跳回注册页面(也就是请求转发)
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/regist.html");
            dispatcher.forward(request, response);
        }
    }
}