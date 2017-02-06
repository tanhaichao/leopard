package io.leopard.web.view;

import io.leopard.json.Json;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * webservice视图.
 * 
 * @author 阿海
 * 
 */
public class WsView extends AbstractView {

	private String status;
	private String message;
	private Object data;

	// public WsView() {
	// this("success");
	// }

	public WsView(Object data) {
		this("200", data);
	}

	public WsView(String status, Object data) {
		this(status, "", data);
	}

	public WsView(String status, String message, Object data) {
		// super(toJson(status, message, data));
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 返回结果.
	 * 
	 * @return
	 */
	protected Map<String, Object> getResult() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("status", status);
		map.put("message", message);
		if (data != null) {
			map.put("clazz", data.getClass().getName());
			map.put("data", Json.toJson(data));
		}
		return map;
	}

	@Override
	public String getContentType() {
		return "text/plain; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = this.getResult();
		return Json.toJson(map);
	}

}
