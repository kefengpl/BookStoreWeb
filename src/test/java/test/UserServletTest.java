package test;
import com.ruc.bookstoreweb.pojo.User;
import com.ruc.bookstoreweb.web.UserServlet;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author 3590
 * @Date 2023/11/12 18:49
 * @Description
 * @Version
 */
public class UserServletTest {
    /**
     * 通过反射构造类，反射回顾
     * 注意：在main方法 static中，可以在类内部调用该类的 clazz.getDeclaredMethod("login");
     * */
    @Test
    public void test() throws Exception {
        // 1. 读取在JVM内存中的类字节码文件
        Class clazz = Class.forName("com.ruc.bookstoreweb.web.UserServlet");
        // 2. 构造该类的对象
        UserServlet userServlet = (UserServlet) clazz.getDeclaredConstructor().newInstance();
        // 3. 调用该类的方法
        Method method = clazz.getDeclaredMethod("login");
        method.setAccessible(true);
        // method.invoke(userServlet, request, response);
    }
}
