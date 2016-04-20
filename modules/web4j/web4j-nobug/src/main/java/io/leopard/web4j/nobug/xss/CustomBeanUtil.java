package io.leopard.web4j.nobug.xss;

@Deprecated
public class CustomBeanUtil {

	/**
	 * 是否自定义的类.
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isCustomBean(Class<?> clazz) {
		String className = clazz.getName();
		return isCustomBean(className);
	}

	public static boolean isCustomBean(String className) {
		if (className.startsWith("com.duowan")) {
			return true;
		}
		if (className.startsWith("com.yy")) {
			return true;
		}
		if (className.startsWith("cn.kuaikuai")) {
			return true;
		}
		if (className.startsWith("com.laopao")) {
			return true;
		}
		return false;
	}
}
