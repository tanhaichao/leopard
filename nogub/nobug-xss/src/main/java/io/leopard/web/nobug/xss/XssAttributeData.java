package io.leopard.web.nobug.xss;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XssAttributeData {

	private static Map<String, Boolean> map = new ConcurrentHashMap<String, Boolean>();

	public static void add(String uri, String paramName) {
		// AssertUtil.assertNotEmpty(paramName, "参数名称不能为空.");

		if (paramName == null || paramName.length() == 0) {
			throw new IllegalArgumentException("参数名称不能为空.");
		}
		String key = uri + ":" + paramName;
		map.put(key, true);
	}

	public static boolean isNoXss(String uri, String paramName) {
		String key = uri + ":" + paramName;
		// System.err.println("keySet:" + map.keySet());
		return map.containsKey(key);
	}
}
