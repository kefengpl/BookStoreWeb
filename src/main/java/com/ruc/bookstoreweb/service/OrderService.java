package com.ruc.bookstoreweb.service;

import com.ruc.bookstoreweb.pojo.Cart;
import com.ruc.bookstoreweb.pojo.Order;
import com.ruc.bookstoreweb.pojo.OrderItem;
import com.ruc.bookstoreweb.service.impl.OrderServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/19 23:01
 * @Description
 * @Version
 */
public interface OrderService {
    // 结算结果
    public enum SettleResult {ALL_SETTLED, PART_SETTLED, NO_SETTLED};
    // 生成订单
    public Map.Entry<String, SettleResult> createOrder(Cart cart, Integer userId);
    // 查询全部订单
    public List<Order> queryAllOrders();
    // 发货
    public void sendOrder();
    // 查看订单详情
    public OrderItem showOrderDetail(String orderId);
    // 签收订单，确认收获
    public void receiveOrder(String orderId);
}
