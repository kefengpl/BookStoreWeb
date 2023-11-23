package com.ruc.bookstoreweb.dao;

import com.ruc.bookstoreweb.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * BaseDao不需要对象实例，所以设置为抽象类
 * 仅仅是为了复用代码，所有函数不要向外抛出异常
 * @update 由于采用 ThreadLocal和事务手动提交和回滚，所以所有方法的异常需要
 * 向外抛出，否则外部无法捕获，也难以回滚（比如，所有方法都设置了 e.print()
 * 那么外界无法捕获到任何异常，也不会回滚。如果下面这些方法的异常处理都是 conn.rollback()
 * 这样看似合理，但是比如外部应该连续调用两次 update，第一次 出现了问题， update 回滚了，
 * 第二次正常执行，这也不行。我们应该保证，这两个 update，要么都执行，要么都不执行。
 * */
public abstract class BaseDao {
    // 使用DbUtils操作数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * update() 用来执行Insert\Update\delete语句
     * @return 如果返回-1，说明执行失败；否则返回更新影响的行数
     * */
    public int update(String sql, Object ... args) {
        try {
            Connection connection = JdbcUtils.getConnection();
            return queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回一个javaBean的sql语句
     * @param type 返回的对象类型，是 Class 对象(反射)
     * @param sql 执行的sql语句
     * @param args sql对应的参数值
     * @param <T> 返回类型的泛型
     * @return
     * */
    public <T> T queryForOne(Class<T> type, String sql, Object ... args) {
        try {
            Connection connection = JdbcUtils.getConnection();
            // BeanHandler<T>(type) 通过反射构造了一个 Handler 对象
            return queryRunner.query(connection, sql, new BeanHandler<T>(type), args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回多个javaBean的sql语句
     * @param type 返回的对象类型，是 Class 对象(反射)
     * @param sql 执行的sql语句
     * @param args sql对应的参数值
     * @param <T> 返回类型的泛型
     * @return
     * */
    public <T> List<T> queryForList(Class<T> type, String sql, Object...args) {
        try {
            Connection conn = JdbcUtils.getConnection();
            return queryRunner.query(conn, sql, new BeanListHandler<>(type), args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用于聚合函数，比如 COUNT(*) 之查询
     * */
    public Number queryForSingleValue(String sql, Object...args) {
        try {
            Connection conn = JdbcUtils.getConnection();
            // BUG：Scalar 需要使用 Number类型才能强转， Long类型或许也可以
            // 应该是Number类，这个类是所有包装类的父类，是一个抽象类
            // 注意用 Long 类型也可以，可能是因为 查询出来的 本就是 long，强转为 int 会直接报错
            // 注意 强转 要么用 Number 要么用 Long ，用 Integer 或者 int 都会直接报错！
            return (Number) queryRunner.query(conn, sql, new ScalarHandler(), args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
