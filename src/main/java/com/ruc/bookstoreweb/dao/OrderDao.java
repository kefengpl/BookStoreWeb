package com.ruc.bookstoreweb.dao;

import com.ruc.bookstoreweb.pojo.Order;

/**
 * @Author 3590
 * @Date 2023/11/19 22:32
 * @Description
 * @Version
 */
public interface OrderDao {
    // 保存订单
    public int saveOrder(Order order);

    // 根据 orderId 删除订单
    public int deleteOrder(String orderId);

}
