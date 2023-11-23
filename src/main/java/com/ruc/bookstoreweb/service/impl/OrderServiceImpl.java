package com.ruc.bookstoreweb.service.impl;

import com.ruc.bookstoreweb.dao.BookDao;
import com.ruc.bookstoreweb.dao.OrderDao;
import com.ruc.bookstoreweb.dao.OrderItemDao;
import com.ruc.bookstoreweb.dao.impl.BookDaoImpl;
import com.ruc.bookstoreweb.dao.impl.OrderDaoImpl;
import com.ruc.bookstoreweb.dao.impl.OrderItemDaoImpl;
import com.ruc.bookstoreweb.pojo.*;
import com.ruc.bookstoreweb.service.OrderService;
import com.ruc.bookstoreweb.utils.JdbcUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/19 23:01
 * @Description
 * @Version
 */
public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    OrderItemDao orderItemDao = new OrderItemDaoImpl();
    BookDao bookDao = new BookDaoImpl();

    /**
     * @return 生成的订单号
     * */
    @Override
    public Map.Entry<String, SettleResult> createOrder(Cart cart, Integer userId) {
        // 1. 生成订单 [注意：这里的逻辑是：一个订单可以对应多个订单项！]
        // [比如：我在2023-11-19创建了1笔订单，它包含 巧克力，彩虹糖，旺旺雪饼 这三个订单项]
        // 订单号要求唯一性，这里可以借助时间戳，再加上userId就是唯一的
        // "" 的作用就是实现 左右两侧 long / Integer 类型的隐式转换
        // new Date() java.util.Date 生成的就是当前的时间戳
        String orderId = System.currentTimeMillis() + "" +userId;
        SettleResult settleResult = SettleResult.NO_SETTLED; // 初始是没有结算
        Integer realTotalCount = 0; // 获得真实结算的商品数量
        BigDecimal realTotalPrice = new BigDecimal(0);   // 获得真实的商品结算最终价格
        /**
         * 注意：下面的操作是一个整体，需要原子化，例如：不能只生成了订单，但是没有订单项
         * 另一种情况：用户订单已经成功结算，但是库存没扣...这些都破坏了“不变量”
         * 下面这种做法(在函数里面使用事务)普适性很差，所以被弃用
         * */
        // Connection conn = JdbcUtils.getConnection();

        // 原子化：设置不自动提交
        // conn.setAutoCommit(false);
        // 注意：订单的总价格还需要再修改一下，所以初始直接给 null
        orderDao.saveOrder(new Order(orderId, new Date(), null, 0, userId));
        // 2. 生成订单项(多个)，采用遍历购物车的方式
        for (var entry : cart.getItems().entrySet()) {
            CartItem item = entry.getValue();
            // 修改数据库表的剩余库存，且增加销售量
            Book book = bookDao.queryBookById(item.getId());
            // 逻辑：库存最多下降到0！
            Integer realCount = book.getStock() >= item.getCount() ? item.getCount() : book.getStock();
            realTotalCount += realCount; // 跟踪实际结算的商品总数
            realTotalPrice = realTotalPrice.add(item.getPrice().multiply(new BigDecimal(realCount))); // 获得实际结算的商品总价格
            book.setStock(book.getStock() - realCount);
            book.setSales(book.getSales() + realCount);
            bookDao.updateBook(book);
            // 保存订单项到数据库
            if (realCount > 0) {
                settleResult = SettleResult.PART_SETTLED;
                // 注意：只能结算有效交易的数量
                orderItemDao.saveOrderItem(new OrderItem(null, item.getName(), realCount, item.getPrice(),
                        item.getPrice().multiply(new BigDecimal(realCount)), orderId));
            }
        }
        // 如果用户实际结算商品数是0，就删除订单
        if (realTotalCount == 0) {
            orderDao.deleteOrder(orderId);
        } else {
            // 否则就把订单的总结算价格填入
            orderDao.updateOrder(orderId, realTotalPrice);
        }
        // 如果购物车商品总数 等于 实际结算商品总数，说明完全结算了
        if (cart.getTotalCount().equals(realTotalCount) && realTotalCount > 0) {
            settleResult = SettleResult.ALL_SETTLED;
        }
        // 3. 用户已经付款(生成订单说明已经结算了！)，要么库存不足无法生成订单，无论如何都需要清空购物车
        cart.clear();
        // 4. 返回结果 [不得不说，这绝对是非常弱智的选择...]
        return new AbstractMap.SimpleEntry<String, SettleResult>(orderId, settleResult);
    }

    @Override
    public List<Order> queryAllOrders() {
        return orderDao.queryOrders();
    }

    @Override
    public void sendOrder(String orderId) {
        orderDao.changeOrderStatus(orderId, 1);
    }

    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        return orderItemDao.queryOrderItemsByOrderId(orderId);
    }

    @Override
    public List<Order> showMyOrders(Integer userId) {
        return orderDao.queryOrdersByUserId(userId);
    }

    @Override
    public void receiveOrder(String orderId) {
        orderDao.changeOrderStatus(orderId, 2);
    }
}
