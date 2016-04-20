package io.leopard.burrow.refect;

public class ClassTypeUtil {

	
	public static boolean isInteger(Class<?> clazz) {
		return isInteger(clazz.getName());
	}

	public static boolean isLong(Class<?> clazz) {
		return isLong(clazz.getName());
	}

	public static boolean isFloat(Class<?> clazz) {
		return isFloat(clazz.getName());
	}

	public static boolean isDouble(Class<?> clazz) {
		return isDouble(clazz.getName());
	}

	public static boolean isBoolean(Class<?> clazz) {
		return isBoolean(clazz.getName());
	}

	public static boolean isInteger(String className) {
		return (className.equals(int.class.getName()) || className.equals(Integer.class.getName()));
	}

	public static boolean isLong(String className) {
		return (className.equals(long.class.getName()) || className.equals(Long.class.getName()));
	}

	public static boolean isFloat(String className) {
		return (className.equals(float.class.getName()) || className.equals(Float.class.getName()));
	}

	public static boolean isDouble(String className) {
		return (className.equals(double.class.getName()) || className.equals(Double.class.getName()));
	}

	public static boolean isBoolean(String className) {
		return (className.equals(boolean.class.getName()) || className.equals(Boolean.class.getName()));
	}

}
