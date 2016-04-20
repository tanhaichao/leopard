package io.leopard.web.taglib.tags;

public class HeadTag extends AbstractStartTagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"phd\">");
		sb.append("<div class=\"con\">");
		sb.append("<h1 class=\"logo\">");
		sb.append("<a href=\"" + this.href + "\" title=\"" + this.title + "\">");
		sb.append(this.title);
		sb.append("</a></h1>");
		sb.append("<div class=\"userbar\">");
		sb.append("用户:<a href=\"#\" class=\"username\">" + this.username + "</a>");
		sb.append("角色：<strong>" + this.role + "</strong>|");
		sb.append("<a class=\"userexit\" href=\"#\">退出</a>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}

	private String title;

	private String href;

	private String username;

	private String role;

	public void setHref(String href) {
		this.href = href;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
