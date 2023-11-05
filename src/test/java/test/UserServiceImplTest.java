package test;

import com.ruc.bookstoreweb.pojo.User;
import com.ruc.bookstoreweb.service.UserService;
import com.ruc.bookstoreweb.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 3590
 * @Date 2023/11/5 23:50
 * @Description
 * @Version
 */
class UserServiceImplTest {
    UserService userService = new UserServiceImpl();
    @Test
    void registUser() {
        userService.registUser(new User(null, "kefeng", "128484", "57407@gmail.com"));
        userService.registUser(new User(null, "zhangxuefeng", "71913fdf3", "4115181321@csc.com"));
    }

    @Test
    void login() {
        // 注意：这里的 id 是没有作用的！ 写 null 或者具体数字皆可
        System.out.println(userService.login(new User(10, "kefng", "128484", "57407@gmail.com")));
        System.out.println(userService.login(new User(null, "kefeng", "128484", "57407@gmail.com")));
    }

    @Test
    void checkExistUsername() {
        System.out.println(userService.checkExistUsername("kefeng"));
    }
}