package io.leopard.jdbc;

import io.leopard.jdbc.builder.InsertParser;
import io.leopard.jdbc.builder.UpdateParser;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class SqlParserUtil {
	public static StatementParameter toUpdateParameter(String sql, Object bean) {
		StatementParameter param = new StatementParameter();
		UpdateParser updateParser = new UpdateParser(sql);
		List<String> fieldList = updateParser.getFields();
		for (String fieldName : fieldList) {
			try {
				toStatementParameter(param, bean, fieldName);
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return param;
	}

	public static StatementParameter toInsertParameter(String sql, Object bean) {
		StatementParameter param = new StatementParameter();
		InsertParser insertParser = new InsertParser(sql);
		LinkedHashSet<String> fieldSet = insertParser.getFields();
		for (String fieldName : fieldSet) {
			try {
				toStatementParameter(param, bean, fieldName);
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return param;
	}

	protected static String toPropertyName(String filedName) {
		char[] arr = filedName.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if (c == '_') {
				char c2 = arr[i + 1];
				if (c2 >= 'a' && c2 <= 'z') {
					c2 = (char) (c2 - 32);
				}
				sb.append(c2);
				i++;
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	protected static void toStatementParameter(StatementParameter param, Object bean, String fieldName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> clazz = bean.getClass();

		String propertyName = toPropertyName(fieldName);
		// String methodName = "get" +
		// StringUtil.firstCharToUpperCase(fieldName);
		PropertyDescriptor desc = BeanUtils.getPropertyDescriptor(clazz, propertyName);
		if (desc == null) {
			throw new RuntimeException("在类[" + clazz.getName() + "]获取不到字段[" + propertyName + "].");
		}
		Class<?> type = desc.getPropertyType();
		// System.out.println("fieldName:" + fieldName + " methodName:" +
		// methodName + " desc:" + desc.getPropertyType());

		Method method = desc.getReadMethod();
		Object value = method.invoke(bean, new Object[] {});
		if (value == null) {
			throw new IllegalArgumentException("参数[" + propertyName + "]不能为null.");
		}
		setValue(param, type, value);
	}

	protected static void setValue(StatementParameter param, Class<?> type, Object value) {
		String className = type.getName();
		// class.getName方法会有性能问题，可以写成常量
		if (String.class.getName().equals(className)) {
			param.setString((String) value);
		}
		else if (Date.class.getName().equals(className)) {
			param.setDate((Date) value);
		}
		else if (className.equals(int.class.getName()) || className.equals(Integer.class.getName())) {
			param.setInt((Integer) value);
		}
		else if (className.equals(long.class.getName()) || className.equals(Long.class.getName())) {
			param.setLong((Long) value);
		}
		else if (className.equals(float.class.getName()) || className.equals(Float.class.getName())) {
			param.setFloat((Float) value);
		}
		else if (className.equals(double.class.getName()) || className.equals(Double.class.getName())) {
			param.setDouble((Double) value);
		}
		else if (className.equals(boolean.class.getName()) || className.equals(Boolean.class.getName())) {
			param.setBool((Boolean) value);
		}
		else {
			throw new IllegalArgumentException("未知数据类型[" + className + "].");
		}
	}
}
