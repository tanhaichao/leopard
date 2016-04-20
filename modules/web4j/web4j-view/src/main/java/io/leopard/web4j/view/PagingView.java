package io.leopard.web4j.view;

import org.springframework.web.servlet.ModelAndView;

/**
 * 分页视图.
 * 
 * @author 阿海
 * 
 */
public class PagingView extends ModelAndView {

	private final int start;
	private int pageSize;
	private final int pageId;
	private int totalCount;

	public PagingView(String viewName, int pageId, int pageSize) {
		super(viewName);
		this.pageSize = pageSize;
		this.start = PagingUtil.getPageStart(pageId, pageSize);

		this.pageId = pageId;
		this.pageSize = pageSize;
		this.addObject("pageId", pageId);
		this.addObject("pageSize", pageSize);
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.addObject("totalCount", totalCount);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageId() {
		return pageId;
	}

	public int getStart() {
		return start;
	}

	public int getPageSize() {
		return pageSize;
	}

}
