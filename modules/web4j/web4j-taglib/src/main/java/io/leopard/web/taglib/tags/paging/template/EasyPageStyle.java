package io.leopard.web.taglib.tags.paging.template;

public class EasyPageStyle extends PageStyle {

	@Override
	public String getHtml(int pageId, int pageSize, int total, String url, boolean go, String align) {
		return this.createHtml(pageId, pageSize, total, url, go, align);
	}

	public String createHtml(int pageId, int pageSize, int total, String url, boolean go, String align) {
		StringBuffer sb = new StringBuffer();
		if (url.indexOf("?") != -1) {
			url = url.substring(url.indexOf("?")) + "&pageId=1";
		}
		else {
			url = "?pageId=1";
		}

		// 计算页数
		int totalPage = (total - 1) / pageSize + 1;
		// int totalPage = (total / pageSize);
		// if (total % pageSize != 0) {
		// totalPage += 1;
		// }
		this.writeCss(sb);
		sb.append(" <div class=\"cs-pager cs-clear\">");
		this.writeFirst(sb, url, pageId);
		this.writePrevious(sb, url, pageId);
		this.writeJump(sb, pageId, totalPage);
		this.writeNext(sb, url, totalPage, pageId);
		this.writeEnd(sb, url, pageId, totalPage);
		sb.append("</div>");
		return sb.toString();
	}

	private void writeJump(StringBuffer sb, int pageId, int totalPage) {
		sb.append("<p><span>第</span><input id=\"currPage\" name=\"currPage\" type=\"text\" value=\"" + pageId + "\"/><span>页/共</span><span>" + totalPage + "</span><span>页</span></p>");
	}

	private void writeEnd(StringBuffer sb, String url, int pageId, int totalPage) {
		String jumpUrl = this.getHref(url, totalPage);
		if (pageId != totalPage) {
			sb.append("<p onclick=\"location.href='" + jumpUrl + "'\" title=\"末页\">");
			sb.append("<img src=\"/img/page/easy/page_4.gif\" ");
			sb.append("onmouseover=\"javascript :src='/img/page/easy/page_4_1.gif';\" ");
			sb.append("onmouseout=\"javascript :src='/img/page/easy/page_4.gif';\" ");
			sb.append("onmousedown=\"javascript :src='/img/page/easy/page_4_2.gif';\" ");
			sb.append("/>");
			sb.append("</p>");
		}
		else {
			sb.append("<p title=\"末页\" onclick=\"" + jumpUrl + "\">");
			sb.append("<img src=\"/img/page/easy/page_4.gif\"/>");
			sb.append("</p>");
		}
	}

	private void writeFirst(StringBuffer sb, String url, int pageId) {
		String jumpUrl = this.getHref(url, 1);
		if (pageId > 1) {
			sb.append("<p onclick=\"location.href='" + jumpUrl + "'\" title=\"首页\">");
			sb.append("<img src=\"/img/page/easy/page_1.gif\" ");
			sb.append("onmouseover=\"javascript :src='/img/page/easy/page_1_1.gif';\" ");
			sb.append("onmouseout=\"javascript :src='/img/page/easy/page_1.gif';\" ");
			sb.append("onmousedown=\"javascript :src='/img/page/easy/page_1_2.gif';\" ");
			sb.append("/>");
			sb.append("</p>");
		}
		else {
			sb.append("<p title=\"首页\" onClick=\"" + jumpUrl + "\">");
			sb.append("<img src=\"/img/page/easy/page_1.gif\"/>");
			sb.append("</p>");

		}

	}

	/**
	 * 生成上一页
	 * 
	 * @param sb
	 */
	private void writePrevious(StringBuffer sb, String url, int pageId) {
		if (pageId > 1) {
			String jumpUrl = this.getHref(url, (pageId - 1));
			sb.append("<p onClick=\"location.href='" + jumpUrl + "'\" title=\"上一页\">");
			sb.append("<img src=\"/img/page/easy/page_2.gif\" ");
			sb.append("onmouseover=\"javascript :src='/img/page/easy/page_2_1.gif';\" ");
			sb.append("onmouseout=\"javascript :src='/img/page/easy/page_2.gif';\" ");
			sb.append("onmousedown=\"javascript :src='/img/page/easy/page_2_2.gif';\" ");
			sb.append("/>");
			sb.append("</p>");
		}
		else {
			sb.append("<p title=\"上一页\">");
			sb.append("<img src=\"/img/page/easy/page_2.gif\"/>");
			sb.append("</p>");
		}

	}

	private void writeNext(StringBuffer sb, String requestUrl, int p, int pageId) {
		if (pageId < p) {
			String jumpUrl = this.getHref(requestUrl, (pageId + 1));
			sb.append("<p onClick=\"location.href='" + jumpUrl + "'\" title=\"下一页\">");
			sb.append("<img src=\"/img/page/easy/page_3.gif\" ");
			sb.append("onmouseover=\"javascript :src='/img/page/easy/page_3_1.gif';\" ");
			sb.append("onmouseout=\"javascript :src='/img/page/easy/page_3.gif';\" ");
			sb.append("onmousedown=\"javascript :src='/img/page/easy/page_3_2.gif';\" ");
			sb.append("/>");
			sb.append("</p>");
		}
		else {
			sb.append("<p title=\"下一页\">");
			sb.append("<img src=\"/img/page/easy/page_3.gif\"/>");
			sb.append("</p>");
		}
	}

	private void writeCss(StringBuffer sb) {
		sb.append("<style>");
		/* cs-pager */
		sb.append(".cs-pager{text-align:center; float:right; font-size:12px}");
		sb.append(".cs-pager-hd{width:223px}");
		sb.append(".cs-pager p{color:#808080; height:24px;line-height:24px; *margin-top:1px;  display:inline-block; float:left}");
		sb.append(".cs-pager p img{margin-top:2px; *margin-top:1px;}");
		sb.append(".cs-pager .next{padding:0 10px;}");
		sb.append(".cs-pager span{height:24px; line-height:24px;float:left}");
		sb.append(".cs-pager input{width:40px; height:24px; line-height:24px; text-align:center; float:left;border:0; margin:0 2px; background:url(/img/page/easy/pager.gif) no-repeat}");
		sb.append("</style>");
	}

}
