package com.ruc.bookstoreweb.web;

import com.ruc.bookstoreweb.pojo.User;
import com.ruc.bookstoreweb.service.UserService;
import com.ruc.bookstoreweb.service.impl.UserServiceImpl;
import com.ruc.bookstoreweb.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @Author 3590
 * @Date 2023/11/12 18:12
 * @Description
 * @Version
 */
public class UserServlet extends BaseServlet {
    //@Override
    //protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取究竟是 注册行为 还是 登录行为？
        // String action = request.getParameter("action");
        /* if (action.equals("regist")) {
            regist(request, response);
        } else if (action.equals("login")) {
            login(request, response);
        }*/
        // request.setAttribute("test", "51658168165161631631");
        // 注意：请求转发，往回传给客户端的时候还会经过这个 UserServlet 程序！
        // 所以执行顺序是：handleRegist ---> regist.jsp(先输出errorMsg)(forward函数) ---> 执行下面的 sout
        // 也就是说，forward函数执行(显示jsp页面)完毕后才会调用forward后面的函数.

        /************************************************/
        // 迭代优化：我们如何通过反射来动态调用 函数 ？ 以便于少写 if else ?
        // 使用 this 指针来指代这个对象即可！
        /*Class clazz = UserServlet.class;
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
            handleMethod.invoke(this, request, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("UserServlet这个程序执行完毕，已将请求对外转发");
    }*/

    /**
     * 用于处理注册请求
     * */
    private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 首先获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        // 注意：为了获取输入的验证码，需要在验证码标签中加入 name="code"
        // 注意：为了防止页面在重新部署服务器后没有更新，应该选择[STOP CACHE][停用快取]选项
        String verifyCode = request.getParameter("code"); // 获取输入的验证码

        //User user = new User();
        //System.out.println("注入之前：" + user);
        // 注意请求的参数无论在 GET 还是 POST
        // 都是 username=kefeng&password=123
        // 只不过 GET 的参数显示在 URL中，POST 的参数显示在 实体体 中。
        /*try {
            // 这行代码把所有请求的参数注入到 user 对象中
            // 神奇的 Bean 注入
            BeanUtils.populate(user, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // WebUtils.copyParamToBean(request, user);
        //WebUtils.copyParamToBean(request.getParameterMap(), user);
        // 新迭代， WebUtils 一行完成注入
        User user = WebUtils.copyParamToBean(request.getParameterMap(), new User());
        System.out.println("注入之后：" + user);

        // 多年以后，补充验证码功能
        // 获取验证码 token，注意，只要验证码图片生成，GOOGLE 验证码包会自动把它保存到 session 里面
        // 此时用户重复提交表单，由于回退到上一步并不会重新请求 regist.jsp，
        // 所以不会生成新验证码，只有发现验证码错误后(即本代码段)，才会请求转发到 regist.jsp，此时验证码会重新生成
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 注意：获取了 token 必须立即删除 token！
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        // 2. 检查验证码是否正确？ 写死，要求验证码为abcde
        if (verifyCode.equalsIgnoreCase(token)) {
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

    /**
     * 用于处理登录请求
     * */
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 2. 调用 service 层处理业务
        UserService userService = new UserServiceImpl();
        User user = userService.login(new User(null, username, password, null));
        if (user != null) {

            System.out.println("尊敬的" + username + "，您已经登录成功！");
            // 功能补充：将用户名存入 session 用于回显
            request.getSession().setAttribute("username", username);
            // 将 userId 也存入 session 域中，用于回显
            request.getSession().setAttribute("userId", user.getId());

            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request, response);
        } else {
            System.out.println("登入失败，用户名或者密码错误！");
            // 登录失败，需要将原来的表单内容填写回去，保存到 request 域中(所谓 “请求转发”)
            // 如果不想写的话，就直接在 jsp 中使用 request.getParameter("username") 或者 ${param.username} 即可
            // 我们填入错误信息
            request.setAttribute("errorMsg", "用户名或者密码输入错误！");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如何进行登出操作呢？
        // 如果已经登入了，就尝试登出即可
        // 当然是直接销毁用户名对应的会话,把当前的会话毁掉
        // 毁掉并重定向到主页之后，就会立即重新创建一个 session
            request.getSession().invalidate();
        // 重定向到首页
        response.sendRedirect("/BookStoreWeb_war_exploded/index.jsp");
    }
}
