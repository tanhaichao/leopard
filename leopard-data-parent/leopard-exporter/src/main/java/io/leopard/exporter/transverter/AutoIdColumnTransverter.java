package io.leopard.exporter.transverter;

import java.lang.reflect.Field;

import io.leopard.burrow.util.NumberUtil;
import io.leopard.burrow.util.StringUtil;
import io.leopard.exporter.ColumnTransverter;

/**
 * 自动
 * 
 * @author 谭海潮
 *
 */
public class AutoIdColumnTransverter extends ColumnTransverter {

	@Override
	public Object transform(String tableName, Field field, String columnName, Object value) {
		if (value != null) {
			return value;
		}
		// TODO 没有存储数据
		Class<?> type = field.getType();
		if (type.equals(String.class)) {
			return StringUtil.uuid();
		}
		else if (type.equals(long.class)) {
			return (long) NumberUtil.random(10000000);
		}
		else {
			throw new RuntimeException("未知主键数据类型[" + type.getSimpleName() + "].");
		}
	}

}
