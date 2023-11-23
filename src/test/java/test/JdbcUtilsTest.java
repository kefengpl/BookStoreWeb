package test;

import com.ruc.bookstoreweb.utils.JdbcUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class JdbcUtilsTest {
    @Test
    public void testJdbcUtils() {
        // for (int i = 0; i < 100; i++)
        // 若循环100次，则只能最大获得10个，这是由配置文件里面maxActive=10最大连接池数量决定的
        // 所以连接要及时释放，即conn.close()，此时获取多少个都没有问题
        // 正因为每次获取了连接都会释放，所以这 100 个连接的地址都是 @428640fa，即每次我们都获得的是连接池中一个特定的连接
        /*for (int i = 0; i < 100; ++i) {
            Connection conn = JdbcUtils.getConnection();
            System.out.println(conn);
            JdbcUtils.close(conn);
        }*/
    }

}
