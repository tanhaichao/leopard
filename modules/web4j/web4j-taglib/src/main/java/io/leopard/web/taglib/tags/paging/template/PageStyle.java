package io.leopard.web.taglib.tags.paging.template;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class PageStyle {
	/**
	 * 获取生成的html
	 * 
	 * @pageId 当前页
	 * @pageSize 一页显示多少条记录
	 * @total 总共有多少条记录
	 * @url 请求的地址
	 * @go 是否显示GO
	 * @return
	 */
	public abstract String getHtml(int pageId, int pageSize, int total, String url, boolean go, String align);

	public static final String MAC_STYLE = "mac";

	public static final String WIN_STYLE = "win";

	public static final String EASY_STYLE = "easy";

	// 左对齐
	public static final String ALIGN_LEFT = "left";

	// 右对齐
	public static final String ALIGN_RIGHT = "right";

	public String writeScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append(" var Page ={");
		sb.append("     'go':function(obj, url){");
		sb.append("          var jump_page = $(obj).prev().val();");
		sb.append("          location.href= url  + jump_page;");
		sb.append("      }");
		sb.append("  }");
		sb.append("</script>");
		return sb.toString();
	}

	public String[] getKeyValue(String param) {
		String[] returnKeyValue = new String[2];
		String[] keyValue = param.split("=");
		String key = "";
		String value = "";
		if (keyValue != null && keyValue.length != 0) {
			key = keyValue[0];
			if (keyValue.length > 1) {
				value = keyValue[1];
			}
		}
		try {
			value = URLEncoder.encode(value, "utf-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("参数编码 错误：[" + value + "].");
		}
		returnKeyValue[0] = key;
		returnKeyValue[1] = value;
		return returnKeyValue;
	}

	public String getHref(String requestUrl, int pageId) {
		// TODO 参数名称和值没有URL编码
		pageId = pageId <= 0 ? 1 : pageId;
		String params[] = requestUrl.split("&");
		String href = "";
		for (String param : params) {
			int k = param.indexOf("?pageId=");
			int j = param.indexOf("pageId=");
			if (k != -1) {
				href += param.substring(0, k) + "?pageId=" + pageId;
				continue;
			}
			if (j == 0) {
				href += "&pageId=" + pageId;
				continue;
			}

			String[] keyValue = this.getKeyValue(param);

			if (href.indexOf("?") != -1) {
				href += "&" + keyValue[0] + "=" + keyValue[1];
			}
			else {
				href += keyValue[0] + "=" + keyValue[1];
			}
		}

		return href;
	}

}
