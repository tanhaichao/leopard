package io.leopard.commons.utility;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型常用方法.
 * 
 * @author 阿海
 * 
 */
public class GenericUtil {
	public static final int IS_INTEGER = 1;
	public static final int IS_STRING = 2;
	public static final int IS_LIST = 3;
	public static final int IS_LONG = 4;

	public static final int IS_MODEL = 99;

	/**
	 * 获取对象直接超类的实际类型参数的 Type对象的数组
	 * 
	 * @param obj
	 *            对象
	 * @return 表示参数化类型的实际类型参数的 Type对象的数组
	 */
	public static Type[] getActualTypeArguments(Object obj) {
		Type genericType = obj.getClass().getGenericSuperclass();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) genericType;
			return type.getActualTypeArguments();
		}
		else {
			throw new IllegalArgumentException("非法genericType:" + genericType);
		}
	}

}
