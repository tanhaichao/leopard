package io.leopard.web.taglib.tags;

import io.leopard.web.taglib.TagUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class AbstractStartTagSupport extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		String content = this.getContent();
		TagUtil.write(pageContext, content);
		// try {
		// pageContext.getOut().write(content);
		// }
		// catch (IOException e) {
		// throw new JspException(e.getMessage(), e);
		// }
		return SKIP_BODY;
	}

	protected abstract String getContent();
}
