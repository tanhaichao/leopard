package io.leopard.web.view;

import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转
 * 
 * @author 阿海
 * 
 */
public class RedirectView extends ModelAndView {

	private String url;

	public RedirectView(String url) {
		super(new org.springframework.web.servlet.view.RedirectView(url));
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
