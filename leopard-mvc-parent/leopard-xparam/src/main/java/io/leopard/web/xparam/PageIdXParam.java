package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取当前页码.
 * 
 * @author 阿海
 * 
 */
@Service
public class PageIdXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String page = this.getPage(request);
		int pageid = XParamUtil.toInt(page);
		if (pageid < 1) {
			pageid = 1;
		}
		return pageid;
	}

	protected String getPage(HttpServletRequest request) {
		String page = request.getParameter("pageid");
		if (page == null) {
			page = request.getParameter("pageId");
		}
		if (page == null) {
			page = request.getParameter("page");
		}
		if (page == null) {
			page = request.getParameter("p");
		}
		return page;
	}

	@Override
	public String getKey() {
		return "pageId";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
