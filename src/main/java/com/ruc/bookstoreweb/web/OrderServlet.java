package com.ruc.bookstoreweb.web;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause;
import com.ruc.bookstoreweb.pojo.Cart;
import com.ruc.bookstoreweb.service.OrderService;
import com.ruc.bookstoreweb.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/19 23:29
 * @Description
 * @Version
 */
public class OrderServlet extends BaseServlet{
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
                    finalMsg = "你的订单已经结算，订单号为" + orderId + "但是由于库存原因，部分商品没有结算";
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
}
