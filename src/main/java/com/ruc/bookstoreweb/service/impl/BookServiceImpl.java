package com.ruc.bookstoreweb.service.impl;

import com.ruc.bookstoreweb.dao.BookDao;
import com.ruc.bookstoreweb.dao.impl.BookDaoImpl;
import com.ruc.bookstoreweb.pojo.Book;
import com.ruc.bookstoreweb.pojo.Page;
import com.ruc.bookstoreweb.service.BookService;

import java.util.List;

/**
 * @Author 3590
 * @Date 2023/11/12 21:09
 * @Description
 * @Version
 */
public class BookServiceImpl implements BookService {
    BookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        // 你需要把 page 的所有非静态属性填满
        Page<Book> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setPageTotalCount(bookDao.queryForPageTotalCount());
        page.setPageTotal(page.getPageTotalCount() / page.getPageSize() +
                (page.getPageTotalCount() % page.getPageSize() == 0 ? 0  : 1));
        page.setItems(bookDao.queryPageItems((pageNo - 1) * pageSize, pageSize));
        return page;
    }
}
