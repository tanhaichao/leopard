package io.leopard.web.taglib.tags;

import io.leopard.web.taglib.TagUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * 后台左侧菜单.
 * 
 * @author 阿海
 * 
 */
public class MenuTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String base;
	private String className;
	private String selectedClass;

	public void setBase(String base) {
		this.base = base;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setSelectedClass(String selectedClass) {
		this.selectedClass = selectedClass;
	}

	private boolean isSelected(String url, String currentUri) {
		// System.out.println("url:" + url + " currentUri:" + currentUri);
		return url.equals(currentUri);
	}

	protected String getHtml(String body) {
		String currentUri = TagUtil.getCurrentUri(pageContext);

		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"").append(this.className).append("\">");
		String regex = "<a href=\"(.*?)\">(.*?)</a>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(body);
		while (m.find()) {
			String href = m.group(1);
			String text = m.group(2);
			String url = StringUtils.defaultString(this.base) + href;
			sb.append("<li");
			if (this.selectedClass != null && this.isSelected(url, currentUri)) {
				sb.append(" class=\"").append(this.selectedClass).append("\"");
			}
			sb.append("><a href=\"").append(url).append("\"");
			sb.append(">");
			sb.append(text);
			sb.append("</a></li>\n");
		}
		// sb.append(body);
		sb.append("</ul>");
		return sb.toString();
	}

	public int doAfterBody() throws JspException {
		BodyContent bodyContent = getBodyContent();
		JspWriter out = bodyContent.getEnclosingWriter();
		String body = bodyContent.getString();
		String html = this.getHtml(body);
		TagUtil.write(out, html);
		// try {
		// out.print(this.getHtml(body));
		// }
		// catch (IOException e) {
		// throw new JspException(e.getMessage(), e);
		// }
		return SKIP_BODY;
	}

}