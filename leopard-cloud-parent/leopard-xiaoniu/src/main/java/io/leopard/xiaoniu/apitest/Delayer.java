package io.leopard.xiaoniu.apitest;

import javax.servlet.http.HttpServletRequest;

/**
 * 延迟器
 * 
 * @author 阿海
 *
 */
public interface Delayer {

	/**
	 * 
	 * @param request
	 * @param requestUri
	 * @param handler
	 * @return true:表示已延迟，理解结束，false，表示未延迟，交给下一个延迟器处理.
	 * @throws InterruptedException
	 */
	boolean delay(HttpServletRequest request, String requestUri, Object handler) throws Exception;

}
