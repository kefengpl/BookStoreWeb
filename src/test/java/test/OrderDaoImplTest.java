package test;

import com.ruc.bookstoreweb.dao.OrderDao;
import com.ruc.bookstoreweb.dao.impl.OrderDaoImpl;
import com.ruc.bookstoreweb.pojo.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 3590
 * @Date 2023/11/19 22:42
 * @Description
 * @Version
 */
class OrderDaoImplTest {

    @Test
    void saveOrder() {
        OrderDao orderDao = new OrderDaoImpl();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse("2022-11-12");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderDao.saveOrder(new Order("1234567890", date, new BigDecimal(5.25), 0, 2));
    }
}