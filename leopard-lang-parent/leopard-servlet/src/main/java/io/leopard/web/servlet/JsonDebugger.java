package io.leopard.web.servlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Json调试工具.
 * 
 * @author 谭海潮
 *
 */
public class JsonDebugger {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getDebugMap() {
		HttpServletRequest request = RequestUtil.getCurrentRequest();
		if (request == null) {
			return null;
		}
		return (Map<String, Object>) request.getAttribute("debug");
	}

	public static void addAttribute(String name, Object value) {
		HttpServletRequest request = RequestUtil.getCurrentRequest();
		if (request == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) request.getAttribute("debug");
		if (map == null) {
			map = new ConcurrentHashMap<String, Object>();
			request.setAttribute("debug", map);
		}
		map.put(name, value);
	}

	// public static void setDebug(Object obj) {
	// HttpServletRequest request = RequestUtil.getCurrentRequest();
	// if (request == null) {
	// return;
	// }
	// request.setAttribute("debug", obj);
	// }

}
