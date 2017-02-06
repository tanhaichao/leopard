package io.leopard.test.mock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class BeanAssert {

	public static void assertModel(Class<?> clazz) {
		try {
			assertModel2(clazz);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	

	public static void assertModel2(Class<?> clazz) throws Exception {
		Object bean = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {

			if (isIgnore(field)) {
				continue;
			}
			Class<?> type = field.getType();
			String fieldName = field.getName();
			Object value;
			try {
				value = getDefaultValue(type);
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
				continue;
			}
			catch (RuntimeException e) {
				throw new RuntimeException(e.getMessage() + " fieldName:" + fieldName, e);
			}

			String getMethodName;
			if (isBoolean(type)) {
				getMethodName = "is" + firstCharToUpperCase(fieldName);
			}
			else {
				getMethodName = "get" + firstCharToUpperCase(fieldName);
			}
			String setMethodName = "set" + firstCharToUpperCase(fieldName);
			// System.out.println("getMethodName:" + getMethodName +
			// " setMethodName:" + setMethodName);
			Method setMethod = clazz.getMethod(setMethodName, type);
			Method getMethod = clazz.getMethod(getMethodName);
			setMethod.invoke(bean, value);
			Object value2 = getMethod.invoke(bean);
			if (isNotEquals(value2, value)) {
				throw new RuntimeException("字段[" + fieldName + "]get和set方法不匹配.");
			}
		}
		// Method[] methods = clazz.getMethods();
		// for (Method method : methods) {
		// String methodName = method.getName();
		// if (!(methodName.startsWith("get") || methodName.startsWith("set") ||
		// methodName.startsWith("is") || "toString".equals(methodName))) {
		// continue;
		// }
		// Object[] args = getDefaultValue(method.getParameterTypes());
		// try {
		// // System.out.println("args:" + args);
		// method.invoke(bean, args);
		// }
		// catch (Exception e) {
		// // System.err.println("method:" + method.toGenericString() + " args:"
		// + StringUtils.join(args));
		// throw new RuntimeException(e.toString(), e);
		// }
		// }
	}

	public static boolean isBoolean(Class<?> clazz) {
		return (boolean.class.equals(clazz) || Boolean.class.equals(clazz));
	}

	public static boolean isNotEquals(Object str1, Object str2) {
		return (!str1.equals(str2));
	}

	/**
	 * 首字母变大写.
	 * 
	 * @param word
	 * @return
	 */
	public static String firstCharToUpperCase(String word) {
		if (word.length() == 1) {
			return word.toUpperCase();
		}
		char c = word.charAt(0);
		char newChar;
		// System.out.println("c:" + c);
		if (c >= 'a' && c <= 'z') {
			newChar = (char) (c - 32);
		}
		else {
			newChar = c;
		}
		return newChar + word.substring(1);
	}

	// protected static Object[] getDefaultValue(Class<?>[] classes) {
	// Object[] args = new Object[classes.length];
	// for (int i = 0; i < classes.length; i++) {
	// args[i] = getDefaultValue(classes[i]);
	// }
	// return args;
	// }

	protected static boolean isIgnore(Field field) {
		Class<?> type = field.getType();
		if (type.equals(List.class)) {
			return true;
		}
		String filedName = field.getName();

		if (filedName.indexOf("$") != -1) {
			return true;
		}
		return false;
	}

	protected static Object getDefaultValue(Class<?> clazz) {
		if (clazz.equals(String.class)) {
			return "string";
		}
		if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			return 0;
		}
		if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			return 0L;
		}
		if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			return 0F;
		}
		if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			return 0D;
		}
		if (clazz.equals(Date.class)) {
			return new Date();
		}
		if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
			return false;
		}
		throw new IllegalArgumentException("未知类型[" + clazz.getName() + "].");
	}
}
