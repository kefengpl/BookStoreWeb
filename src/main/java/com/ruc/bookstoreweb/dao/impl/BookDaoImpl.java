package com.ruc.bookstoreweb.dao.impl;

import com.ruc.bookstoreweb.dao.BaseDao;
import com.ruc.bookstoreweb.dao.BookDao;
import com.ruc.bookstoreweb.pojo.Book;

import java.util.List;

/**
 * @Author 3590
 * @Date 2023/11/12 20:43
 * @Description BookDao 的实现类
 * @Version
 */
public class BookDaoImpl extends BaseDao implements BookDao {

    @Override
    public int addBook(Book book) {
        String sql = "insert into t_book (name, price, author, sales, stock, img_path) " +
                "values(?, ?, ?, ?, ?, ?)";
        return update(sql, book.getName(), book.getPrice(), book.getAuthor(),
                book.getSales(), book.getStock(), book.getImgPath());
    }

    @Override
    public int deleteBookById(Integer id) {
        String sql = "delete from t_book where id = ?";
        return update(sql, id);
    }

    @Override
    public int updateBook(Book book) {
        String sql = "update t_book set name = ? , price = ?, author = ? , sales = ? , stock = ? , img_path = ? where id = ?";
        return update(sql, book.getName(), book.getPrice(), book.getAuthor(),
                book.getSales(), book.getStock(), book.getImgPath(), book.getId());
    }

    @Override
    public Book queryBookById(Integer id) {
        // 为什么要这么写？详见 JDBCStudy中的BaseDao源码
        String sql = "select id, name, price, author, sales, stock, img_path imgPath from t_book " +
                "where id = ?";
        return queryForOne(Book.class, sql, id);
    }

    @Override
    public List<Book> queryBooks() {
        String sql = "select id, name, price, author, sales, stock, img_path imgPath from t_book";
        return queryForList(Book.class, sql);
    }

    @Override
    public int queryForPageTotalCount() {
        String sql = "select count(id) from t_book";
        return ((Number) queryForSingleValue(sql)).intValue();
    }

    @Override
    public List<Book> queryPageItems(int begin, int size) {
        // 第一个占位符是 begin ， 第二个占位符是 size， 值得注意的是：begin 的起始是 0
        String sql = "select id, name, price, author, sales, stock, img_path imgPath from t_book limit ?, ? ";
        return queryForList(Book.class, sql, begin, size);
    }
}
