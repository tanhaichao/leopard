package io.leopard.commons.utility;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.core.exception.NoSuchFieldRuntimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class BeanUtil {

	/**
	 * 实例化一个clazz类的对象</br>
	 * 
	 * @param clazz 类名
	 * @param params 构造方法的参数
	 * @return clazz对象
	 */
	public static <T> T instantiateClass(Class<T> clazz, Object... params) {
		Class<?>[] parameterTypes = new Class<?>[params.length];
		for (int i = 0; i < params.length; i++) {
			parameterTypes[i] = params[i].getClass();
		}
		try {
			Constructor<T> ctor = clazz.getConstructor(parameterTypes);
			T bean = ctor.newInstance(params);
			return bean;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 判断一个对象是否为null</br>
	 * 
	 * @param bean 对象
	 * @return 如果对象为null，返回true
	 */
	public static boolean isNull(Object bean) {
		return bean == null;
	}

	/**
	 * 判断一个对象是否非空</br>
	 * 
	 * @param bean 对象
	 * @return 如果对象非空，返回true
	 */
	public static boolean isNotNull(Object bean) {
		return bean != null;
	}

	// @SuppressWarnings("unchecked")
	// public static <T> T getFieldValue(Object bean, String fieldName) {
	// try {
	// return (T) BeanUtils.getProperty(bean, fieldName);
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// }

	/**
	 * 获取一个对象指定字段上的值
	 * 
	 * @param model 对象
	 * @param fieldName 指定的字段
	 * @return 字段对应的值，若对象为空则返回null
	 */
	public static Object getPropertyValue(Object model, String fieldName) {
		if (model == null) {
			return null;
		}

		Field field = getField(model, fieldName);
		field.setAccessible(true);
		try {
			return field.get(model);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	/**
	 * 获取一个对象指定字段上的值
	 * 
	 * @param bean 对象
	 * @param fieldName 指定的字段
	 * @return 字段对应的值
	 */
	public static Object getFieldValue(Object bean, String fieldName) {
		try {
			Field field = getField(bean, fieldName);
			field.setAccessible(true);
			return field.get(bean);
		}
		catch (Exception e) {
			throw new NoSuchFieldRuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * h获取对象指定字段的Field对象
	 * 
	 * @param bean 对象
	 * @param fieldName 字段名
	 * @return 字段对应的Field对象
	 */
	public static Field getField(Object bean, String fieldName) {
		AssertUtil.assertNotNull(bean, "参数bean不能为空。");
		Class<?> clazz = bean.getClass();
		while (true) {
			try {
				return clazz.getDeclaredField(fieldName);
			}
			catch (NoSuchFieldException e) {
				if (clazz.getSuperclass() == null) {
					String className = bean.getClass().getSimpleName();
					throw new NoSuchFieldRuntimeException("clazz:" + className + " " + e.getMessage(), e);
				}
				clazz = clazz.getSuperclass();
			}
		}
	}

	public static void setFieldValue(Object bean, String fieldName, Object value) {
		try {
			Field field = bean.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(bean, value);
		}
		catch (Exception e) {
			throw new NoSuchFieldRuntimeException(e.getMessage(), e);
		}

	}
}
