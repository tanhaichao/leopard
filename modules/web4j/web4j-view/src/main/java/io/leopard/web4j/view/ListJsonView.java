package io.leopard.web4j.view;

import java.util.LinkedHashMap;
import java.util.Map;

public class ListJsonView extends JsonView {

	private boolean next;

	public ListJsonView(Object data, boolean next) {
		super(data);
		this.next = next;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
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
		map.put("next", this.isNext());
		map.put("data", this.getData());

		return map;
	}
}
