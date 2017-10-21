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
	 * 获取ID转换的表名
	 * 
	 * @param field
	 * @return null:表示不需要转换
	 */
	String getIdTransformTableName(Field field);

}
