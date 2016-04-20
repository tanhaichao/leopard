package io.leopard.web4j.parameter;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面特殊参数.
 * 
 * @author 阿海
 * 
 */
public interface PageParameter {

	/**
	 * 参数名称(区分大小写)，在spring初始化时使用.
	 * 
	 * @return
	 */
	String getKey();

	/**
	 * 获取参数值.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	String getValue(HttpServletRequest request);

}
