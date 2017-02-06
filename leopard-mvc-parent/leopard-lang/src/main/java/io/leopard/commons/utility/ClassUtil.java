package io.leopard.commons.utility;

import io.leopard.core.exception.ClassNotFoundRuntimeException;

public class ClassUtil {
	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new ClassNotFoundRuntimeException(e.getMessage(), e);
		}
	}

	public static boolean exist(String className) {
		try {
			Class.forName(className);
			return true;
		}
		catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Class<?> getRealClass(Class<?> clazz) {
		while (true) {
			String className = clazz.getName();
			if (className.indexOf("$$") == -1) {
				return clazz;
			}
			clazz = clazz.getSuperclass();
			if (clazz == null || clazz.equals(Object.class)) {
				throw new RuntimeException("获取真实class出错.");
			}
		}
	}

}
