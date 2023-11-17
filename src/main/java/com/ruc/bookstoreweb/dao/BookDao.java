package com.ruc.bookstoreweb.dao;

import com.ruc.bookstoreweb.pojo.Book;

import java.util.List;

/**
 * @Author 3590
 * @Date 2023/11/12 20:39
 * @Description
 * @Version
 */
public interface BookDao {
    public int addBook(Book book);
    public int deleteBookById(Integer id);
    /**
     * @return 对某个 Book 记录进行更新，更新的行数，如果更新成功，则返回1
     * */
    public int updateBook(Book book);
    public Book queryBookById(Integer id);
    public List<Book> queryBooks();

    public int queryForPageTotalCount();
    public List<Book> queryPageItems(int begin, int size);

    int queryForPageTotalCount(Integer minVal, Integer maxVal);

    List<Book> queryPageItems(int begin, int size, Integer minVal, Integer maxVal);
}
