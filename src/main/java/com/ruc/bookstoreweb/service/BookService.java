package com.ruc.bookstoreweb.service;

import com.ruc.bookstoreweb.pojo.Book;
import com.ruc.bookstoreweb.pojo.Page;

import java.util.List;

/**
 * @Author 3590
 * @Date 2023/11/12 21:06
 * @Description
 * @Version
 */
public interface BookService {
    public void addBook(Book book);
    public void deleteBookById(Integer id);
    public void updateBook(Book book);
    public Book queryBookById(Integer id);
    public List<Book> queryBooks();

    public Integer getPageTotalCount();

    public Integer getPageTotal(int pageSize);

    public Page<Book> page(int pageNo, int pageSize);

    Page<Book> pageByPrice(int pageNo, int pageSize, Integer minVal, Integer maxVal);
}
