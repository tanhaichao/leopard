package io.leopard.web.taglib.tags.paging.template;

public class MacPageStyle extends PageStyle {

	@Override
	public String getHtml(int pageId, int pageSize, int total, String url, boolean go, String align) {
		return this.createHtml(pageId, pageSize, total, url, go, align);
	}

	public String createHtml(int pageId, int pageSize, int total, String url, boolean go, String align) {
		// 设置默认值
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
		// int p = (total / pageSize);
		// if (total % pageSize != 0) {
		// p += 1;
		// }
		int totalPage = (total - 1) / pageSize + 1;
		this.writePrevious(sb, url, pageId);
		// 如果小于14页
		if (totalPage < 14) {
			for (int i = 0; i < totalPage; i++) {
				sb.append("<a href=\"" + this.getHref(url, (i + 1)) + "\"");
				if ((i + 1) == pageId) {
					sb.append(" class=\"on\"");
				}
				sb.append(">");
				sb.append((i + 1) + "</a>\n");
			}
		}
		else {// 如果大于14页
			int pageNo[] = this.getPageNo(totalPage, pageId);
			for (int i = 0; i < pageNo.length; i++) {
				if (pageNo[i] != 0) {
					sb.append("<a href=\"" + getHref(url, pageNo[i]) + "\"");
					if ((pageNo[i]) == pageId) {
						sb.append(" class=\"on\"");
					}
					sb.append(">");
					sb.append((pageNo[i]) + "</a>");
				}
				else {
					sb.append("<a>..</a>");
				}
			}
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
		if (pageId <= 9) {
			int pageNo[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, totalPage - 1, totalPage };
			return pageNo;
		}
		else if (totalPage - pageId <= 5) {
			int pageNo[] = { 1, 2, 0, totalPage - 9, totalPage - 8, totalPage - 7, totalPage - 6, totalPage - 5, totalPage - 4, totalPage - 3, totalPage - 2, totalPage - 1, totalPage };
			return pageNo;
		}
		else {
			int pageNo[] = { 1, 2, 0, pageId - 5, pageId - 4, pageId - 3, pageId - 2, pageId - 1, pageId, pageId + 1, pageId + 2, pageId + 3, pageId + 4, 0, totalPage - 1, totalPage };
			return pageNo;
		}
	}

	/**
	 * 生成上一页
	 * 
	 * @param sb
	 */
	private void writePrevious(StringBuffer sb, String url, int pageId) {

		if (pageId > 1) {
			sb.append("<a href=\"" + this.getHref(url, (pageId - 1)) + "\">上一页</a>");
		}

	}

	private void writeNext(StringBuffer sb, String requestUrl, int totalPage, int pageId) {

		if (pageId < totalPage) {
			sb.append("<a href=\"" + this.getHref(requestUrl, (pageId + 1)) + "\">下一页</a>");
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
	//
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
	//
	// }
	// // try {
	// // href = URLEncoder.encode(href, "utf-8");
	// // } catch (UnsupportedEncodingException e) {
	// // throw new RuntimeException("参数编码 错误：[" + href + "].");
	// // }
	// return href;
	// }

	public static void main(String args[]) {
		// String text =
		// " 测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容。测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容测试：小说第四章：冰糖小丸子内容";
		System.out.print(new MacPageStyle().getHref("http://www.baidu.com?pageId=1&cc=你好&dd=ee", 0));
	}

}
