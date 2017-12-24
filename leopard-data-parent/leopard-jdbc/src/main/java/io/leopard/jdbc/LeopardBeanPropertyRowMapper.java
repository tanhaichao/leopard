package io.leopard.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import io.leopard.jdbc.onum.OnumResolver;
import io.leopard.jdbc.onum.OnumResolverImpl;
import io.leopard.json.Json;
import io.leopard.json.JsonException;
import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.EnumConstantInvalidException;
import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;
import io.leopard.lang.inum.daynamic.DynamicEnum;
import io.leopard.lang.inum.daynamic.DynamicEnumUtil;

public class LeopardBeanPropertyRowMapper<T> implements RowMapper<T> {

	private static OnumResolver onumResolver = new OnumResolverImpl();

	private Class<T> mappedClass;

	protected Map<String, Field> mappedFields;

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
			String columnName = JdbcUtils.lookupColumnName(rsmd, index);
			// System.err.println("column:" + column);
			columnName = columnName.replaceAll(" ", "").toLowerCase();
			columnName = columnName.replace("_", "");

			Field field = this.mappedFields.get(columnName);

			// if (field == null) {
			// this.mappedFields.get(underscoreName(column));
			// }
			// System.out.println("column:" + column + " field:" + field);

			if (field == null && columnName.endsWith("s")) {
				// TODO images转imageList的临时实现?
				String column2 = columnName.substring(0, columnName.length() - 1) + "list";
				field = this.mappedFields.get(column2);
				// System.out.println("column2:" + column2 + " field:" + field);
			}

			if (field != null) {
				Object value = getColumnValue(rs, index, rsmd, columnName, field);

				field.setAccessible(true);
				try {
					field.set(bean, value);
				}
				// catch (IllegalArgumentException e) {
				// throw new SQLException(e.getMessage(), e);
				// }
				catch (IllegalAccessException e) {
					throw new SQLException(e.getMessage(), e);
				}
			}
		}
		return bean;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getColumnValue(ResultSet rs, int index, ResultSetMetaData resultSetMetaData, String columnName, Field field) throws SQLException {
		Class<?> requiredType = field.getType();
		// JdbcUtils.getResultSetValue(rs, index, requiredType);// TODO 20171012 这是多余的代码吧?
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
			if (timestamp == null) {
				return null;
			}
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
			// System.out.println("name:" + field.getName() + " json:" + json);
			ParameterizedType type = (ParameterizedType) field.getGenericType();
			String elementClassName = type.getActualTypeArguments()[0].getTypeName();
			if (int.class.getName().equals(elementClassName) || Integer.class.getName().equals(elementClassName)) {
				String json = rs.getString(index);
				value = Json.toListObject(json, Integer.class);
			}
			else if (long.class.getName().equals(elementClassName) || Long.class.getName().equals(elementClassName)) {
				String json = rs.getString(index);
				value = Json.toListObject(json, Long.class);
			}
			else if (String.class.getName().equals(elementClassName)) {
				String json = rs.getString(index);
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
					String json = rs.getString(index);
					return toEnumList(json, elementType, field);
				}
				else {
					Class<?> clazz = (Class<?>) type.getActualTypeArguments()[0];
					String json = rs.getString(index);
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
					return onumResolver.toEnum(key, (Class<? extends Enum>) requiredType, field);
					// return EnumUtil.toEnum(key, (Class<? extends Enum>) requiredType);
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
				return onumResolver.toEnum(key, (Class<? extends Enum>) requiredType, field);
				// return EnumUtil.toEnum(key, (Class<? extends Enum>) requiredType);
			}
			else if (Bnum.class.isAssignableFrom(requiredType)) {
				byte key = rs.getByte(index);
				return onumResolver.toEnum(key, (Class<? extends Enum>) requiredType, field);
				// return EnumUtil.toEnum(key, (Class<? extends Enum>) requiredType);
			}
			else {
				throw new RuntimeException("未知枚举类型[" + requiredType.getName() + "].");
			}
		}
		else if (DynamicEnum.class.isAssignableFrom(requiredType)) {
			if (Snum.class.isAssignableFrom(requiredType)) {
				String key = rs.getString(index);
				if (key == null) {
					return null;
				}
				try {
					return DynamicEnumUtil.toEnum(key, (Class<? extends DynamicEnum>) requiredType);
				}
				catch (EnumConstantInvalidException e) {
					// e.printStackTrace();
					if ("".equals(key)) {
						return null;
					}
					throw e;
				}
			}
			else if (Inum.class.isAssignableFrom(requiredType)) {
				int key = rs.getInt(index);
				// return EnumUtil.toEnum(key, (Class<? extends Enum>) requiredType);
				return DynamicEnumUtil.toEnum(key, (Class<? extends DynamicEnum>) requiredType);
			}
			else if (Bnum.class.isAssignableFrom(requiredType)) {
				byte key = rs.getByte(index);
				// return EnumUtil.toEnum(key, (Class<? extends Enum>) requiredType);
				return DynamicEnumUtil.toEnum(key, (Class<? extends DynamicEnum>) requiredType);
			}
			else {
				throw new RuntimeException("未知枚举类型[" + requiredType.getName() + "].");
			}
		}

		else if (Object.class.equals(requiredType)) {
			throw new RuntimeException("实体类的字段类型不能使用Object.");
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
	protected static <T> List<T> toEnumList(String json, Class<T> elementType, Field field) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		if (Snum.class.isAssignableFrom(elementType)) {
			List<String> keyList = Json.toListObject(json, String.class);
			List<T> list = new ArrayList<T>();
			for (String key : keyList) {
				// T onum = (T) EnumUtil.toEnum(key, (Class<Enum>) elementType);
				T onum = (T) onumResolver.toEnum(key, (Class<Enum>) elementType, field);
				list.add(onum);
			}
			return list;
		}
		else if (Inum.class.isAssignableFrom(elementType)) {
			if (isNumeric(json)) {// 枚举位运算
				return toBitEnumList(json, elementType, field);
			}
			else {
				List<T> list = new ArrayList<T>();
				List<Integer> keyList = Json.toListObject(json, Integer.class);
				for (Integer key : keyList) {
					T onum = (T) onumResolver.toEnum(key, (Class<Enum>) elementType, field);
					list.add(onum);
				}
				return list;
			}
		}
		else {
			throw new RuntimeException("未知枚举类型[" + elementType.getName() + "].");
		}
	}

	/**
	 * 枚举位运算，将数据库中的数字转换成枚举列表
	 * 
	 * @param json
	 * @param elementType
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static <T> List<T> toBitEnumList(String json, Class<T> elementType, Field field) {
		int value = Integer.parseInt(json);

		@SuppressWarnings({ "rawtypes" })
		Map<Object, Enum<?>> map = EnumUtil.toMap((Class<Enum>) elementType);
		Collection<Enum<?>> constants = map.values();
		List<T> list = new ArrayList<T>();
		for (Enum<?> constant : constants) {
			Inum inum = (Inum) constant;
			int constantKey = inum.getKey();
			if ((constantKey & value) > 0) {
				list.add((T) constant);
			}
		}
		return list;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param json
	 * @return
	 */
	protected static boolean isNumeric(String json) {
		Pattern pattern = Pattern.compile("^[0-9]+");
		Matcher m = pattern.matcher(json);
		return m.matches();
	}
}
