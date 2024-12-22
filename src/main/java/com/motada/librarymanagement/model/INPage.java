package com.motada.librarymanagement.model;


public class INPage {

    private long size;
    private long totalElements;
    private long totalPages;
    private long pageNumber;
    private String sortDirection;
    private String sortColumn;

    public INPage() {
    }

    public INPage(long var1, long var3, long var5, long var7) {
        this.size = var1;
        this.totalElements = var3;
        this.totalPages = var5;
        this.pageNumber = var7;
    }

    public final long getSize() {
        return this.size;
    }

    public final void setSize(long var1) {
        this.size = var1;
    }

    public final long getTotalElements() {
        return this.totalElements;
    }

    public final void setTotalElements(long var1) {
        this.totalElements = var1;
    }

    public final long getTotalPages() {
        return this.totalPages;
    }

    public final void setTotalPages(long var1) {
        this.totalPages = var1;
    }

    public final long getPageNumber() {
        return this.pageNumber;
    }

    public final void setPageNumber(long var1) {
        this.pageNumber = var1;
    }

    public final String getSortDirection() {
        return this.sortDirection;
    }

    public final void setSortDirection(String var1) {
        this.sortDirection = var1;
    }

    public final String getSortColumn() {
        return this.sortColumn;
    }

    public final void setSortColumn(String var1) {
        this.sortColumn = var1;
    }

    public String toString() {
        return "INPage [size=" + this.size + ", totalElements=" + this.totalElements + ", totalPages=" + this.totalPages + ", pageNumber=" + this.pageNumber + "]";
    }
}
