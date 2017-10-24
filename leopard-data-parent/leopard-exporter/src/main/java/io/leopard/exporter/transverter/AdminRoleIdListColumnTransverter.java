package io.leopard.exporter.transverter;

import java.lang.reflect.Field;

import io.leopard.exporter.ColumnTransverter;

public class AdminRoleIdListColumnTransverter extends ColumnTransverter {

	@Override
	public Object transform(String tableName, Field field, String columnName, Object value) {
		System.err.println("value:" + value);
		return null;
	}

}
