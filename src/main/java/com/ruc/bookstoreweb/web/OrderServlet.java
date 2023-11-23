package com.ruc.bookstoreweb.web;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause;
import com.ruc.bookstoreweb.pojo.Cart;
import com.ruc.bookstoreweb.pojo.Order;
import com.ruc.bookstoreweb.service.OrderService;
import com.ruc.bookstoreweb.service.impl.OrderServiceImpl;
import com.ruc.bookstoreweb.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    protected void showAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 用户登录后展示订单
     * */
    protected void showMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户ID
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            // 如果没有登录，就显示错误信息
            request.setAttribute("myOrderErrorMsg", "您尚未登录，无法显示订单信息");
        } else {
            // 获取订单列表
            List<Order> myOrders = orderService.showMyOrders(userId);
            // 存入 request
            request.setAttribute("myOrders", myOrders);
        }
        // 请求转发
        request.getRequestDispatcher("/pages/order/order.jsp").forward(request, response);
    }
}
