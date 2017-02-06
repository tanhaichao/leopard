package io.leopard.lang.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.reflect.FieldUtils;


public class FieldUtil {

	public static List<Field> listFields(Object bean) {
		List<Field> list = new ArrayList<Field>();
		Class<?> currentClazz = bean.getClass();
		while (true) {
			Field[] fields = currentClazz.getDeclaredFields();
			for (Field field : fields) {
				list.add(field);
			}
			if (currentClazz.getSuperclass() == null) {
				break;
			}
			currentClazz = currentClazz.getSuperclass();
		}
		return list;
	}

	public static List<Field> listFields(Object bean, Class<?> clazz) {
		return FieldUtil.listFields(bean, clazz.getName());
	}

	public static List<Field> listFields(Object bean, String className) {

		List<Field> list = new ArrayList<Field>();
		Class<?> currentClazz = bean.getClass();
		while (true) {
			Field[] fields = currentClazz.getDeclaredFields();
			for (Field field : fields) {
				// System.out.println("fieldName:" + field.getType().getName());
				if (field.getType().getName().equals(className)) {
					list.add(field);
				}
			}
			if (currentClazz.getSuperclass() == null) {
				break;
			}
			currentClazz = currentClazz.getSuperclass();
		}
		return list;
	}

	public static void setFieldValue(Object bean, Field field, Object value) {
		field.setAccessible(true);
		try {
			field.set(bean, value);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Object getFieldValue(Object bean, String fieldName) {
		Field field = FieldUtils.getField(bean.getClass(), fieldName, true);
		if (field == null) {
			throw new NullPointerException("在对象[" + bean.getClass().getName() + "]找不到属性[" + fieldName + "].");
		}
		return getFieldValue(bean, field);
	}

	protected static Field getField(Object bean, Class<?> type) {
		Field[] fields = bean.getClass().getDeclaredFields();
		// if (fields != null) {
		for (Field field : fields) {
			if (field.getType().equals(type)) {
				return field;
			}
		}
		// }
		return null;
	}

	public static Object getFieldValue(Object bean, Class<?> type) {
		Field field = getField(bean, type);
		if (field == null) {
			throw new NullPointerException("在对象[" + bean.getClass().getName() + "]找不到属性[" + type.getSimpleName() + "].");
		}
		return getFieldValue(bean, field);
	}

	public static Object getFieldValue(Object bean, Field field) {
		field.setAccessible(true);
		Object value;
		try {
			value = field.get(bean);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return value;
	}
}
