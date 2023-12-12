package com.ruc.bookstoreweb.service;

import com.ruc.bookstoreweb.pojo.Cart;
import com.ruc.bookstoreweb.pojo.Order;
import com.ruc.bookstoreweb.pojo.OrderItem;
import com.ruc.bookstoreweb.pojo.Page;
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
    /**
     * 结算结果：ALL_SETTLED 表示购物车所有商品全部结算；
     * PART_SETTLED 表示购物车商品部分结算；
     * NO_SETTLED 表示由于库存原因，没有任何商品被结算了，但是购物车已经被清空了。
     * */
    public enum SettleResult {ALL_SETTLED, PART_SETTLED, NO_SETTLED};
    /**
     * 生成订单
     * @return std::pair 返回 订单号 和 订单结算状态
     * */
    public Map.Entry<String, SettleResult> createOrder(Cart cart, Integer userId);
    /**
     * 查询全部订单
     * */
    public List<Order> queryAllOrders();
    /**
     * 执行操作：发货，即把订单状态进行修改
     * @param orderId 订单的 Id
     * */
    public void sendOrder(String orderId);
    /**
     * 查看订单详情
     * @param orderId 订单的 Id
     * @return 返回订单包含的所有订单项
     * */
    public List<OrderItem> showOrderDetail(String orderId);

    /**
     * 查看一个用户的所有订单
     * @param userId 订单的 Id
     * @return 返回一个用户的所有订单
     * */
    public List<Order> showMyOrders(Integer userId);

    /**
     * 签收订单，确认收货。即把订单状态从 1 转到 2。
     * @param orderId 订单的 Id
     * */
    public void receiveOrder(String orderId);

    Integer getPageTotal(int pageSize);

    Integer getUserPageTotal(Integer userId, int pageSize);

    /**
     * 所有订单分页功能
     * */
    public Page<Order> allOrderPage(Integer pageNo, Integer pageSize);

    /**
     * 用户订单分页功能
     * */
    public Page<Order> userOrderPage(Integer userId, Integer pageNo, Integer pageSize);


}
