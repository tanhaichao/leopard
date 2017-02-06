package io.leopard.test.mock;

import java.util.Map;

public class MockStatic {
	private static final ThreadLocal<Map<String, String>> STATIC_CLASS_MAPPING = new ThreadLocal<Map<String, String>>();

	public static String getStaticClassName(String classSimpleName) {
		Map<String, String> map = STATIC_CLASS_MAPPING.get();
		if (map == null) {
			throw new NullPointerException("您还没有在@PrepareForTest加入[" + classSimpleName + ".class].");
		}
		return map.get(classSimpleName);
	}

	public static void setStaticClassName(Map<String, String> mapping) {
		STATIC_CLASS_MAPPING.set(mapping);
	}

}
