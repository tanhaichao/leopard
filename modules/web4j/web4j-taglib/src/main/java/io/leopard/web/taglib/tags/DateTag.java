package io.leopard.web.taglib.tags;

public class DateTag extends AbstractStartTagSupport {

	private static final long serialVersionUID = 1L;

	protected String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<input type=\"text\" name=\"" + this.name + "\" id=\"" + this.name + "\" ");
		if (this.className != null) {
			sb.append(" class=\"" + this.className + "\"");
		}
		sb.append("onfocus=\"WdatePicker({dateFmt:'" + this.format + "'");
		if (this.onchange != null) {
			sb.append(",onpicked:" + this.onchange + " ");
		}
		sb.append("})\" ");
		sb.append("value=\'" + this.value + "\'");
		sb.append("/>");
		return sb.toString();
	}

	// @Override
	// public int doStartTag() throws JspException {
	// String content = this.getContent();
	// try {
	// pageContext.getOut().write(content);
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// return SKIP_BODY;
	// }

	// css名称
	private String className;

	// 名称,ID
	private String name;

	// 格式
	private String format = "yyyy-MM-dd";

	// 值
	private String value;

	private String onchange;

	public void setName(String name) {
		this.name = name;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

}
