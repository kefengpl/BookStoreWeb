package test;

import com.ruc.bookstoreweb.dao.OrderDao;
import com.ruc.bookstoreweb.dao.impl.OrderDaoImpl;
import com.ruc.bookstoreweb.pojo.Order;
import com.ruc.bookstoreweb.utils.JdbcUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 3590
 * @Date 2023/11/19 22:42
 * @Description
 * @Version
 */
class OrderDaoImplTest {

    OrderDao orderDao = new OrderDaoImpl();
    @Test
    void saveOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse("2022-11-12");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderDao.saveOrder(new Order("1234567890", date, new BigDecimal(5.25), 0, 2));
    }

    @Test
    public void testQueryOrders() {
        System.out.println(orderDao.queryOrders());
    }

    @Test
    public void testChangeOrderStatus() {
        // 注意：由于事务暂时设置需要手动提交，所以应手动提交事务
        orderDao.changeOrderStatus("1234567890", 3);
        try {
            // 尝试通过反射获取私有静态变量
            Class<JdbcUtils> jdbcUtilsClass = JdbcUtils.class;
            Field field = jdbcUtilsClass.getDeclaredField("threadLocal");
            field.setAccessible(true);
            // 获取其中的私有静态成员变量
            ThreadLocal<Connection> threadLocal = (ThreadLocal<Connection>) field.get(null);
            threadLocal.get().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryOrdersByUserId() {
        System.out.println(orderDao.queryOrdersByUserId(2));
    }
}