package com.server.backend.DTO;

import java.util.List;

public class PagedResponse<T> {
    private List<T> data;
    private int page;
    private int pageSize;
    private long totalCount;
    private int totalPages;

    public PagedResponse(List<T> data, int page, int pageSize, long totalCount) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = pageSize > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;
    }

    // Getters
    public List<T> getData() { return data; }
    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
    public long getTotalCount() { return totalCount; }
    public int getTotalPages() { return totalPages; }
}
