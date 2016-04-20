package io.leopard.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class TagUtil {

	/**
	 * 从PageContext获取URI.
	 * 
	 * @param pageContext
	 * @return
	 */
	public static String getCurrentUri(PageContext pageContext) {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String uri = request.getRequestURI();
		uri = uri.replace("/WEB-INF/jsp", "");
		uri = uri.replace(".jsp", ".do");
		return uri;
	}

	public static void write(JspWriter out, String str) throws JspException {
		try {
			out.write(str);
		}
		catch (IOException e) {
			throw new JspException(e.getMessage(), e);
		}
	}

	public static void write(PageContext pageContext, String str) throws JspException {
		write(pageContext.getOut(), str);
	}
}
