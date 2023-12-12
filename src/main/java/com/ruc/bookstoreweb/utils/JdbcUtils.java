package com.ruc.bookstoreweb.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 这个需要用！有了 dbUtils : BaseDAO 之类的函数依然要写，只是更简化了
 * 注意：获取连接和关闭连接的方法不要抛出异常！要自己处理异常！
 * */
public class JdbcUtils {
    private static DataSource dataSource = null; // Druid 连接池的 dataSource
    // <Connection> 表示 ThreadLocal 保存的数据类型
    // 加上 final 关键字可以有效防止内存泄漏
    public static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>(); // 同一个线程，同一个数据库连接

    // 只维护一个连接池，但是连接池里面有很多连接，因此这里需要使用静态代码
    static {
        // 注意：为了稳，强烈建议还是这么写吧... getSystemResourceAsStream 会在 TOMCAT运行时直接报错
        // 这可能是因为 System 的原因！即我们部署到浏览器后，就找不到这个资源了！
        InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("DruidJDBC.properties");
        Properties pros = new Properties();
        try {
            pros.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            // 注意：连接池的数据结构是 dataSource
            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 获取连接，能够确保同一个线程下的连接对象只有一个
     * */
    public static Connection getConnection() {
        try {
            // 如果先前还没有获得过连接，就必须获得连接，一个线程只能有一个连接
            // 改进：threadLocal.get().isClosed()
            // 如果连接关闭，就再给他一个连接，否则翻页等情况下可能线程池不够用，无法分配新线程，而
            // 连接已经关闭，会导致项目频繁出错
            if (threadLocal.get() == null || threadLocal.get().isClosed()) {
                // 使得同一线程下的连接一致
                threadLocal.set(dataSource.getConnection());
                // 更好的设计方案：直接在此处取消自动提交的设置
                threadLocal.get().setAutoCommit(false);
            }
            System.out.println("连接获取成功，爱来自Druid，连接是：" + threadLocal.get());
            return threadLocal.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 注意：DBUtils已经提供了关闭连接的接口，因此我们直接调用即可
     * */
    public static void rollbackAndClose() {
        Connection conn = threadLocal.get();
        DbUtils.rollbackAndCloseQuietly(conn);
    }
    public static void commitAndClose() {
        Connection conn = threadLocal.get();
        DbUtils.commitAndCloseQuietly(conn);
    }

    /**
     * 关闭连接，由于 QueryRunner 已经封装了 PrepareStatement，
     * 也封装了 ResultSet 这种东西，因此现在只需要手动关闭连接 Connection
     * @param conn 准备关闭的数据库连接
     * @deprecated 由于我们将事务设置为手动提交，所以关闭连接请调用 rollbackAndClose 或者 commitAndClose 方法
     * */
    @Deprecated
    public static void close(Connection conn) {
        // 直接调用接口关闭连接即可
        DbUtils.closeQuietly(conn);
    }

}
