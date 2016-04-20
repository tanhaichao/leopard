package io.leopard.web4j.view;

/**
 * 数据更新后的页面跳转，用于做CSRF漏洞防范.
 * 
 * @author 阿海
 * 
 */
public class UpdatedRedirectView extends RedirectView {

	public UpdatedRedirectView(String url) {
		super(url);
	}

}
