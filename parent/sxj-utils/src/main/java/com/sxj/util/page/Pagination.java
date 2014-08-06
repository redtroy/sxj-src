package com.sxj.util.page;

import java.io.Serializable;

/**
 * 翻页实现类
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = 8925597938748885418L;

    /**
     * 当前页，默认 = 1
     */
    private int curPageNo = 1;

    /**
     * 每页条数，默认 = 20
     */
    private int pageSize = 20;
    /**
     * 总条数
     */
    private int totalCount = 0;

    public int getCurPageNo() {
        if (curPageNo <= 0) {
            return 1;
        }
        if (curPageNo > getTotalPage()) {
            return getTotalPage();
        }
        return curPageNo;
    }

    public int getNextPageNo() {
        if (isLastPage()) {
            return getCurPageNo();
        }
        return getCurPageNo() + 1;
    }

    public int getPrePageNo() {
        if (isFirstPage()) {
            return getCurPageNo();
        }
        return getCurPageNo() - 1;
    }

    public int getPageSize() {
        if (pageSize < 0) {
            return 0;
        }
        return pageSize;
    }

    public int getTotalCount() {
        if (totalCount < 0) {
            return 0;
        }
        return totalCount;
    }

    public int getTotalPage() {

        if (getPageSize() == 0)
            return 0;
        int totalPage = getTotalCount() / getPageSize();
        if (getTotalCount() % getPageSize() != 0 || totalPage == 0) {
            return totalPage + 1;
        }
        return totalPage;
    }

    public boolean isFirstPage() {
        return getCurPageNo() <= 1;
    }

    public boolean isLastPage() {
        return getCurPageNo() >= getTotalPage();
    }

    public void setCurPageNo(int curPageNo) {
        this.curPageNo = curPageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStartNo() {
        return ((getCurPageNo() - 1) * getPageSize());
    }

    public int getEndNo() {
        return getStartNo() + getPageSize();
    }

}
