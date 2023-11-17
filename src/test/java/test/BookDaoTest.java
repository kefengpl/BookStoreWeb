package test;

import com.ruc.bookstoreweb.dao.BookDao;
import com.ruc.bookstoreweb.dao.impl.BookDaoImpl;
import com.ruc.bookstoreweb.pojo.Book;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 3590
 * @Date 2023/11/12 20:59
 * @Description
 * @Version
 */
class BookDaoTest {
    private BookDao bookDao = new BookDaoImpl();
    @Test
    void addBook() {
        bookDao.addBook(new Book(null, "崇祯年代", new BigDecimal(25.8), "利达", 1100, 10, null));
    }

    @Test
    void deleteBookById() {
    }

    @Test
    void updateBook() {
        bookDao.updateBook(new Book(21, "崇祯年代封禁版", new BigDecimal(1125.83), "利达", 1100, 10, null));
    }

    @Test
    void queryBookById() {
        System.out.println(bookDao.queryBookById(20));
    }

    @Test
    void queryBooks() {
        for (Book book : bookDao.queryBooks()) {
            System.out.println(book);
        }
    }

    @Test
    void queryForPageTotalCountTest() {
        bookDao.queryForPageTotalCount();
    }

    @Test
    public void testQueryPageItems() {
        System.out.println(bookDao.queryPageItems(0, 4, 0, 1000));
    }
}