package com.ruc.bookstoreweb.dao.impl;

import com.ruc.bookstoreweb.dao.BaseDao;
import com.ruc.bookstoreweb.dao.OrderDao;
import com.ruc.bookstoreweb.pojo.Order;

/**
 * @Author 3590
 * @Date 2023/11/19 22:34
 * @Description
 * @Version
 */
public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(`order_id`, `create_time`, `price`, `status`, `user_id`) " +
                "values(?, ?, ?, ?, ?)";
        return update(sql, order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());
    }

    @Override
    public int deleteOrder(String orderId) {
        String sql = "delete from t_order where order_id = ?";
        return update(sql, orderId);
    }

}
