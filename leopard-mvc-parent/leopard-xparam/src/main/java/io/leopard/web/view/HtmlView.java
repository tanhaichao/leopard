package io.leopard.web.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消息(支持HTML).
 * 
 * @author 阿海
 * 
 */
public class HtmlView extends AbstractView {

	private String message;
	private boolean escapeXml;

	public HtmlView(String message) {
		this(message, false);
	}

	public HtmlView(String message, boolean escapeXml) {
		this.escapeXml = escapeXml;
		// this.addObject("message", message);
		this.message = message;
	}

	public String getMessage() {
		// return (String) this.getModel().get("message");
		return this.message;
	}

	@Override
	public String getContentType() {
		return "text/html; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		if (escapeXml) {
			return JstlFunctions.escape(this.getMessage());
		}
		else {
			return this.getMessage();
		}
	}
}