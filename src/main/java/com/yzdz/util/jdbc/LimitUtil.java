package com.yzdz.util.jdbc;

/**
 * 一个用于分页的工具类
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/15
 */
public class LimitUtil {
    /**
     * 当前页码,来自用户输入
     */
    private long currentPageNo = 1;

    /**
     * 总数量（表）
     */
    private long totalCount = 0;

    /**
     * 页面容量
     */
    private int pageSize = 0;

    /**
     * 总页数-totalCount/pageSize（+1）
     */
    private long totalPageCount = 1;

    public long getCurrentPageNo() {
        return currentPageNo;
    }

    /**
     * 用于设置当前页码
     * 用户设置,不能小于0
     *
     * @param currentPageNo 页码
     */
    public void setCurrentPageNo(long currentPageNo) {
        if (currentPageNo > 0) {
            this.currentPageNo = currentPageNo;
        }
    }

    /**
     * @return 返回总数量
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount 设置总页数
     */
    public void setTotalCount(long totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            //设置总页数
            this.setTotalPageCountByRs();
        }
    }

    /**
     * @return 返回页面容量
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize 设置页面容量
     */
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    /**
     * @return 返回总页数 (总页数-totalCount/pageSize（+1）)
     */
    public long getTotalPageCount() {
        return totalPageCount;
    }

    /**
     * @param totalPageCount 设置总页数 (总页数-totalCount/pageSize（+1）)
     */
    public void setTotalPageCount(long totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    /**
     * 用于设置参设?
     */
    public void setTotalPageCountByRs() {
        if (this.totalCount % this.pageSize == 0) {
            this.totalPageCount = this.totalCount / this.pageSize;
        } else if (this.totalCount % this.pageSize > 0) {
            this.totalPageCount = this.totalCount / this.pageSize + 1;
        } else {
            this.totalPageCount = 0;
        }
    }
}