package io.leopard.exporter;

import java.lang.reflect.Field;

/**
 * 属性解析器
 * 
 * @author 谭海潮
 *
 */
public interface FieldResolver {

	/**
	 * 解析字段类型
	 * 
	 * @param field
	 * @return
	 */
	FieldType resolveType(Field field);

	/**
	 * 转换列值
	 * 
	 * @param table
	 * @param fieldName
	 * @param columnName
	 * @param value
	 * @return
	 */
	Object transformColumnValue(String tableName, Field field, String columnName, Object value);

	/**
	 * 获取ID转换的表名
	 * 
	 * @param field
	 * @return null:表示不需要转换
	 */
	String getIdTransformTableName(Field field);

}
