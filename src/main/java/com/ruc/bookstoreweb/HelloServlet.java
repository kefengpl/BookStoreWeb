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
 *
 * 书城项目第三阶段
 * a. 页面 jsp 动态化
 *   1. 在 html 页面顶行添加 page 指令
 *   2. 修改文件后缀名为 .jsp
 *   3. 修改其它文件的相对引用，这里在 IDEA 的 Refactor 中已经做得很好了
 * b. 抽取页面中相同的内容：复用代码！
 *   细节问题：如果我们把访问页面ip从127.0.0.1换为 10.47.197.19(本机ip)
 *   则引用的其它资源仍然是 localhost 127.0.0.1 ，这种情况下，部署项目会直接出错
 * c. 表单提交失败的错误回显
 *   原理讲解：客户端(浏览器) --- 服务器(Tomcat)
 *   登录的时候请求从浏览器发起，登录或者注册
 *   只要登录失败，就会跳回原来的页面，回显信息需要保存在 request 域中
 *   1. 客户端需要回显错误信息
 *   2. 还需要回显原来用户输入框的内容[把回显的信息带回客户端，给 jsp 页面]
 *   注意区分 getAttribute 和 getParameter
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