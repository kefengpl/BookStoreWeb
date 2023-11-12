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
     * 获取连接
     * */
    public static Connection getConnection() {
        try {
            Connection conn = dataSource.getConnection();
            System.out.println("连接获取成功，爱来自Druid，连接是：" + conn);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接，由于 QueryRunner 已经封装了 PrepareStatement，
     * 也封装了 ResultSet 这种东西，因此现在只需要手动关闭连接 Connection
     * @param conn 准备关闭的数据库连接
     * */
    public static void close(Connection conn) {
        // 直接调用接口关闭连接即可
        DbUtils.closeQuietly(conn);
    }
}
