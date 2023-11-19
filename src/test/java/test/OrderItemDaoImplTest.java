package test;

import com.ruc.bookstoreweb.dao.OrderDao;
import com.ruc.bookstoreweb.dao.OrderItemDao;
import com.ruc.bookstoreweb.dao.impl.OrderItemDaoImpl;
import com.ruc.bookstoreweb.pojo.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 3590
 * @Date 2023/11/19 22:42
 * @Description
 * @Version
 */
class OrderItemDaoImplTest {

    @Test
    void saveOrderItem() {
        OrderItem orderItem = new OrderItem();
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        orderItemDao.saveOrderItem(new OrderItem(null, "数据结构与算法", 2, new BigDecimal(2.5),
                new BigDecimal(5), "123456789"));
    }
}