package io.leopard.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.leopard.web.servlet.RequestUtil;

/**
 * 调试信息.
 * 
 * @author 谭海潮
 *
 */
public class DebugUtil {

	public static void setDebug(Object obj) {
		HttpServletRequest request = RequestUtil.getCurrentRequest();
		if (request == null) {
			return;
		}
		request.setAttribute("debug", obj);
	}
}
