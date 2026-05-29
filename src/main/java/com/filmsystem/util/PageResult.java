package com.filmsystem.util;

import java.util.List;

public class PageResult<T> {
    private List<T> records;
    private long total;
    private int page;
    private int pageSize;
    private int totalPages;

    public PageResult(List<T> records, long total, int page, int pageSize) {
        this.records = records;
        this.total = total;
        this.page = page < 1 ? 1 : page;
        this.pageSize = pageSize < 1 ? 10 : pageSize;
        this.totalPages = (int) Math.ceil(total * 1.0 / this.pageSize);
    }

    public List<T> getRecords() {
        return records;
    }

    public long getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isHasPrevious() {
        return page > 1;
    }

    public boolean isHasNext() {
        return totalPages > 0 && page < totalPages;
    }
}
