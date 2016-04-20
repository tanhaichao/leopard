package io.leopard.web.taglib.tags.paging.template;

public class WinPageStyle extends PageStyle {
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
		// sb.append("<input type='hidden' name='pageId' id = 'pageId' value='"+
		// this.pageId + "'/>");
		sb.append("<div class=\"Paging ");
		if ("left".equalsIgnoreCase(align)) {
			sb.append("cs-fl");
		}
		else {
			sb.append("cs-fr");
		}
		sb.append("\">");

		// 计算页数
		// int totalPage = (total / pageSize);
		int totalPage = (total - 1) / pageSize + 1;
		if (total % pageSize != 0) {
			totalPage += 1;
		}
		this.writePrevious(sb, url, pageId);
		// 写中间内容
		int pageNo[] = this.getPageNo(totalPage, pageId);
		for (int i = 0; i < pageNo.length; i++) {
			sb.append("<a href=\"" + this.getHref(url, pageNo[i]) + "\"");
			if (pageNo[i] == pageId) {
				sb.append(" class=\"on\"");
			}
			sb.append(">");
			sb.append(pageNo[i]);
			sb.append("</a>");
		}
		this.writeNext(sb, url, totalPage, pageId);
		if (go) {
			this.writeGo(sb, url);
		}
		sb.append("</div>");
		sb.append(this.writeScript());
		return sb.toString();
	}

	private int[] getPageNo(int totalPage, int pageId) {
		if (totalPage <= 10) {
			int pageNo[] = new int[totalPage];
			for (int i = 0; i < totalPage; i++) {
				pageNo[i] = i + 1;
			}
			return pageNo;
		}
		else if (totalPage - pageId <= 10) {
			int pageNo[] = { totalPage - 9, totalPage - 8, totalPage - 7, totalPage - 6, totalPage - 5, totalPage - 4, totalPage - 3, totalPage - 2, totalPage - 1, totalPage };
			return pageNo;
		}
		else {

			int k = 0;
			for (int i = 1; i <= totalPage; i++) {
				if (pageId - i == 0) {
					k = i;
					break;
				}
			}
			if (k > 5) {
				int pageNo[] = { pageId - 5, pageId - 4, pageId - 3, pageId - 2, pageId - 1, pageId, pageId + 1, pageId + 2, pageId + 3, pageId + 4 };
				return pageNo;
			}
			else {
				int pageNo[] = new int[10];
				for (int i = 1; i <= k; i++) {
					pageNo[i - 1] = pageId - (k - i);
				}
				for (int j = 1; j <= 10 - k; j++) {
					pageNo[k + j - 1] = pageId + j;
				}
				return pageNo;
			}
		}
	}

	/**
	 * 生成上一页
	 * 
	 * @param sb
	 */
	private void writePrevious(StringBuffer sb, String url, int pageId) {

		if (pageId > 1) {
			sb.append("<a href=\"" + this.getHref(url, (pageId - 1)) + "\">&lt;</a>");
		}

	}

	private void writeNext(StringBuffer sb, String url, int totalPage, int pageId) {

		if (pageId < totalPage) {
			sb.append("<a href=\"" + this.getHref(url, (pageId + 1)) + "\">&gt;</a>");
		}

	}

	private void writeGo(StringBuffer sb, String url) {
		url = this.getHref(url, 0);
		url = url.substring(0, url.length() - 1);
		sb.append("<input type=\"text\" name=\"jump_page\" id=\"jump_page\" size=\"2\" class=\"page_txt\"/>");
		sb.append("<a href=\"javacript:;\" onclick=\"Page.go(this,'" + url + "');\"\">GO</a>");
	}

	// private void writeScript(StringBuffer sb) {
	// sb.append("<script>");
	// sb.append(" var Page = {go : function(p){$('#pageId').val(p);$('#"
	// + this.formId + "').submit();}};");
	// sb.append("</script>");
	// }

	protected void writeInclude(StringBuffer sb) {
		// sb.append("<link rel=\"stylesheet\" href=\"/chico/css/chico-min-0.11.1.css\"/>");
		// sb.append("<script type=\"text/javascript\" src=\"/chico/js/chico-min-0.11.1.js\"/>");
	}

	// private String getHref(String requestUrl, int pageId) {
	// pageId = pageId == 0 ? 1 : pageId;
	// String params[] = requestUrl.split("&");
	// String href = "";
	// for (String param : params) {
	// int k = param.indexOf("?pageId=");
	// int j = param.indexOf("pageId=");
	// if (k != -1) {
	// href += param.substring(0, k) + "?pageId=" + pageId;
	// continue;
	// }
	// if (j == 0) {
	// href += "&pageId=" + pageId;
	// continue;
	// }
	//
	// String[] keyValue = this.getKeyValue(param);
	//
	// if (href.indexOf("?") != -1) {
	// href += "&" + keyValue[0] + "=" + keyValue[1];
	// } else {
	// href += keyValue[0] + "=" + keyValue[1];
	// }
	// }
	//
	// return href;
	// }

	public static void main(String args[]) {
		// int pageId = 15;
		// int pageSize = 20;
		// int total = 1000;
		// String url = "";
		// boolean go = false;
		System.out.print(new WinPageStyle().getHref("?pageId=1&cc=你好&dd=ee", 1));
	}
}
