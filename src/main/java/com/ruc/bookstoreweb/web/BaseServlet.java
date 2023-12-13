package com.ruc.bookstoreweb.web;

import com.ruc.bookstoreweb.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author 3590
 * @Date 2023/11/12 19:17
 * @Description
 * @Version
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解决中文乱码问题
        request.setCharacterEncoding("UTF-8");
        // 1. 获取究竟是 注册行为 还是 登录行为？
        String action = request.getParameter("action");
        // request.setAttribute("test", "51658168165161631631");
        // 注意：请求转发，往回传给客户端的时候还会经过这个 UserServlet 程序！
        // 所以执行顺序是：handleRegist ---> regist.jsp(先输出errorMsg)(forward函数) ---> 执行下面的 sout
        // 也就是说，forward函数执行(显示jsp页面)完毕后才会调用forward后面的函数.

        /************************************************/
        // 迭代优化：我们如何通过反射来动态调用 函数 ？ 以便于少写 if else ?
        // 使用 this 指针来指代这个对象即可！
        Class clazz = this.getClass();
        Method handleMethod = null;
        try {
            // 错误：由于重载，函数有参数的时候需要把参数类型也写入！
            //  HttpServletRequest.class, HttpServletResponse.class 这两个参数不要忘记！
            handleMethod = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        handleMethod.setAccessible(true);
        try {
            // 动态调用函数进行处理
            // 注意：这个 this 指针如果子类调用，将指向子类
            handleMethod.invoke(this, request, response);
        } catch (Exception e) {
            // 把异常抛给 Filter过滤器
            throw new RuntimeException(e);
        }

        System.out.println("BaseServlet这个程序执行完毕，已将请求对外转发");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        System.out.println("BaseServlet这个GET程序执行完毕，已将请求对外转发");
    }
}
