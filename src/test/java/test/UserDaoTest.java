package test;

import com.ruc.bookstoreweb.dao.UserDao;
import com.ruc.bookstoreweb.dao.impl.UserDaoImpl;
import com.ruc.bookstoreweb.pojo.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 3590
 * @Date 2023/11/5 23:35
 * @Description
 * @Version
 */
class UserDaoTest {
    UserDao userDao = new UserDaoImpl();
    @Test
    void queryUserByUsername() {
        System.out.println(userDao.queryUserByUsername("admin"));
    }

    @Test
    void verifyLogin() {
        System.out.println(userDao.verifyLogin("admin", "admin"));
    }

    @Test
    void saveUser() {
        User user = new User(null, "caixukun", "123456", "caixukun@168.com");
        // 下面的语句会失败，因为admin是唯一性约束
        // User user = new User(null, "admin", "123456", "caixukun@168.com");
        System.out.println(userDao.saveUser(user));
    }
}