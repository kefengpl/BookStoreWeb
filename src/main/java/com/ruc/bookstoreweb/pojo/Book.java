package com.ruc.bookstoreweb.pojo;

import java.math.BigDecimal;

/**
 * @Author 3590
 * @Date 2023/11/12 20:29
 * @Description 图书模块的 JavaBean
 *              一个疑惑：为何数据库表中只存储图片的路径，而不存放图片本身？答：如果直接在数据库中存
 *              图片，会使得数据库存取效率大幅降低，让数据库回归本身的指针作用不是更好吗？
 * @Version
 */
public class Book {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String author;
    private Integer sales;
    private Integer stock;
    // 注意：MySQL的数据表使用的下划线命名
    // 写 SQL 的时候需要 取 别名！
    private String imgPath = "/static/img/pwd-icons-new.png";

    public Book() {
    }

    public Book(Integer id, String name, BigDecimal price, String author, Integer sales, Integer stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.sales = sales;
        this.stock = stock;
        // 要求输入的路径不能是空指针，也不能是空字符串
        if (imgPath != null && !"".equals(imgPath)) {
            this.imgPath = imgPath;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getSales() {
        return sales;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setImgPath(String imgPath) {
        if (imgPath != null && !"".equals(imgPath)) {
            this.imgPath = imgPath;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                ", sales=" + sales +
                ", stock=" + stock +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
