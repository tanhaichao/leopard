package io.leopard.web.view;

import io.leopard.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * json视图.
 * 
 * @author 阿海
 * 
 */
public class OnlyJsonView extends AbstractView {

	
	private final Object data;
	private final boolean format;

	public OnlyJsonView(Object data) {
		this(data, false);
	}

	public OnlyJsonView(Object data, boolean format) {
		this.data = data;
		this.format = format;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String getContentType() {
		return "text/plain; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		// boolean format = "true".equals(request.getParameter("format"));
		if (this.format || "true".equals(request.getParameter("format"))) {
			return Json.toFormatJson(data);
		}
		else {
			return Json.toJson(data);
		}
	}
}
