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
 *   则引用的其它资源仍然是 localhost 127.0.0.1 ，这种情况下，部署项目会直接出错，这就是在 common/head.jsp 中我们为何要动态获取 ip 的原因
 * c. 表单提交失败的错误回显
 *   原理讲解：客户端(浏览器) --- 服务器(Tomcat)
 *   登录的时候请求从浏览器发起，登录或者注册
 *   只要登录失败，就会跳回原来的页面，回显信息需要保存在 request 域中
 *   1. 客户端需要回显错误信息
 *   2. 还需要回显原来用户输入框的内容[把回显的信息带回客户端，给 jsp 页面]
 *   注意区分 getAttribute 和 getParameter
 * d. 在一个项目中，一个 模块一般只使用一个 servlet 程序，比如 login & regist 应该位于一个 servlet 程序中
 *   login.jsp 和 regist.jsp 都把请求发给 UserServlet 程序，两个都是 POST 请求
 *   可以在 login.jsp 添加隐藏域，<input type="hidden" name="action" value="login">表示登录功能
 *   可以在 regist.jsp 添加隐藏域，<input type="hidden" name="action" value="regist">表示注册功能
 *   最后在 servlet 程序中的 doPost 方法中进行分发处理。
 *   用户模块的功能，除了登录和注册，还可能有其它模块，比如添加用户、修改用户信息、修改密码、绑定手机等，每个
 *   功能都有隐藏域，然后使用 else if 进行处理，如果不使用 if else 是不是会很好呢？这就需要用到反射了！
 * e. 代码需要进一步优化
 *   例如：我有 UserServlet 程序，还有 BookServlet 程序
 *   在 UserServlet 程序中，需要做的事情如下：
 *   ① 获取 action 参数值
 *   ② 通过反射获取 action 对应的业务方法
 *   ③ 通过反射调用业务方法
 *   我会发现在 BookServlet 程序中，我也需要这些方法
 *   因此，我们需要进一步抽象：BaseServlet 程序，UserServlet BookServlet 继承 BookServlet即可！
 *
 *   BeanUtils 工具类的使用：
 *   它可以一次性把所有请求的参数注入到 JavaBean 中
 *   下面的代码过于复杂，如果表单包含 30 个字段，那将无法处理，因此需要用 BeanUtils 进行简化
 *         String username = request.getParameter("username");
 *         String password = request.getParameter("password");
 *         String email = request.getParameter("email");
 *    BeanUtils 不是 JDK 类，而是第三方工具类，所以需要导包
 *    本质分析：
 *    BeanUtils注入的核心是 对于某个变量名，例如 age
 *    通过反射调用 setAge 即可完成注入，这是 java 为什么要求命名规范的根本原因 [约定大于配置]
 *  知识补充：Java 明明可以使用 Object 实现泛型， 为什么还要引入 T 泛型？
 *  答：有助于提高代码健壮性，减少运行时类型转换异常，在编译阶段就实现泛型，使得问题都出现于编译时异常
 *
 *  ======================================
 *  书城项目：第五阶段：图书模块
 *  ****MVC可以理解为 在原来 Web / service / DAO 的基础上，对其中的 Web 进一步分为 Model View Controller
 *  MVC 概念讲解： Model 模型， View 试图， Controller 控制器
 *  它和我们之前提到的  Web / service / DAO 架构联系在于：
 *  比如一个应用的架构是这样的 view control service dao model(pojo/JavaBean) ，那么 view control 就是 servlet程序和 jsp页面(Web)
 *  Service 层依旧是你 Service 层，dao + model 可以理解为 dao 层
 *  其理念是将软件代码拆分为组件，单独开发，组合使用，为了解耦合
 *  MVC 最早出现于 JavaEE 的 Web 层，它可以有效直到 web 层代码如何有效分离，单独工作
 *  View 视图：仅负责数据界面显示，不接收任何与数据无关的代码
 *  Controller 控制器：只负责接收请求，调用业务层代码处理请求，然后派发页面(请求转发/重定向)，一般是 Servlet
 *  Model 模型：将与业务逻辑相关的数据封装为具体的 JavaBean，其中不参杂任何与数据处理相关的代码，一般是 JavaBean/domain/entity/pojo
 *
 *  再论 DAO 和 Service：
 *  dao 相当于每个 bean 的工具类，而 service 是调用 dao 完成各种操作的，dao 只能对其指定的的 bean 完成操作，
 *  而有时业务需求会需要使用多个 dao，如果 dao 中调用 dao，耦合性高，可读性差
 *
 *  前台和后台的概念
 *  问题：为什么 BookServlet 的路径要使用 /manager/book 而不是 /book
 *  前台：是给普通用户使用的，不需要权限检查，就可以访问的资源，比如：淘宝不登录就可以访问的商品首页
 *  (包含商品浏览)
 *  后台：是给管理员使用的，一般都需要权限检查才可以访问到的资源或者页面或者功能，比如审批系统后台的审批权限
 *  于是，在设计时可以这样： /client/bookServlet /manager/bookServlet 分别表示普通用户和管理层
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