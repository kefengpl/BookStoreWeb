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

    /**
     * 根据价格区间查找数据并进行分页
     * 这样实现后仍然有问题，即如何将分页条换页后仍然能够来到 pageByPrice 而不是 下面的 page 函数？
     * 或许我们需要价格区间的回显！
     * 一个更新：支持查询 单侧 价格索引，比如 两个输入框只在左侧输入 5，那么会查询 >= 5 的结果
     * */
    public void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 当前页码 和 每页显示的条目数量，默认是第一页！
        int pageNo = WebUtils.parseValue(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseValue(request.getParameter("pageSize"), Page.PAGE_SIZE);

        Integer minVal = getBoundary(request, "min");
        Integer maxVal = getBoundary(request, "max");

        if (minVal == null && maxVal == null) {
            request.setAttribute("writeErrorMsg", "请至少在价格下限或者上限中填写一个");
            page(request, response);
            return;
        }
        Page<Book> page = bookService.pageByPrice(pageNo, pageSize, minVal, maxVal);
        page.setUrl("client/book");
        request.setAttribute("page", page);
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }

    public void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // minVal MaxVal 是用来搜索价格区间的！
        Integer minVal = getBoundary(request, "min");
        Integer maxVal = getBoundary(request, "max");
        if (minVal != null || maxVal != null) {
            pageByPrice(request, response);
            return;
        }

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

    Integer getBoundary(HttpServletRequest request, String varName) {
        try {
            return Integer.parseInt(request.getParameter(varName));
        } catch (Exception e) {
            return null;
        }
    }
}
