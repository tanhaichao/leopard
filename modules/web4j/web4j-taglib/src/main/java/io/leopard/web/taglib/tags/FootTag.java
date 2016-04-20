package io.leopard.web.taglib.tags;

public class FootTag extends AbstractStartTagSupport {

	private static final long serialVersionUID = 1L;

	// @Override
	// public int doStartTag() throws JspException {
	// StringBuffer sb = new StringBuffer();
	// sb.append("<div class=\"pft\">");
	// sb.append("<a href=\"#\" title=\"关于多玩\">关于多玩</a>|");
	// sb.append("<a href=\"#\" title=\"多玩招聘\">多玩招聘</a>|");
	// sb.append("<a href=\"#\" title=\"联系多玩\">联系多玩</a>|");
	// sb.append("<span>广告热线：020-85521516</span>|");
	// sb.append("<span>客服热线：020-22826770</span>");
	// sb.append("<p>Copyright © 2005-2010  DuoWan.com [多玩游戏] ICP证编号：");
	// sb.append("<a href=\"http://www.miibeian.gov.cn/\" target=\"_blank\">粤B2-20050785</a></p>");
	// sb.append("</div>");
	// try {
	// pageContext.getOut().write(sb.toString());
	// }
	// catch (Exception e) {
	// e.printStackTrace();
	// }
	// return SKIP_BODY;
	// }

	@Override
	protected String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"pft\">");
		sb.append("<a href=\"#\" title=\"关于多玩\">关于多玩</a>|");
		sb.append("<a href=\"#\" title=\"多玩招聘\">多玩招聘</a>|");
		sb.append("<a href=\"#\" title=\"联系多玩\">联系多玩</a>|");
		sb.append("<span>广告热线：020-85521516</span>|");
		sb.append("<span>客服热线：020-22826770</span>");
		sb.append("<p>Copyright © 2005-2010  DuoWan.com [多玩游戏] ICP证编号：");
		sb.append("<a href=\"http://www.miibeian.gov.cn/\" target=\"_blank\">粤B2-20050785</a></p>");
		sb.append("</div>");
		return sb.toString();
	}

}
