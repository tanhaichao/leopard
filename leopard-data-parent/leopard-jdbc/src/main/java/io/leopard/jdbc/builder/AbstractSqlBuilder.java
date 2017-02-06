package io.leopard.jdbc.builder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.jdbc.StatementParameter;

public abstract class AbstractSqlBuilder implements SqlBuilder {
	protected StatementParameter statementParameter = new StatementParameter();
	protected List<String> fieldList = new ArrayList<String>();

	/**
	 * 设置String类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setString(String fieldName, String value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}

		fieldList.add(fieldName);
		statementParameter.setString(value);
	}

	/**
	 * 设置List类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setList(String fieldName, List<?> value) {
		if (value == null) {
			// list默认允许传入null
			return;
		}
		fieldList.add(fieldName);
		statementParameter.setList(value);
	}

	/**
	 * 设置List类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setObject(String fieldName, Object value) {
		if (value == null) {
			// list默认允许传入null
			return;
		}
		fieldList.add(fieldName);
		statementParameter.setObject(value);
	}

	/**
	 * 设置Timestamp类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setTimestamp(String fieldName, Timestamp value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setTimestamp(value);
	}

	// /**
	// * 设置OnlyDate类型参数.
	// *
	// * @param fieldName
	// * 参数名
	// * @param value
	// * 参数值
	// */
	// public void setOnlyDate(String fieldName, OnlyDate value) {
	// fieldList.add(fieldName);
	// statementParameter.setOnlyDate(value);
	// }

	/**
	 * 设置boolean类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setBool(String fieldName, Boolean value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setBool(value);
	}

	/**
	 * 设置int类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setInt(String fieldName, Integer value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setInt(value);
	}

	/**
	 * 设置short类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setShort(String fieldName, Short value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setShort(value);
	}

	/**
	 * 设置Date类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setDate(String fieldName, Date value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setDate(value);
	}

	/**
	 * 设置long类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setLong(String fieldName, Long value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setLong(value);
	}

	public void setBytes(String fieldName, byte[] value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setBytes(value);
	}

	/**
	 * 设置float类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setFloat(String fieldName, Float value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setFloat(value);
	}

	/**
	 * 设置double类型参数.
	 * 
	 * @param fieldName 参数名
	 * @param value 参数值
	 */
	public void setDouble(String fieldName, Double value) {
		if (value == null) {
			throw new IllegalArgumentException("参数值[" + fieldName + "]不能为NULL.");
		}
		fieldList.add(fieldName);
		statementParameter.setDouble(value);
	}

	@Override
	public StatementParameter getParam() {
		return statementParameter;
	}

}
