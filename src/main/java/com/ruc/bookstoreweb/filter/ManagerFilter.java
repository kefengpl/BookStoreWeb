package com.ruc.bookstoreweb.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 3590
 * @Date 2023/11/20 23:01
 * @Description 用来管理后台 pages/manager/* 。。一些值得注意的地方： init 和 destroy 必须重载实现，否则必定报错！
 * @Version
 */
public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    /**
     * 对没有登录的情况进行拦截
     * */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 我们使用 session 中的 uid，来检验用户是否登录
        if (request.getSession().getAttribute("userId") == null) {
            // 如果用户没有登录，就不让进入后台
            // 注意：如果是请求转发，则浏览器虽然会显示登录界面，但是浏览器地址栏会显示 servletRequest 你请求的地址！
            response.sendRedirect("/BookStoreWeb_war_exploded/pages/user/login.jsp");
        } else {
            // 如果用户已经登录，则允许进入后台
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
