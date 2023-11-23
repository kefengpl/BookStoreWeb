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
 *
 *  ================================
 *  书城项目：第六阶段 A
 *  3.2 登出——注销用户，你需要怎么做？
 *  ① 销毁 session 中用户登录的信息 (或者销毁 session)
 *  ② 重定向到首页
 *  3.3 表单重复提交之——验证码
 *  表单重复提交的三种常见情况：
 *      一、提交完表单，服务器使用请求进行页面跳转，这时候，用户一直刷新，就会一直请求，这时需要使用[重定向]
 *      二、用户正常提交服务器，但是由于网络延迟等原因，迟迟未收到服务器响应，然后用户狂按提交...也会造成表单重复提交
 *      三、用户正常提交，服务器也没有延迟，但是提交完成后，用户回退浏览器重新提交
 *  后两种情况就需要引入 [验证码] 了！
 *  服务器端在 regist.jsp 加入验证码，①当用户第一次访问表单的时候，就需要给表单生成一个
 *  随机的验证码字符串 ②要把验证码保存到 session 域中 ③要把验证码生成为验证码图片显示在表单中
 *  用户输入用户名，输入验证码，点击提交图表，进入 RegistServlet程序：① 获取 session 中的验证码，并
 *  删除 session 中的验证码(先获取，后删除)，② 获取表单中的表单项信息，③比较 session中的验证码和表单项中的验证码是否相等。
 *  这时候：如果用户发来 abcde，匹配 session 的 abcde，于是允许操作。如果用户着急又点了好几次 提交，那么 此时由于 session
 *  中的 验证码项被删除了，即为 null， 则 null 无法匹配用户提交的 abcde，阻止用户重复提交，则问题二、三可被解决。
 *
 *  使用 GOOGLE 提供的验证码方案：
 *      1. 导入jar包，Kaptcha
 *      2. 在 web.xml 中配置用于生成验证码的 servlet 程序
 *      3. 在表单中使用 img 标签显示验证码图片，并使用之
 *      4. 在服务器获取谷歌生成的验证码和客户端发送过来的验证码比较使用
 *  知识补充：什么是缓存？浏览器为了让请求速度更快，就把每次请求的内容缓存到了浏览器
 *  端，(磁盘上或者内存中)。浏览器每次发请求../verifycode.jpg，Google的验证码工具
 *  就会生成验证码，返回验证码图片。浏览器会生成 verifycode.jpg=返回的图片内容，下次浏览器再
 *  发送请求，浏览器会先直接从缓存中找(直接来自本地)。
 *
 *  问题：如何跳过浏览器的缓存而发起请求给服务器？
 *  缓存的名称由最后的资源名和参数组成。只要再请求上面加一个参数，比如每次都不同的值[时间戳]，此时浏览器就会跳过缓存
 *  例如 /verifycode.jpg 会在本地缓存中查找，而 /verifycode.jpg?a=1681321 则会跳过缓存
 *  (更多信息请浏览) regist.jsp
 *  ================================
 *  书城项目：第六阶段B 购物车，实现 Session 版本：提示：不需要链接数据库，不需要DAO层
 *  ================================
 *  书城项目：第八阶段：引入 ThreadLocal，它可以解决多线程数据安全问题
 *  ThreadLocal 可以给当前线程关联一个数据(可以是普通变量，可以是对象，也可以是数组，集合)
 *  ThreadLocal 特点：
 *      1. ThreadLocal 可以为当前线程关联一个数据，即 它可以像 Map 一样存取数据，key 是当期线程
 *      2. 每一个 ThreadLocal 对象只能为当前线程关联一个数据，如果要为当前线程关联多个数据，就需要使用多个 ThreadLocal 实例
 *      3. 每个 ThreadLocal 对象定义的时候一般都是 static 类型
 *      4. ThreadLocal 中保存的数据，在线程销毁后，会由 JVM 自动释放。
 *  ThreadLocal 用于 存储[线程域]的数据对象，只要在这个线程中，就能共享此数据
 *  那么下一步，就是使用 ThreadLocal 和 Filter 管理事务.
 *
 *  回顾 Jdbc 数据库事务管理
 *  Connection conn = JdbcUtils.getConnection();
 *  try {
 *      conn.setAutoCommit(false);
 *      执行事务
 *      conn.commit(); // 没有异常，则手动提交事务
 *  } catch (Exception e) {
 *      conn.rollback(); // 回滚事务
 *  } finally {
 *      JdbcUtils.close(conn);
 *  }
 *  [暂时理解为：commit是将修改写入磁盘。此前的操作是将修改写入内存，所以如果发生异常，还是需要回滚事务，因为内存中的数据被修改了]
 *  要确保所有操作要么都成功，要么都失败，就必须要使用数据库的事务。
 *  要确保所有操作都在一个事务内，就必须要确保，所有操作都使用同一个 Connection 对象
 *  如何确保所有操作都使用一个 Connection 连接对象？
 *  我们可以使用 ThreadLocal 对象，来确保所有操作都使用同一个 Connection 对象。
 *  ThreadLocal 要确保所有操作都使用同一个 Connection 连接对象，那么操作的前提条件是
 *  所有操作都必须在同一个线程中完成(这个条件满足了)，那么应该如何使得所有连接都是一个连接？
 *
 *  ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
 *  threadLocal.set(conn) // 保存连接对象
 *  我们的目标是，使得Service 层 saveOrder函数的三个 Dao 使用同一个连接操作，这可能需要修改 BaseDao，统一管理连接
 *
 *  注意：servlet程序单例多线程，**每个http请求都会有一个线程!** 因此一次请求关闭连接之后，其它请求又会获得新的连接
 *  问：如何使用 Filter 给所有 Service 方法都加上 try...catch... 加上事务呢？
 *  分析：现在有一个 TransactionFilter 事务的 Filter 过滤器。
 *  public void doFilter() {
 *      try {
 *          filterChain.doFilter(); --> 可以访问资源(包括 jsp servlet程序等)
 *                                  [间接调用了servlet中的业务方法，servlet会直接调用 service 的方法]
 *                                  所以doFilter间接调用了 service 层的方法
 *          后置代码：提交事务
 *       } catch (Exception e) {
 *           回滚事务代码
 *       }
 *  }
 *  按照上述框架，就可以使用一个 Filter 一次性给所有 service 的方法统一加上 try catch 来实现事务管理
 *
 *  异常页面：将所有异常统一交给 Tomcat，让 Tomcat展示友好错误信息页面
 *  在 web.xml 可以通过错误页面配置进行管理
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