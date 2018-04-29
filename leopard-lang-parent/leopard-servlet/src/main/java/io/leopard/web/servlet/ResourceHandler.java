package io.leopard.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;

public interface ResourceHandler {

	/**
	 * 
	 * @param uri URL(已经做过路径安全性检查)
	 * @param request
	 * @param response
	 * @return
	 */
	Resource doHandler(String uri, HttpServletRequest request, HttpServletResponse response);
}
