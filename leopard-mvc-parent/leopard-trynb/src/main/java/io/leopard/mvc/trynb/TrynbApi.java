package io.leopard.mvc.trynb;

import javax.servlet.http.HttpServletRequest;

import io.leopard.mvc.trynb.model.TrynbInfo;

public interface TrynbApi {

	/**
	 * 
	 * @param request
	 * @param uri
	 * @param exception
	 * @return 如果返回null，则有Leopard定义异常信息
	 */
	TrynbInfo parse(TrynbLogger trynbLogger, HttpServletRequest request, String uri, Exception exception);

}
