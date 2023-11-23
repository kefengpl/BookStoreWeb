package test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author 3590
 * @Date 2023/11/20 23:24
 * @Description
 * @Version
 */
public class ThreadLocalTest {
    // 这是线程安全的容器
    public static Map<String, Object> data = new ConcurrentHashMap<>();
    private static Random random = new Random();
    // 它类似于楼上线程安全的容器
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();
    // Runnable 是 java 的线程
    public static class Task implements Runnable {
        @Override
        public void run() {
            // 在 run 方法中，随机生成一个变量(线程要关联的数据)，然后以当前线程名为 key 保存到 map 中
            Integer i = random.nextInt(1000); // 范围是 0 ~ 999
            // 获取当期线程名
            String name = Thread.currentThread().getName();
            System.out.println("线程[" + name + "]生成的随机数是：" + i);
            data.put(name, i); // 将 name 和 value 放入集合类
            // 尽管我们只有一个 threadLocal ，但是可以被多个线程使用，一个线程可以存放一个数据对象
            threadLocal.set(i); // 将 value 放入 threadLocal 对象
            try {
                Thread.sleep(1234);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 这个线程里面可以 new 一些数据元素，无论代码层级，我都可以使用 data.get(name) 取出数据，name 就是 线程名
            // 过滤器默认一条线程，用线程名存储数据，来保证数据安全就是这个意思
            // 在 run 方法结束之前，以当前线程名获取出数据并打印，查看是否可以取出操作
            Object o = data.get(name);
            Object oLocal = threadLocal.get();
            System.out.println("在线程[" + name + "快结束时，取出的关联数据是：" + oLocal);
        }
    }

    public static void main(String[] args) {
        // 创建几个线程
        for (int i = 0; i < 3; ++i) {
            (new Thread(new Task())).start();
        }
    }
}
