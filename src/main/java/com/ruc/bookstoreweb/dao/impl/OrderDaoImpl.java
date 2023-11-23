package com.ruc.bookstoreweb.dao.impl;

import com.ruc.bookstoreweb.dao.BaseDao;
import com.ruc.bookstoreweb.dao.OrderDao;
import com.ruc.bookstoreweb.pojo.Order;

import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 根据结算结果，部分商品可能没有库存，此时需要我们去更新 订单 的总价格
     * */
    @Override
    public int updateOrder(String orderId, BigDecimal finalPrice) {
        String sql = "update t_order set price = ? where order_id = ?";
        return update(sql, finalPrice, orderId);
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "select order_id orderId, create_time createTime, price, status, user_id userId from t_order";
        return queryForList(Order.class, sql);
    }

    @Override
    public int changeOrderStatus(String orderId, Integer status) {
        String sql = "update t_order set status = ? where order_id = ?";
        return update(sql, status, orderId);
    }

    @Override
    public List<Order> queryOrdersByUserId(Integer userId) {
        String sql = "select order_id orderId, create_time createTime, price, status, user_id userId " +
                "from t_order where user_id = ?";
        return queryForList(Order.class, sql, userId);
    }

}
