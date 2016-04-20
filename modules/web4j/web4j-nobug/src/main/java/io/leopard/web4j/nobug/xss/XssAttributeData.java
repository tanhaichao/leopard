package io.leopard.web4j.nobug.xss;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

public class XssAttributeData {

	private static Map<String, Boolean> map = new ConcurrentHashMap<String, Boolean>();

	public static void add(String uri, String paramName) {
		// AssertUtil.assertNotEmpty(paramName, "参数名称不能为空.");

		if (StringUtils.isEmpty(paramName)) {
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
