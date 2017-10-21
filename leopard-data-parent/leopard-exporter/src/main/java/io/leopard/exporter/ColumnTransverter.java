package io.leopard.exporter;

/**
 * 字段值转换器
 * 
 * @author 谭海潮
 *
 */
public abstract class ColumnTransverter {

	public abstract Object transform(String tableName, String fieldName, String columnName, Object value);

	public abstract static class None extends ColumnTransverter {
	}
}
