package io.leopard.exporter;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
		int columnType = resultSetMetaData.getColumnType(index);
		// Object value = super.getColumnValue(rs, index, resultSetMetaData, columnName, field);
		Object value = rs.getObject(index);
		
		return fieldResolver.transformColumnValue(tableName, field, columnName, value);
	}
}
