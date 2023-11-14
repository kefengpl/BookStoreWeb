package com.ruc.bookstoreweb.web;

import com.ruc.bookstoreweb.pojo.Book;
import com.ruc.bookstoreweb.pojo.Page;
import com.ruc.bookstoreweb.service.BookService;
import com.ruc.bookstoreweb.service.impl.BookServiceImpl;
import com.ruc.bookstoreweb.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * @Author 3590
 * @Date 2023/11/14 23:53
 * @Description
 * @Version
 */
public class ClientBookServlet extends BaseServlet {
    BookService bookService = new BookServiceImpl();
    public void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 当前页码 和 每页显示的条目数量，默认是第一页！
        int pageNo = WebUtils.parseValue(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseValue(request.getParameter("pageSize"), Page.PAGE_SIZE);
        Integer curPageNo = WebUtils.parseValue(request.getParameter("curPageNo"), null);
        int pageTotal = bookService.getPageTotal(pageSize);
        // 下面这种情况表明是从 form 表单提交的 跳转至第 n 页
        // 你输入的页码越界了！
        if (pageNo <= 0 || pageNo > pageTotal) {
            pageNo = curPageNo == null ? (pageNo <= 0 ? 1 : pageTotal) : curPageNo;
            request.setAttribute("errorMsg", "你输入的页码不在范围内!");
        }

        // 调用 Service 层，产生分页对象
        Page<Book> page = bookService.page(pageNo, pageSize);
        page.setUrl("client/book");
        // 将数据存入 request 域当中
        request.setAttribute("page", page);
        // 进行请求转发
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }
}
