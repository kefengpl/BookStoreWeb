package test;

import com.google.gson.Gson;
import com.ruc.bookstoreweb.pojo.Cart;
import com.ruc.bookstoreweb.pojo.CartItem;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 3590
 * @Date 2023/11/18 0:18
 * @Description
 * @Version
 */
class CartTest {

    @Test
    public void testGson() {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("name", "kefeng");
        map.put("password", String.valueOf(1212));
        String json = gson.toJson(map);
        System.out.println(json);
    }
    @Test
    void addItem() {

    }

    @Test
    void deleteItem() {
    }

    @Test
    void clear() {
    }

    @Test
    void updateCount() {
    }
}