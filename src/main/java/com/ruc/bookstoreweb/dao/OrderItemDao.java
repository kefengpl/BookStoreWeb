package com.ruc.bookstoreweb.dao;

import com.ruc.bookstoreweb.pojo.OrderItem;

/**
 * @Author 3590
 * @Date 2023/11/19 22:33
 * @Description
 * @Version
 */
public interface OrderItemDao {
    // 保存订单项
    public int saveOrderItem(OrderItem orderItem);
}
