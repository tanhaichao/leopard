package io.leopard.web.taglib.tags;

import io.leopard.web.taglib.TagUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

public class SelectTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	protected String getHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<select data-placeholder=\"" + this.title + "\" ");
		sb.append("class=\"chzn-select\" ");
		if (StringUtils.isNotEmpty(style)) {
			sb.append("style=\"" + this.style + "\" ");
		}
		if (StringUtils.isNotEmpty(this.tabindex)) {
			// if (this.tabindex != null && !"".equals(this.tabindex)) {
			sb.append("tabindex=\"" + this.tabindex + "\" ");
		}
		if (this.multiple) {
			sb.append("multiple ");
		}
		sb.append(">");
		return sb.toString();
	}

	@Override
	public int doStartTag() throws JspException {
		String content = this.getHtml();
		TagUtil.write(pageContext, content);
		// try {
		// pageContext.getOut().write(content);
		// }
		// catch (IOException e) {
		// throw new JspException(e.getMessage(), e);
		// }
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append("</select>");
		// try {
		// pageContext.getOut().write(sb.toString());
		// }
		// catch (IOException e) {
		// throw new JspException(e.getMessage(), e);
		// }
		TagUtil.write(pageContext, sb.toString());
		return SKIP_BODY;
	}

	private String style;

	private String tabindex;

	private boolean multiple;

	private String title;

	// public String getStyle() {
	// return style;
	// }

	public void setStyle(String style) {
		this.style = style;
	}

	// public String getTabindex() {
	// return tabindex;
	// }

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	// public boolean isMultiple() {
	// return multiple;
	// }

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	// public String getTitle() {
	// return title;
	// }

	public void setTitle(String title) {
		this.title = title;
	}

}
