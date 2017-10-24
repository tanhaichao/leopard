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
public class AutoIdColumnTransverter extends ColumnTransverter<Object> {

	@Override
	public Object transform(String tableName, Field field, String columnName, Object value) {

		System.err.println("AutoIdColumnTransverter transform:" + value);
		// TODO 没有存储数据
		Class<?> type = field.getType();
		if (type.equals(String.class)) {
			if (value != null) {
				return value;
			}
			return StringUtil.uuid();
		}
		else if (type.equals(long.class)) {
			long id = (long) value;
			if (id > 0) {
				return value;
			}
			return (long) NumberUtil.random(10000000);
		}
		else {
			throw new RuntimeException("未知主键数据类型[" + type.getSimpleName() + "].");
		}
	}

}
