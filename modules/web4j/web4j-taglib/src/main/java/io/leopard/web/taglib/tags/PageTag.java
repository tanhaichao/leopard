package io.leopard.web.taglib.tags;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.web.taglib.tags.paging.template.EasyPageStyle;
import io.leopard.web.taglib.tags.paging.template.MacPageStyle;
import io.leopard.web.taglib.tags.paging.template.PageStyle;
import io.leopard.web.taglib.tags.paging.template.WinPageStyle;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 分页标签
 * 
 * @author 卢轩华
 * 
 */
public class PageTag extends AbstractStartTagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getContent() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

		Object pageIdObj = request.getAttribute("pageId");
		Object totalCountObj = request.getAttribute("totalCount");
		Object pageSizeObj = request.getAttribute("pageSize");

		AssertUtil.assertNotNull(pageIdObj, "pageId还没有设置.");
		AssertUtil.assertNotNull(totalCountObj, "totalCount还没有设置.");
		AssertUtil.assertNotNull(pageSizeObj, "pageSize还没有设置.");

		// if (pageIdObj == null) {
		// throw new IllegalArgumentException("pageId还没有设置.");
		// }
		// if (totalCountObj == null) {
		// throw new IllegalArgumentException("totalCount还没有设置.");
		// }
		// if (pageSizeObj == null) {
		// throw new IllegalArgumentException("pageSize还没有设置.");
		// }
		int pageId = (Integer) pageIdObj;
		int total = (Integer) totalCountObj;
		int pageSize = (Integer) pageSizeObj;
		if (pageSize < 1) {
			throw new IllegalArgumentException("当前页显示行数不能小于1[" + pageSize + "].");
		}

		String url = this.getUrl(request);

		String html;

		if (PageStyle.MAC_STYLE.equalsIgnoreCase(this.style)) {
			html = new MacPageStyle().getHtml(pageId, pageSize, total, url, go, align);
		}
		else if (PageStyle.WIN_STYLE.equalsIgnoreCase(this.style)) {
			html = new WinPageStyle().getHtml(pageId, pageSize, total, url, go, align);
		}
		else if (PageStyle.EASY_STYLE.equalsIgnoreCase(this.style)) {
			html = new EasyPageStyle().getHtml(pageId, pageSize, total, url, false, "");
		}
		else {
			throw new RuntimeException("未知风格[" + this.style + "].");
		}
		return html;
	}

	protected String getUrl(HttpServletRequest request) {
		String url = StringUtils.defaultIfEmpty(request.getRequestURI(), "");
		// url = url.substring(0, url.indexOf("?") == -1 ? url.length() : url.indexOf("?"));
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			if (name.equalsIgnoreCase("pageId")) {
				continue;
			}
			String value = (String) request.getParameter(name);
			if (url.indexOf("?") == -1) {
				url += "?" + name + "=" + value;
			}
			else {
				url += "&" + name + "=" + value;
			}
		}
		return url;
	}

	// /**
	// * 当前页
	// */
	// private int pageId = 1;
	//
	// /**
	// * 总数
	// */
	// private int total = 0;

	// /**
	// * 每页显示数量
	// */
	// private int pageSize = 10;

	/**
	 * 是否允许跳转
	 */
	private boolean go = false;

	/**
	 * 风格
	 */
	private String style = PageStyle.MAC_STYLE;

	/**
	 * 对齐方式
	 */
	private String align = PageStyle.ALIGN_LEFT;

	// public void setPageId(int pageId) {
	// this.pageId = pageId;
	// }
	//
	// public void setTotal(int total) {
	// this.total = total;
	// }

	// public void setPageSize(int pageSize) {
	// this.pageSize = pageSize;
	// }

	public void setGo(boolean go) {
		this.go = go;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setAlign(String align) {
		this.align = align;
	}

}
