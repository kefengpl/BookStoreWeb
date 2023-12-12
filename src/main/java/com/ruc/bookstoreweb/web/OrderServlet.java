package com.ruc.bookstoreweb.web;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause;
import com.ruc.bookstoreweb.pojo.Cart;
import com.ruc.bookstoreweb.pojo.Order;
import com.ruc.bookstoreweb.pojo.OrderItem;
import com.ruc.bookstoreweb.pojo.Page;
import com.ruc.bookstoreweb.service.OrderService;
import com.ruc.bookstoreweb.service.impl.OrderServiceImpl;
import com.ruc.bookstoreweb.utils.JdbcUtils;
import com.ruc.bookstoreweb.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/19 23:29
 * @Description
 * @Version
 */
public class OrderServlet extends BaseServlet {
    OrderService orderService = new OrderServiceImpl();
    /**
     * 生成订单
     * */
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String finalMsg = null;
        // 1. 获取用户购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) finalMsg = "您的购物车为空，无法生成订单！";
        // 2. 获取 用户 id
        // 细节： getParameter 它只能获得字符串类型，所以需要 parseInt，而 getAttribute 无需这样，它返回 Object 类型，只需要强转即可
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) finalMsg = "您没有登入，无法生成订单！";
        // 强制要求：生成订单必须保证 ① 购物车里有东西 ② 用户必须是登录状态
        if ((cart != null && !cart.getItems().isEmpty()) && userId != null) {
            // 3. 生成订单
            Map.Entry<String, OrderService.SettleResult> generateResult = orderService.createOrder(cart, userId);
            // 结算状态：部分结算/全部结算/没有结算
            OrderService.SettleResult settleStatus = generateResult.getValue();
            String orderId = generateResult.getKey();
            switch (settleStatus) {
                case NO_SETTLED:
                    finalMsg = "由于库存不足，你的商品没有结算，无法生成订单";
                    break;
                case PART_SETTLED:
                    finalMsg = "你的订单已经结算，订单号为" + orderId + "，但是由于库存原因，部分商品没有结算";
                    break; // 细微bug：switch没加 break
                case ALL_SETTLED:
                    finalMsg = "恭喜！你的订单已经结算成功，订单号为" + orderId;
                    break;
            }
        }
        // 4. 重定向
        request.getSession().setAttribute("orderFinalMsg", finalMsg);
        response.sendRedirect("/BookStoreWeb_war_exploded/pages/cart/checkout.jsp");
    }

    /**
     * 实现分页
     * */
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        Integer userId = null;
        // 当前页码 和 每页显示的条目数量，默认是第一页！
        int pageNo = WebUtils.parseValue(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseValue(request.getParameter("pageSize"), Page.TABLE_PAGE_SIZE);
        int pageTotal = 0;
        if (mode.equals("user")) {
            userId = (Integer) request.getSession().getAttribute("userId");
            pageTotal = orderService.getUserPageTotal(userId, pageSize);
        } else {
            pageTotal = orderService.getPageTotal(pageSize);
        }
        Integer curPageNo = WebUtils.parseValue(request.getParameter("curPageNo"), null);
        // 你输入的页码越界了！
        if (pageNo <= 0 || pageNo > pageTotal) {
            pageNo = curPageNo == null ? (pageNo <= 0 ? 1 : pageTotal) : curPageNo;
            request.setAttribute("errorMsg", "你输入的页码不在范围内!");
        }
        Page<Order> page = orderService.userOrderPage(userId, pageNo, pageSize);
        if (mode.equals("user")) {
            // 获取订单列表
            page = orderService.userOrderPage(userId, pageNo, pageSize);
        } else {
            page = orderService.allOrderPage(pageNo, pageSize);
        }
        // BUG 一定要手动设置 URL！！！
        page.setUrl("order");
        // 存入 request
        request.setAttribute("page", page);
        String dispatcherFile =  mode.equals("user") ? "order.jsp" : "order_manage.jsp";
        // 请求转发
        request.getRequestDispatcher("/pages/order/" + dispatcherFile).forward(request, response);
    }

    /**
     * 展示订单细节
     * */
    protected void showOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取订单编号
        String orderId = request.getParameter("id");
        // 获得订单细节
        List<OrderItem> orderDetails = orderService.showOrderDetail(orderId);
        // 进行请求转发(注意：只要不是数据表更新操作，其实是不必使用重定向的)
        request.setAttribute("orderDetails", orderDetails);
        request.getRequestDispatcher("/pages/order/order_detail.jsp").forward(request, response);
    }

    /**
     * 实现发货功能
     * */
    protected void sendOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取订单编号
        String orderId = request.getParameter("id");
        // 发货：将订单状态由0修改为1
        orderService.sendOrder(orderId);
        // 返回
        response.sendRedirect(request.getHeader("Referer"));
    }

    /**
     * 实现收货功能
     * */
    protected void receiveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取订单编号
        String orderId = request.getParameter("id");
        // 发货：将订单状态由1修改为2
        orderService.receiveOrder(orderId);
        // 返回
        response.sendRedirect(request.getHeader("Referer"));
    }
}
