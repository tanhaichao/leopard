package io.leopard.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 调试信息.
 * 
 * @author 谭海潮
 *
 */
public class DebugUtil {

	public static void setDebug(Object obj) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attr == null) {
			return;
		}
		HttpServletRequest request = attr.getRequest();
		request.setAttribute("debug", obj);
	}
}
