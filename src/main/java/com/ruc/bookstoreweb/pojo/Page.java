package com.ruc.bookstoreweb.pojo;

import java.util.List;

/**
 * @Author 3590
 * @Date 2023/11/13 23:10
 * @Description Page 是分页的模型对象，<E>是具体模块的 JavaBean类
 *              知识补充：一些约定： T 表示类型泛型， E 表示集合类的元素泛型
 * @Version
 */
public class Page <E> {
    public static final Integer PAGE_SIZE = 4;
    public static final Integer TABLE_PAGE_SIZE = 15;
    private Integer pageNo;

    private Integer pageTotal;
    private Integer pageSize = PAGE_SIZE;
    // 表示该数据表一共有多少条记录
    private Integer pageTotalCount;
    private String url;

    // 当前页数据，加泛型
    private List<E> items;

    public Page(Integer pageNo, Integer pageTotal, Integer pageSize, Integer pageTotalCount, List<E> items, String url) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.pageSize = pageSize;
        this.pageTotalCount = pageTotalCount;
        this.items = items;
        this.url = url;
    }

    public Page() {
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public List<E> getItems() {
        return items;
    }

    public String getUrl() { return url; }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public void setUrl(String url) { this.url = url; }

    public void setItems(List<E> items) {
        this.items = items;
    }
}
