package io.leopard.web4j.view;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * json分页视图.
 * 
 * @author 谭海潮
 * 
 */
public class PagingJsonView extends JsonView {
	private int start;
	private int pageSize;
	private int pageCount;
	private int pageId;
	private int totalCount;

	// http://message.game.yy.com/test/pagingJson.do?pageId=3&callback=callback2
	// http://message.game.yy.com/test/pagingJson.do?pageId=3
	// http://message.game.yy.com/test/pagingJson.do?pageId=3&var=var1

	public PagingJsonView(int pageId, int pageSize) {
		this("200", pageId, pageSize);
	}

	public PagingJsonView(String status, int pageId, int pageSize) {
		super.setStatus(status);
		this.start = PagingUtil.getPageStart(pageId, pageSize);
		this.pageSize = pageSize;
		this.pageId = pageId;
	}

	public void setData(Object data, int totalCount) {
		this.setData(data);
		this.pageCount = (totalCount - 1) / pageSize + 1;
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 返回结果.
	 * 
	 * @return
	 */
	@Override
	protected Map<String, Object> getResult() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("status", this.getStatus());
		map.put("message", "");
		map.put("data", this.getData());

		map.put("pageCount", pageCount);
		map.put("pageSize", pageSize);
		return map;
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
