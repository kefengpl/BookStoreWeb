package com.ruc.bookstoreweb.service.impl;

import com.ruc.bookstoreweb.dao.BookDao;
import com.ruc.bookstoreweb.dao.OrderDao;
import com.ruc.bookstoreweb.dao.OrderItemDao;
import com.ruc.bookstoreweb.dao.impl.BookDaoImpl;
import com.ruc.bookstoreweb.dao.impl.OrderDaoImpl;
import com.ruc.bookstoreweb.dao.impl.OrderItemDaoImpl;
import com.ruc.bookstoreweb.pojo.*;
import com.ruc.bookstoreweb.service.OrderService;

import java.math.BigDecimal;
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
        // 设置跟踪变量，考察是否生成了订单
        orderDao.saveOrder(new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId));
        // 2. 生成订单项(多个)，采用遍历购物车的方式
        for (var entry : cart.getItems().entrySet()) {
            CartItem item = entry.getValue();
            // 修改数据库表的剩余库存，且增加销售量
            Book book = bookDao.queryBookById(item.getId());
            // 逻辑：库存最多下降到0！
            Integer realCount = book.getStock() >= item.getCount() ? item.getCount() : book.getStock();
            realTotalCount += realCount; // 跟踪实际结算的商品总数
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
        // 如果购物车商品总数 等于 实际结算商品总数，说明完全结算了
        if (cart.getTotalCount().equals(realTotalCount) && realTotalCount > 0) {
            settleResult = SettleResult.ALL_SETTLED;
        }
        // 3. 用户已经付款(生成订单说明已经结算了！)，需要清空购物车
        cart.clear();
        // 4. 返回结果 [不得不说，这绝对是非常弱智的选择...]
        return new AbstractMap.SimpleEntry<String, SettleResult>(orderId, settleResult);
    }

    @Override
    public List<Order> queryAllOrders() {
        return null;
    }

    @Override
    public void sendOrder() {

    }

    @Override
    public OrderItem showOrderDetail(String orderId) {
        return null;
    }

    @Override
    public void receiveOrder(String orderId) {

    }
}
