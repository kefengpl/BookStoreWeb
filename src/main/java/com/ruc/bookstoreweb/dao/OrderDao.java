package com.ruc.bookstoreweb.dao;

import com.ruc.bookstoreweb.pojo.Order;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author 3590
 * @Date 2023/11/19 22:32
 * @Description
 * @Version
 */
public interface OrderDao {
    /**
     * 保存订单
     * */
    public int saveOrder(Order order);

    /**
     * 根据 orderId 删除订单
     * */
    public int deleteOrder(String orderId);

    /**
     *     根据 orderId 和 最终结算价格(可能由于库存为0导致用户购物车无法完全结算)
     */
    public int updateOrder(String orderId, BigDecimal finalPrice);

    /**
     * 查询全部订单
     */
    public List<Order> queryOrders();

    /**
     * 修改订单状态
     */
    public int changeOrderStatus(String orderId, Integer status);

    /**
     * 根据用户 ID 查询其所有订单信息
     * */
    public List<Order> queryOrdersByUserId(Integer userId);

}
