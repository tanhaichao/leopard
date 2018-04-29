package io.leopard.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;

/**
 * 资源文件处理器
 * 
 * @author 谭海潮
 *
 */
public interface ResourceHandler {

	/**
	 * 
	 * @param uri URL(已经做过路径安全性检查，并做了重复的/过滤)
	 * @param request
	 * @param response
	 * @return
	 */
	Resource doHandler(String uri, HttpServletRequest request, HttpServletResponse response);
}
