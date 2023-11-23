package com.ruc.bookstoreweb.filter;

import com.ruc.bookstoreweb.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author 3590
 * @Date 2023/11/21 22:01
 * @Description 使用它来管理 service 层的事务
 * @Version
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JdbcUtils.commitAndClose(); // 成功就提交事务
        } catch (Exception e) {
            e.printStackTrace();
            JdbcUtils.rollbackAndClose(); // 异常则回滚事务
            throw new RuntimeException(e); // 向外抛出异常，以便于能够被 Tomcat 捕获以展示错误页面
        }
    }

    @Override
    public void destroy() {}
}
