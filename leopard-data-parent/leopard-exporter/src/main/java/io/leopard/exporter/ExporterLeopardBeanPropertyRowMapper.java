package io.leopard.exporter;

import java.lang.reflect.Field;
import java.sql.ResultSet;
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
	protected Object getColumnValue(ResultSet rs, int index, String columnName, Field field) throws SQLException {
		Object value = super.getColumnValue(rs, index, columnName, field);
		// Object value = rs.getObject(index);
		return fieldResolver.transformColumnValue(tableName, field, columnName, value);
	}
}
