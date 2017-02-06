package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;

/**
 * 页面特殊参数.
 * 
 * @author 阿海
 * 
 */
public interface XParam {

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
	Object getValue(HttpServletRequest request, MethodParameter parameter);

	// /**
	// * 当存在多个相同key的实现时，被覆盖的实现类通过此方法传递给新的实现类
	// *
	// * TODO 什么情况需要用到此方法?
	// *
	// * @param xparam
	// */
	// void override(XParam xparam);
}
