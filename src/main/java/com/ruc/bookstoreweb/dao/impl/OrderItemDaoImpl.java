package com.ruc.bookstoreweb.dao.impl;

import com.ruc.bookstoreweb.dao.BaseDao;
import com.ruc.bookstoreweb.dao.OrderItemDao;
import com.ruc.bookstoreweb.pojo.OrderItem;

/**
 * @Author 3590
 * @Date 2023/11/19 22:34
 * @Description
 * @Version
 */
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item(`name`, `count`, `price`, `total_price`, `order_id`) " +
                "values(?, ?, ?, ?, ?)";
        return update(sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(),
                orderItem.getTotalPrice(), orderItem.getOrderId());
    }
}
