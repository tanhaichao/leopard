package io.leopard.lang;

import java.util.ArrayList;
import java.util.List;

public class PagingImpl<E> implements Paging<E> {

	private List<E> list = new ArrayList<E>();

	private Boolean nextPage;// 是否有下一页
	private Integer totalCount;// 记录总数
	private Integer pageCount;// 总页数
	private Integer pageSize;// 一页显示的记录条数.

	public PagingImpl() {

	}

	public PagingImpl(int pageSize) {
		this.pageSize = pageSize;
	}

	public PagingImpl(Paging<?> paging) {
		if (paging.isNextPage() != null) {
			this.setNextPage(paging.isNextPage());
		}
		if (paging.getTotalCount() != null) {
			this.setTotalCount(paging.getTotalCount());
		}
		if (paging.getPageCount() != null) {
			this.setPageCount(paging.getPageCount());
		}
		if (paging.getPageSize() != null) {
			this.setPageSize(paging.getPageSize());
		}
	}

	public PagingImpl(List<?> list, int size) {
		boolean nextPage;
		if (list == null) {
			nextPage = false;
		}
		else {
			nextPage = list.size() >= size;
		}
		this.setNextPage(nextPage);

		this.setPageSize(size);

		this.list = new ArrayList<E>();
	}

	// public PagingImpl(List<E> list) {
	// this.list = list;
	// }
	//
	// public PagingImpl(List<E> list, int count) {
	// this.list = list;
	// this.totalCount = count;
	// }
	//
	// public PagingImpl(List<E> list, boolean nextPage) {
	// this.list = list;
	// this.nextPage = nextPage;
	// }

	@Override
	public List<E> getList() {
		return this.list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	@Override
	public void add(E element) {
		if (this.list == null) {
			this.list = new ArrayList<E>();
		}
		this.list.add(element);
	}

	@Override
	public void add(int index, E element) {
		if (this.list == null) {
			this.list = new ArrayList<E>();
		}
		this.list.add(index, element);
	}

	@Override
	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public Integer getPageCount() {
		if (pageCount == null && pageSize != null && totalCount != null) {
			// TODO ahai 代码未测试
			int diff = (totalCount % pageSize);
			int pageCount = totalCount / pageSize;
			if (diff > 0) {
				pageCount++;
			}
			return pageCount;
		}
		return this.pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public Boolean isNextPage() {
		if (nextPage == null && pageSize != null) {
			int size = (list == null) ? 0 : list.size();
			return (size >= this.pageSize);
		}
		return nextPage;
	}

	public void setNextPage(Boolean nextPage) {
		this.nextPage = nextPage;
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

}
