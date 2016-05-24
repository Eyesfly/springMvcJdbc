package com.bjrxht.utils;

import java.io.Serializable;
import java.util.List;

public class PageBean implements Serializable {
	/** 总记录条数 */
	private long total;
	/** 总记录页数. */
	private long totalPages;
	/** 每页显示的条数 */
	private long pageSize;
	/** 当前页的页数 */
	private long currentPageNo;
	/** 上一页的页数 */
	private long previousPageNo;
	/** 下一页的页数 */
	private long nextPageNo;
	/** 是否是首页 */
	private boolean isFirstPage;
	/** 是否是末页 */
	private boolean isLastPage;
	/** 是否有上一页 */
	private boolean hasPreviousPage;
	/** 是否有下一页 */
	private boolean hasNextPage;
	/** 当前页的结果集数据 */
	private List<?> rows = null;

	/**
	 * 分页类的一个构造方法 入口参数：总的记录条数、页面显示容量
	 * */
	public PageBean(long totalRecords, int pageSize) {
		// 设置总记录条数
		if (totalRecords >= 0) {
			this.total = totalRecords;
		} else {
			totalRecords = 0;
		}
		// 设置每页的记录条数
		setPageSize(pageSize);
		// 设置总页数
		if (totalRecords % pageSize == 0) {
			totalPages = totalRecords / pageSize;
		} else {
			totalPages = (totalRecords / pageSize) + 1;
		}
		// 设置当前页
		currentPageNo = 1;

	}

	/** 获得当前页数 */
	public long getCurrentPageNo() {
		return currentPageNo;
	}

	/** 设置当前页数 */
	public void setCurrentPageNo(int currentPageNo) {

		if (currentPageNo < 1) {
			this.currentPageNo = 1;
		} else if (currentPageNo > totalPages) {
			if(currentPageNo == 1){
				this.currentPageNo = 1;
			} else {
				this.currentPageNo = totalPages;
			}
		} else {
			this.currentPageNo = currentPageNo;
		}
		// 每次设置当前页时更新上一页、下一页、是否首页、是否末页、是否有上一页、是否有下一页标志
		isFirstPage = (currentPageNo == 1) ? true : false;
		isLastPage = (currentPageNo == totalPages) ? true : false;
		hasPreviousPage = (currentPageNo == 1) ? false : true;
		hasNextPage = (currentPageNo == totalPages) ? false : true;
		previousPageNo = (hasPreviousPage) ? (currentPageNo - 1)
				: currentPageNo;
		nextPageNo = (hasNextPage) ? (currentPageNo + 1) : currentPageNo;
	}

	/** 获得当前页数的容量 */
	public long getCurrentPageSize() {
		if (total == 0) {
			return 0;
		} else if (currentPageNo < totalPages) {
			// 不是末页
			return pageSize;
		} else {
			// 是末页
			return (total - (currentPageNo - 1) * pageSize);
		}
	}

	/** 获得当前页的开始记录数 */
	public long getCurrentPageStartRecord() {
		return (currentPageNo - 1) * pageSize;
	}

	/** 获得当前页的结束记录数 */
	public long getCurrentPageEndRecord() {
		return (currentPageNo - 1) * pageSize + getCurrentPageSize();
	}

	/** 设置页面显示的容量 */
	public void setPageSize(int pageSize) {
		this.pageSize = (pageSize <= 0) ? 10 : pageSize;
	}

	/** 是否有下一页 */
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	/** 是否有上一页 */
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	/** 是否是首页 */
	public boolean isFirstPage() {
		return isFirstPage;
	}

	/** 是否是末页 */
	public boolean isLastPage() {
		return isLastPage;
	}

	/** 获得页面显示的容量 */
	public long getPageSize() {
		return pageSize;
	}

	/** 获得前一页的页数 */
	public long getPreviousPageNo() {
		return previousPageNo;
	}

	/** 获得下一页的页数 */
	public long getNextPageNo() {
		return nextPageNo;
	}

	/** 获得总页数 */
	public long getTotalPages() {
		return totalPages;
	}

	/** 获得总记录条数 */
	public long getTotal() {

		return total;
	}

	/** 获得结果集数据 */
	public List<?> getRows() {
		return rows;
	}

	/** 设置结果集数据 */
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
