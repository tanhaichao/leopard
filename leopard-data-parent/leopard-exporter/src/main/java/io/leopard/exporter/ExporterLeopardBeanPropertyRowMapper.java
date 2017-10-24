package io.leopard.exporter;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import io.leopard.exporter.annotation.Column;
import io.leopard.jdbc.LeopardBeanPropertyRowMapper;

public class ExporterLeopardBeanPropertyRowMapper<T> extends LeopardBeanPropertyRowMapper<T> {

	private static FieldResolver fieldResolver = FieldResolverImpl.getInstance();

	private String tableName;

	public ExporterLeopardBeanPropertyRowMapper(Class<T> mappedClass, String tableName) {
		super(mappedClass);
		this.tableName = tableName;
	}

	@Override
	protected Object getColumnValue(ResultSet rs, int index, ResultSetMetaData resultSetMetaData, String columnName, Field field) throws SQLException {
		// Object value = super.getColumnValue(rs, index, resultSetMetaData, columnName, field);
		// System.err.println("transformColumnValue tableName:" + tableName + " columnName:" + columnName + " value:" + value);
		ColumnTransverter transverter = this.getColumnTransverter(field);
		// System.err.println("field:" + field.getName() + " transverter:" + transverter);
		if (transverter == null) {
			return super.getColumnValue(rs, index, resultSetMetaData, columnName, field);
		}
		// int columnType = resultSetMetaData.getColumnType(index);
		Object value = rs.getObject(index);
		return transverter.transform(tableName, field, columnName, value);
		// return fieldResolver.transformColumnValue(tableName, field, columnName, value);
	}

	/**
	 * 获取列值转换器
	 * 
	 * @param field
	 * @return
	 */
	protected ColumnTransverter getColumnTransverter(Field field) {
		Column column = field.getAnnotation(Column.class);
		if (column == null) {
			return null;
		}

		Class<? extends ColumnTransverter> clazz = column.transverter();
		if (clazz.equals(ColumnTransverter.None.class)) {
			return null;
		}
		ColumnTransverter transverter;
		try {
			transverter = clazz.newInstance();
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return transverter;
	}

}
