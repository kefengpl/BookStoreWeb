package com.ruc.bookstoreweb.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/18 0:01
 * @Description 购物车对象
 * @Version
 */
public class Cart {
    /**
     * key 是商品编号
     * val 是商品信息
     * */
    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    /*
    * 添加商品项
    * */
    public void addItem(CartItem cartItem) {
        if (cartItem == null) return;
        // items的查找方法：.get
        CartItem item = items.get(cartItem.getId());
        if (item == null) {
            // 之前没添加过商品
            items.put(cartItem.getId(), cartItem);
        } else {
            // 注意：由于是引用类型，所以相当于直接操作了 map 内部的元素
            // 数量 + 1，其它不变
            item.setCount(item.getCount() + 1); // 数量累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount()))); // 更新总金额
        }
    }

    /**
     * 删除商品项
     * */
    public void deleteItem(CartItem cartItem) {
        items.remove(cartItem.getId());
    }

    /**
     * 清空购物车
     * */
    public void clear() {
        items.clear();
    }

    /**
     * 修改商品数量
     * */
    public void updateCount(Integer id, Integer count) {
        // 先查看是否由此商品，如果有，则修改商品数量，更新总金额
        if(items.get(id) == null) return;
        CartItem item = items.get(id);
        item.setCount(count);
        item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
    }

    public Cart() {
    }



    public Cart(Integer totalCount, BigDecimal totalPrice, Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (var entry : items.entrySet()) {
            // 获取每个商品的数量
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (var entry : items.entrySet()) {
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
