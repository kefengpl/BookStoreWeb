package com.ruc.bookstoreweb.web;/**
 * @Author 3590
 * @Date 2023/11/12 21:15
 * @Description
 * @Version
 */

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
 * 业务流程详细概述：
 * 1. 列表功能
 *   首页 -- 后台管理 -- 图书管理(manager.jsp页面) -- 跳入 book_manager.jsp -- 该页面展示图书信息
 *   我们不能使用 jsp 直接查询数据库，所以 jsp 需要使用 web 层，即本 class，而里面有一个 list 函数
 *   list需要做的事情：1. 查询全部图书 2. 保存到 Request 中 3. 请求转发到 pages/manager/manager_book.jsp
 *   而 manager_book.jsp 需要从 Request 域中获取全部图书信息 2. 遍历输出结果即可
 *   NOTE 如果访问 jsp 无法直接得到数据，可以先让程序访问 Servlet ，再进行请求转发
 * 2. 如果点击修改，需要显示待修改的记录
 *   PART1 在 book_edit.jsp 回显修改信息
 *   在 book_manager.jsp 中，显示的是一行一行的记录，每行都可以点击修改
 *   点击 修改 后进入 book_edit 图书编辑。
 *   注意 jsp 页面无法直接查询数据库，不要让程序直接跳转，先让它经过 Servlet，即 BookServlet
 *   public void getBook() 获取要修改的图书信息
 *   1. 在 book_manager.jsp 点击跳转，跳转到 BookServlet，传递的参数是 id，
 *   2. 然后在 Servlet 中 getBook()，查询该数据，然后将查出的数据保存到 request域中，转入 book_edit.jsp(通过请求转发)
 *   PART2
 *   提交修改后的数据给服务器保存修改
 *   =================================
 * 新的任务：图书分页，如何实现分页？
 *   由分页视图分析出分页的对象模型 Page
 *   pageNo 当前页码
 *   pageTotal 总页码
 *   pageTotalCount 总记录数
 *   pageSize 每页显示数量
 *   items 当前页数据
 *  pageNo 当前页码是由客户端进行传递，用户点击 3，就是第三页
 *  pageSize 由两种因素决定：①客户端进行传递 ②由页面布局决定
 *  pageTotalCount 可以由 SELECT COUNT(*) 求出
 *  pageTotal = pageTotalCount / pageSize 需要向上取整，算法 pageTotalCount % pageSize 则总页码 + 1
 *  items 是当前页数据，也是可以由 SQL语句求得， select * from t_book limit begin, size; begin 开始索引(从0开始)， size 每页数量
 *  begin = (pageNo - 1) * pageSize
 * 那么分页该如何实现呢？
 *  点击页数导航条，比如“首页”，传入 BookServlet 程序，有一个 page 函数用于处理分页，需要传递两个参数  pageNo pageSize
 *  page 函数的处理过程：请求参数 pageNo 和 pageSize ，调用 BookService.page(pageNo, pageSize) 用于回显数据，保存到 Request域中
 *  最后请求转发到 book_manager.jsp
 * service 层需要干什么？ 注意：需要返回的是 分页对象！关键就是求 上面 五个属性，注意 items 需要调用 DAO 层
 *  public Page page(pageNo, pageSize) {} 处理分页业务
 * BookDao 程序 queryForPageTotalCount() 求总记录数
 *             queryForItems() 求当前页数据
 * */
public class BookServlet extends BaseServlet {
    BookService bookService = new BookServiceImpl();
    /**
     * 注意：在这种架构设计下，函数名称和 jsp 传入的参数十分重要！
     * */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 添加图书
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        bookService.addBook(book);
        // 请求转发
        // 这样写会导致表单重复提交，应该使用重定向
        // list(request, response);
        // 这样就不会重复添加了
        response.sendRedirect("/BookStoreWeb_war_exploded/manager/book?action=list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 处理修改图书的操作
     * 1. 获取请求的参数，封装成为 book 对象
     * 2. 调用 bookservice.update
     * 3. 重定向页面，重定向 book_manager.jsp 页面
     * */
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取请求参数，注意这里需要注入 JavaBean
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        System.out.println(request.getParameterMap());
        // 2. update
        bookService.updateBook(book);
        // 3. 重定向
        response.sendRedirect("/BookStoreWeb_war_exploded/manager/book?action=list");
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 查询列表
        List<Book> bookList = bookService.queryBooks();
        // 2. 保存结果
        request.setAttribute("list", bookList);
        // 3. 请求转发，记住，无论如何 / 解析的含义是完全一致的，与 web.xml 中的配置没有任何关系
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
        System.out.println("请求转发成功！");
    }

    /**
     * 在用户点击修改之后，你需要回显修改所在行的图书记录
     * */
    private void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取请求参数 id
        int id = Integer.parseInt(request.getParameter("id"));
        // 2. 通过 id 查询该记录
        Book book = bookService.queryBookById(id);
        // 3. 将该记录存到 request 域中
        request.setAttribute("modifyItem", book);
        // 4. 请求转发
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
    }

    /**
     * 实现分页功能
     * */
    private void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 当前页码 和 每页显示的条目数量，默认是第一页！
        int pageNo = request.getParameter("pageNo") == null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = request.getParameter("pageSize") != null ? Integer.parseInt(request.getParameter("pageSize")) : Page.PAGE_SIZE;
        // 调用 Service 层，产生分页对象
        Page<Book> page = bookService.page(pageNo, pageSize);
        // 将数据存入 request 域当中
        request.setAttribute("page", page);
        // 进行请求转发
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
    }
}