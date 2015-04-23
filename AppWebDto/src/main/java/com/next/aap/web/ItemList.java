package com.next.aap.web;

import java.util.List;

public class ItemList<T> {

	private List<T> items;
	private long pageNumber;
	private long pageSize;
	private long totalPages;
	public ItemList(List<T> list){
		this.items = list;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public long getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

    @Override
    public String toString() {
        return "ItemList [items=" + items + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalPages=" + totalPages + "]";
    }

}
