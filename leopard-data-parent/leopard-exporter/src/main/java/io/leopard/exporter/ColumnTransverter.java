package io.leopard.exporter;

import java.lang.reflect.Field;

/**
 * 字段值转换器
 * 
 * @author 谭海潮
 *
 */
public abstract class ColumnTransverter<T> {

	public abstract Object transform(String tableName, Field field, String columnName, T value);

	public abstract static class None extends ColumnTransverter<Object> {

	}
}
