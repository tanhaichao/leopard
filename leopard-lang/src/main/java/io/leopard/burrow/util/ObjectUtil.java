package io.leopard.burrow.util;

public class ObjectUtil {
	
	/**
	 * 判断一个对象是否为null
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	
	/**
	 * 判断一个对象是否为非null
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return obj != null;
	}
}
