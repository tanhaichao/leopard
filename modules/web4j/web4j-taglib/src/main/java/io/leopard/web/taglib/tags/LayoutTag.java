package io.leopard.web.taglib.tags;

import io.leopard.web.taglib.TagUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class LayoutTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"page\">");

		TagUtil.write(pageContext, sb.toString());
		// try {
		// pageContext.getOut().write(sb.toString());
		// }
		// catch (IOException e) {
		// throw new JspException(e.getMessage(), e);
		// }
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append("</div>");
		TagUtil.write(pageContext, sb.toString());
		// try {
		// pageContext.getOut().write(sb.toString());
		// }
		// catch (IOException e) {
		// throw new JspException(e.getMessage(), e);
		// }
		return SKIP_BODY;
	}
}
