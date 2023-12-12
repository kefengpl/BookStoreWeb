package com.ruc.bookstoreweb.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/19 22:25
 * @Description
 * @Version
 */
public class Order {
    /**
     *  0 表示未发货，1表示已发货，2表示已签收
     * */
    public static Map<Integer, String> statusMap = new HashMap<>();

    static {
        statusMap.put(0, "未发货");
        statusMap.put(1, "已发货");
        statusMap.put(2, "已签收");
    }

    private String orderId;
    private Date createTime;
    private BigDecimal price;
    private Integer status = 0;
    private Integer userId;


    public Order(String orderId, Date createTime, BigDecimal price, Integer status, Integer userId) {
        this.orderId = orderId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.userId = userId;
    }


    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}
