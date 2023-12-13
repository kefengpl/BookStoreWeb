package com.ruc.bookstoreweb.web;

import com.google.gson.Gson;
import com.ruc.bookstoreweb.pojo.Book;
import com.ruc.bookstoreweb.pojo.Cart;
import com.ruc.bookstoreweb.pojo.CartItem;
import com.ruc.bookstoreweb.service.BookService;
import com.ruc.bookstoreweb.service.impl.BookServiceImpl;
import com.ruc.bookstoreweb.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/19 14:52
 * @Description
 * @Version
 */
public class CartServlet extends BaseServlet {

    BookService bookService = new BookServiceImpl();

    /**
     * 实现加入购物车功能
     * 注意：我们的购物车功能似乎只依赖于 session，不依赖于 MySQL
     * 它应该完成的事情如下：
     *    1. 获取请求的参数 商品编号
     *    2. 调用 bookService.queryBookById(id) 得到图书信息
     *    3. 调用 Cart.addItem(CartItem)添加商品项
     *    4. 重定向回商品列表页面
     * */
    private void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 生成 CartItem
        Book book = bookService.queryBookById(WebUtils.parseValue(request.getParameter("id"), null));
        if (book == null) return;
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        HttpSession session = request.getSession();
        // 2. 将它加入 Cart 里面
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new Cart());
        }
        ((Cart) session.getAttribute("cart")).addItem(cartItem);
        // 记录上次添加的书籍名称
        session.setAttribute("lastbook", book.getName());
        // 3. 重定向回商品列表页面（注意需要通过 Servlet 请求转发）
        // 代码优化 Referer 表示该请求发出时，客户端浏览器正在访问的地址，借助它可以很好地回退到用户点击“添加购物车”时的页面
        // 例如 Referer：http://localhost:8080/BookStoreWeb_war_exploded/client/book?action=page&pageNo=2&min=&max=
        // response.sendRedirect(request.getHeader("Referer"));
        Gson gson = new Gson();
        Map<String, String> addInfo = new HashMap<>();
        addInfo.put("bookName", book.getName());
        Integer cartCount = ((Cart) session.getAttribute("cart")).getTotalCount();
        addInfo.put("cartCount", String.valueOf(cartCount));
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(gson.toJson(addInfo));
    }

    /**
     * 删除一条购物车记录
     * */
    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) return;
        cart.deleteItem(WebUtils.parseValue(request.getParameter("id"), null));
        response.sendRedirect(request.getHeader("Referer"));
    }

    /**
     * 清空购物车
     * */
    private void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            response.sendRedirect(request.getHeader("Referer"));
            return;
        }
        cart.clear();
        response.sendRedirect(request.getHeader("Referer"));
    }

    /**
     * 修改 id 商品的 数量 count
     * */
    private void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) return;
        cart.updateCount(WebUtils.parseValue(request.getParameter("id"), null),
                         WebUtils.parseValue(request.getParameter("count"), null));
        response.sendRedirect(request.getHeader("Referer"));
    }
}
