package com.ruc.bookstoreweb;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * 此文件用于步骤指引：
 * 1.先创建书城需要的数据库和表(已经在MySQL终端中创建完成了)
 * 2.编写数据库表对应的javaBean对象
 * 3.编写Dao持久层 需要编写工具类JdbcUtils，主要用来管理数据库连接
 * 4.编写BaseDao
 * */
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}