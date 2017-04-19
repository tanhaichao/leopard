package io.leopard.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import io.leopard.json.Json;
import io.leopard.json.JsonException;
import io.leopard.lang.inum.EnumConstantInvalidException;
import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;

public class LeopardBeanPropertyRowMapper<T> implements RowMapper<T> {

	private Class<T> mappedClass;

	private Map<String, Field> mappedFields;

	public LeopardBeanPropertyRowMapper(Class<T> mappedClass) {
		this.mappedClass = mappedClass;

		mappedFields = new HashMap<String, Field>();

		Class<?> clazz = mappedClass;
		while (true) {

			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				String key = field.getName().toLowerCase();
				// System.out.println("key:" + key);
				mappedFields.put(key, field);
			}
			clazz = clazz.getSuperclass();
			if (clazz.equals(Object.class)) {
				break;
			}
		}

	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		Assert.state(this.mappedClass != null, "Mapped class was not specified");
		T bean = BeanUtils.instantiate(this.mappedClass);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int index = 1; index <= columnCount; index++) {
			String column = JdbcUtils.lookupColumnName(rsmd, index);
			// System.err.println("column:" + column);
			column = column.replaceAll(" ", "").toLowerCase();
			column = column.replace("_", "");

			Field field = this.mappedFields.get(column);

			// if (field == null) {
			// this.mappedFields.get(underscoreName(column));
			// }
			// System.out.println("column:" + column + " field:" + field);

			if (field == null && column.endsWith("s")) {
				// TODO images转imageList的临时实现?
				String column2 = column.substring(0, column.length() - 1) + "list";
				field = this.mappedFields.get(column2);
				// System.out.println("column2:" + column2 + " field:" + field);
			}

			if (field != null) {
				Object value = getColumnValue(rs, index, field);

				field.setAccessible(true);
				try {
					field.set(bean, value);
				}
				catch (IllegalArgumentException e) {
					throw new SQLException(e.getMessage(), e);
				}
				catch (IllegalAccessException e) {
					throw new SQLException(e.getMessage(), e);
				}
			}
		}
		return bean;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getColumnValue(ResultSet rs, int index, Field field) throws SQLException {
		Class<?> requiredType = field.getType();
		JdbcUtils.getResultSetValue(rs, index, requiredType);
		Object value;
		if (String.class.equals(requiredType)) {
			value = rs.getString(index);
		}
		else if (boolean.class.equals(requiredType) || Boolean.class.equals(requiredType)) {
			value = rs.getBoolean(index);
		}
		else if (byte.class.equals(requiredType) || Byte.class.equals(requiredType)) {
			value = rs.getByte(index);
		}
		else if (short.class.equals(requiredType) || Short.class.equals(requiredType)) {
			value = rs.getShort(index);
		}
		else if (int.class.equals(requiredType) || Integer.class.equals(requiredType)) {
			value = rs.getInt(index);
		}
		else if (long.class.equals(requiredType) || Long.class.equals(requiredType)) {
			value = rs.getLong(index);
		}
		else if (float.class.equals(requiredType) || Float.class.equals(requiredType)) {
			value = rs.getFloat(index);
		}
		else if (double.class.equals(requiredType) || Double.class.equals(requiredType) || Number.class.equals(requiredType)) {
			value = rs.getDouble(index);
		}
		else if (byte[].class.equals(requiredType)) {
			value = rs.getBytes(index);
		}
		else if (java.sql.Timestamp.class.equals(requiredType) || java.util.Date.class.equals(requiredType)) {
			value = rs.getTimestamp(index);
		}
		else if (java.util.Date.class.isAssignableFrom(requiredType)) {
			Timestamp timestamp = rs.getTimestamp(index);
			Date date;
			try {
				date = (Date) requiredType.newInstance();
			}
			catch (InstantiationException e) {
				throw new RuntimeException(requiredType.getName() + "没有默认构造方法.", e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(requiredType.getName() + "默认构造方法实例化失败.", e);
			}
			date.setTime(timestamp.getTime());
			value = date;
		}
		else if (List.class.equals(requiredType)) {
			String json = rs.getString(index);
			// System.out.println("name:" + field.getName() + " json:" + json);
			ParameterizedType type = (ParameterizedType) field.getGenericType();
			String elementClassName = type.getActualTypeArguments()[0].getTypeName();

			if (int.class.getName().equals(elementClassName) || Integer.class.getName().equals(elementClassName)) {
				value = Json.toListObject(json, Integer.class);
			}
			else if (long.class.getName().equals(elementClassName) || Long.class.getName().equals(elementClassName)) {
				value = Json.toListObject(json, Long.class);
			}
			else if (String.class.getName().equals(elementClassName)) {
				value = Json.toListObject(json, String.class);
			}
			else {
				Class<?> elementType;
				try {
					elementType = Class.forName(elementClassName);
				}
				catch (ClassNotFoundException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				if (elementType.isEnum()) {
					return toEnumList(json, requiredType, elementType);
				}
				else {
					Class<?> clazz = (Class<?>) type.getActualTypeArguments()[0];
					// System.out.println("clazz:" + clazz.getName());
					value = Json.toListObject(json, clazz, true);
					// throw new IllegalArgumentException("未知数据类型[" +
					// elementClassName + "].");
				}
			}
		}
		else if (requiredType.isEnum()) {
			if (Snum.class.isAssignableFrom(requiredType)) {
				String key = rs.getString(index);
				if (key == null) {
					return null;
				}
				try {
					return EnumUtil.toEnum(key, (Class<? extends Enum>) requiredType);
				}
				catch (EnumConstantInvalidException e) {
					if ("".equals(key)) {
						return null;
					}
					throw e;
				}
			}
			else if (Inum.class.isAssignableFrom(requiredType)) {
				int key = rs.getInt(index);
				return EnumUtil.toEnum(key, (Class<? extends Enum>) requiredType);
			}
			else {
				throw new RuntimeException("未知枚举类型[" + requiredType.getName() + "].");
			}
		}
		else {
			String json = rs.getString(index);
			try {
				value = Json.toObject(json, requiredType, true);
			}
			catch (JsonException e) {
				System.err.println("JsonException fieldName:" + field.getName() + " " + requiredType.getName() + " json:" + json);
				throw e;
			}
			// throw new SQLException("未知数据类型[" + requiredType.getName() +
			// "].");
		}
		return value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static <T> List<T> toEnumList(String json, Class<?> requiredType, Class<T> elementType) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		if (Snum.class.isAssignableFrom(elementType)) {
			List<String> keyList = Json.toListObject(json, String.class);
			List<T> list = new ArrayList<T>();
			for (String key : keyList) {
				T onum = (T) EnumUtil.toEnum(key, (Class<Enum>) elementType);
				list.add(onum);
			}
			return list;
		}
		else if (Inum.class.isAssignableFrom(elementType)) {
			List<Integer> keyList = Json.toListObject(json, Integer.class);
			// return Json.toListObject(json, elementType);
			List<T> list = new ArrayList<T>();
			for (Integer key : keyList) {
				T onum = (T) EnumUtil.toEnum(key, (Class<Enum>) elementType);
				list.add(onum);
			}
			return list;
		}
		else {
			throw new RuntimeException("未知枚举类型[" + requiredType.getName() + "].");
		}

	}

}
