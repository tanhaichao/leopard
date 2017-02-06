package io.leopard.web.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 普通文本.
 * 
 * @author 阿海
 * 
 */
public class TextView extends AbstractView {

	private String message;

	public TextView(final String message) {
		// this.addObject("message", data);
		this.message = message;
	}

	public String getMessage() {
		// return (String) this.getModel().get("message");
		return this.message;
	}

	@Override
	public String getContentType() {
		return "text/plain; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		String message = this.getMessage();
		return message;
	}
}
