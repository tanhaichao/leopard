package io.leopard.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
//import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;

import io.leopard.json.Json;

/**
 * SQL参数.
 * 
 * @author 阿海
 * 
 */
public class StatementParameter {

	private final List<Object> list = new ArrayList<Object>();
	private final List<Class<?>> type = new ArrayList<Class<?>>();

	protected void checkNull(Object value) {
		// AssertUtil.assertNotNull(value, "参数值[" + list.size() + "]不能为NULL.");
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + list.size() + "]不能为NULL.");
		}
	}

	/**
	 * 设置Date类型参数.
	 * 
	 * @param value
	 */
	public void setDate(Date value) {
		this.checkNull(value);
		list.add(value);
		type.add(Date.class);
	}

	// /**
	// * 设置OnlyDate类型参数.
	// *
	// * @param value
	// */
	// public void setOnlyDate(OnlyDate value) {
	// this.checkNull(value);
	// // long time = ((OnlyDate) value).getTime();
	// // list.add(new java.sql.Date(time));
	// list.add(value);
	// type.add(OnlyDate.class);
	// }

	// /**
	// * 设置OnlyDate类型参数.
	// *
	// * @param value
	// */
	// public void setMonth(Month value) {
	// this.checkNull(value);
	// list.add(value);
	// type.add(Month.class);
	// }

	/**
	 * 设置Timestamp类型参数.
	 * 
	 * @param value
	 */
	public void setTimestamp(Timestamp value) {
		this.checkNull(value);
		list.add(value);
		type.add(Timestamp.class);
	}

	/**
	 * 设置参数.
	 * 
	 * @param value
	 */
	public void setObject(Class<?> type, Object value) {
		if (type.equals(String.class)) {
			this.setString((String) value);
		}
		else if (type.equals(Integer.class) || type.equals(int.class)) {
			this.setInt((Integer) value);
		}
		else if (type.equals(Long.class) || type.equals(long.class)) {
			this.setLong((Long) value);
		}
		else if (type.equals(Float.class) || type.equals(float.class)) {
			this.setFloat((Float) value);
		}
		else if (type.equals(Double.class) || type.equals(double.class)) {
			this.setDouble((Double) value);
		}
		else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
			if (value instanceof Integer) {
				int num = (Integer) value;
				this.setBool(num == 1);
			}
			else {
				this.setBool((Boolean) value);
			}
		}
		// else if (type.equals(Month.class)) {
		// if (value instanceof String) {
		// String month = (String) value;
		// this.setMonth(new Month(month));
		// }
		// else {
		// this.setMonth((Month) value);
		// }
		// }
		else if (type.equals(Date.class)) {
			this.setDate((Date) value);
		}
		// else if (type.equals(OnlyDate.class)) {
		// if (value instanceof java.sql.Date) {
		// java.sql.Date date = (java.sql.Date) value;
		// this.setOnlyDate(new OnlyDate(date.getTime()));
		// }
		// else {
		// this.setOnlyDate((OnlyDate) value);
		// }
		// }
		else {
			throw new IllegalArgumentException("未知类型[" + type + "].");
		}
	}

	/**
	 * 设置String类型参数.
	 * 
	 * @param value
	 */
	public void setString(String value) {
		this.checkNull(value);
		list.add(value);
		type.add(String.class);
	}

	/**
	 * 设置List类型参数.
	 * 
	 * @param value
	 */
	public void setList(List<?> list) {
		this.checkNull(list);
		this.list.add(list);
		this.type.add(List.class);
	}

	/**
	 * 设置Object类型参数.
	 * 
	 * @param value
	 */
	public void setObject(Object obj) {
		this.checkNull(obj);
		this.list.add(obj);
		this.type.add(Object.class);
	}

	/**
	 * 设置Boolean类型参数.
	 * 
	 * @param value
	 */
	public void setBool(Boolean value) {
		this.checkNull(value);
		list.add(value);
		type.add(Boolean.class);
	}

	/**
	 * 设置Integer类型参数.
	 * 
	 * @param value
	 */
	public void setInt(Integer value) {
		this.checkNull(value);
		list.add(value);
		type.add(Integer.class);
	}

	/**
	 * 设置Short类型参数.
	 * 
	 * @param value
	 */
	public void setShort(Short value) {
		this.checkNull(value);
		list.add(value);
		type.add(Short.class);
	}

	public void setBytes(byte[] value) {
		this.checkNull(value);
		list.add(value);
		type.add(byte[].class);
	}

	/**
	 * 设置Long类型参数.
	 * 
	 * @param value
	 */
	public void setLong(Long value) {
		this.checkNull(value);
		list.add(value);
		type.add(Long.class);
	}

	/**
	 * 设置Double类型参数.
	 * 
	 * @param value
	 */
	public void setDouble(Double value) {
		this.checkNull(value);
		list.add(value);
		type.add(Double.class);
	}

	/**
	 * 设置Float类型参数.
	 * 
	 * @param value
	 */
	public void setFloat(Float value) {
		this.checkNull(value);
		list.add(value);
		type.add(Float.class);
	}

	/**
	 * 返回索引对应的Date类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public Date getDate(int index) {
		Object value = this.getObject(index);
		return (Date) value;
	}

	/**
	 * 返回索引对应的Timestamp类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public Timestamp getTimestamp(int index) {
		Object value = this.getObject(index);
		return (Timestamp) value;
	}

	/**
	 * 返回索引对应的String类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public String getString(int index) {
		Object value = this.getObject(index);
		return (String) value;
	}

	/**
	 * 返回索引对应的int类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public int getInt(int index) {
		Object value = this.getObject(index);
		return (Integer) value;
	}

	/**
	 * 返回索引对应的short类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public int getShort(int index) {
		Object value = this.getObject(index);
		return (Short) value;
	}

	/**
	 * 返回索引对应的float类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public float getFloat(int index) {
		Object value = this.getObject(index);
		return (Float) value;
	}

	/**
	 * 返回索引对应的long类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public long getLong(int index) {
		Object value = this.getObject(index);
		return (Long) value;
	}

	public byte[] getBytes(int index) {
		Object value = this.getObject(index);
		return (byte[]) value;
	}

	/**
	 * 返回索引对应的double类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public double getDouble(int index) {
		Object value = this.getObject(index);
		return (Double) value;
	}

	/**
	 * 返回索引对应的boolean类型参数值.
	 * 
	 * @param index 索引
	 * @return 参数值
	 */
	public Boolean getBool(int index) {
		Object value = this.getObject(index);
		return (Boolean) value;
	}

	// /**
	// * 返回索引对应的month类型参数值.
	// *
	// * @param index
	// * 索引
	// * @return 参数值
	// */
	// public Month getMonth(int index) {
	// Object value = this.getObject(index);
	// return (Month) value;
	// }

	/**
	 * 返回索引对应的参数.
	 * 
	 * @param index 索引
	 * @return 参数
	 */
	public Object getObject(int index) {
		return list.get(index);
	}

	public Class<?> getType(int index) {
		return type.get(index);
	}

	/**
	 * 返回索引对应参数的类型.
	 * 
	 * @param index 索引
	 * @return 参数类型
	 */
	// private int getTypes(int index) {
	// Object value = list.get(index);
	// return this.getTypes(value);
	// }
	/**
	 * 将参数List转成参数数组.
	 * 
	 * @return
	 */
	public Object[] getArgs() {
		Object[] values = new Object[list.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = this.getArg(i);
		}
		return values;
	}

	protected Object getArg(int index) {
		Object value = list.get(index);
		if (value == null) {
			return null;
		}
		Class<?> type = this.type.get(index);
		if (type.equals(String.class)) {
			return value;
		}
		else if (type.equals(Boolean.class)) {
			Boolean flag = (Boolean) value;
			return (int) (flag ? 1 : 0);
		}

		else if (type.equals(Integer.class)) {
			return value;
		}
		else if (type.equals(Short.class)) {
			return value;
		}
		else if (type.equals(Long.class)) {
			return value;
		}
		else if (type.equals(Float.class)) {
			return value;
		}
		else if (type.equals(Double.class)) {
			return value;
		}
		// else if (type.equals(Month.class)) {
		// return ((Month) value).toString();
		// // return (Month) value;
		// }

		// else if (type.equals(OnlyDate.class)) {
		// long time = ((OnlyDate) value).getTime();
		// return new java.sql.Date(time);
		// }
		else if (type.equals(Date.class)) {
			Date date = (Date) value;
			return new Timestamp(date.getTime());
		}
		else if (type.equals(Timestamp.class)) {
			return value;
		}
		else if (type.equals(List.class)) {
			return value;
		}
		else if (type.equals(byte[].class)) {
			return value;
		}
		else {
			// throw new IllegalArgumentException("未知类型[" + type.getName() + "].");
			return value;
		}
	}

	/**
	 * 获取参数类型数组 .
	 * 
	 * @return
	 */
	protected int[] getArgTypes() {
		// { Types.CHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR };
		int[] valuesTypes = new int[type.size()];
		Object[] types = type.toArray();
		for (int i = 0; i < types.length; i++) {
			valuesTypes[i] = this.getIntType(i);
		}
		return valuesTypes;
	}

	protected int getIntType(int index) {
		Class<?> type = this.type.get(index);
		if (type.equals(String.class)) {
			return Types.VARCHAR;
		}
		else if (type.equals(Boolean.class)) {
			return Types.INTEGER;
		}
		else if (type.equals(Integer.class)) {
			return Types.INTEGER;
		}
		else if (type.equals(Integer.class)) {
			return Types.INTEGER;
		}
		else if (type.equals(Long.class)) {
			return Types.BIGINT;
		}
		else if (type.equals(Float.class)) {
			return Types.FLOAT;
		}
		else if (type.equals(Double.class)) {
			return Types.DOUBLE;
		}
		else if (type.equals(Date.class)) {
			return Types.TIMESTAMP;
		}
		else if (type.equals(Timestamp.class)) {
			return Types.TIMESTAMP;
		}
		// else if (type.equals(OnlyDate.class)) {
		// return Types.DATE;
		// }
		// else if (type.equals(Month.class)) {
		// return Types.VARCHAR;
		// }
		else {
			throw new IllegalArgumentException("未知类型[" + type.getName() + "].");
		}
	}

	/**
	 * 参数列表大小.
	 * 
	 * @return
	 */
	public int size() {
		return list.size();
	}

	/**
	 * 生成PreparedStatementSetter对象.
	 * 
	 * @return PreparedStatementSetter
	 */
	public PreparedStatementSetter getParameters() {
		if (list.size() == 0) {
			return null;
		}
		PreparedStatementSetter param = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) {
				try {
					StatementParameter.this.setValues(pstmt);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}

		};
		return param;
	}

	/**
	 * 将参数列表添加到PreparedStatementSetter对象中.
	 * 
	 * @param pstmt PreparedStatementSetter对象
	 * @throws SQLException
	 */
	public void setValues(PreparedStatement pstmt) throws SQLException {
		int i = 1;
		for (Class<?> type : this.type) {
			this.setValues(pstmt, i, type);
			i++;
		}
	}

	protected void setValues(PreparedStatement pstmt, int i, Class<?> type) throws SQLException {
		Object value = this.getArg(i - 1);
		if (type.equals(String.class)) {
			pstmt.setString(i, (String) value);
		}
		else if (type.equals(Boolean.class)) {
			pstmt.setInt(i, (Integer) value);
		}
		else if (type.equals(Integer.class)) {
			pstmt.setInt(i, (Integer) value);
		}
		else if (type.equals(Long.class)) {
			pstmt.setLong(i, (Long) value);
		}
		else if (type.equals(Float.class)) {
			pstmt.setFloat(i, (Float) value);
		}
		else if (type.equals(Double.class)) {
			pstmt.setDouble(i, (Double) value);
		}
		// else if (type.equals(OnlyDate.class)) {
		// pstmt.setDate(i, (java.sql.Date) value);
		// }
		else if (type.equals(Date.class)) {
			pstmt.setTimestamp(i, (Timestamp) value);
		}
		else if (type.equals(Timestamp.class)) {
			pstmt.setTimestamp(i, (Timestamp) value);
		}
		else if (type.equals(byte[].class)) {
			pstmt.setBytes(i, (byte[]) value);
		}
		else if (type.equals(List.class)) {
			pstmt.setString(i, Json.toJson(value));
		}
		else {
			pstmt.setString(i, Json.toJson(value));
			// throw new InvalidParamDataAccessException("未知参数类型[" + i + ":" + type.getName() + "].");
		}
	}
}
