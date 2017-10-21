package io.leopard.exporter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;
import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.EnumConstantInvalidException;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;
import io.leopard.lang.inum.daynamic.DynamicEnum;
import io.leopard.lang.inum.daynamic.DynamicEnumUtil;

/**
 * 属性类型解析器
 * 
 * @author 谭海潮
 *
 */
public class FieldTypeResolverImpl implements FieldTypeResolver {

	@Override
	public FieldType resolveType(Field field) {
		Class<?> type = field.getType();
		if (type.equals(String.class)) {
			return FieldType.STRING;
		}
		else if (type.equals(List.class)) {
			// TODO这里要判断是否枚举位运算
			return FieldType.JSON;
		}
		else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return FieldType.BOOLEAN;
		}
		else if (type.equals(int.class) || type.equals(Integer.class)) {
			return FieldType.INTEGER;
		}
		else if (type.equals(long.class) || type.equals(Long.class)) {
			return FieldType.LONG;
		}
		else if (type.equals(float.class) || type.equals(Float.class)) {
			return FieldType.FLOAT;
		}
		else if (type.equals(double.class) || type.equals(Double.class)) {
			return FieldType.DOUBLE;
		}
		else if (type.equals(Date.class)) {
			return FieldType.DATE;
		}
		else if (type.equals(Timestamp.class)) {
			return FieldType.TIMESTAMP;
		}
		else if (type.equals(OnlyDate.class)) {
			return FieldType.ONLY_DATE;
		}
		else if (type.equals(Month.class)) {
			return FieldType.MONTH;
		}
		else if (type.isEnum()) {
			if (Snum.class.isAssignableFrom(type)) {
				return FieldType.SNUM;
			}
			else if (Inum.class.isAssignableFrom(type)) {
				return FieldType.INUM;
			}
			else if (Bnum.class.isAssignableFrom(type)) {
				return FieldType.BNUM;
			}
			else {
				throw new RuntimeException("未知枚举类型[" + type.getSimpleName() + "].");
			}
		}
		else if (DynamicEnum.class.isAssignableFrom(type)) {
			if (Snum.class.isAssignableFrom(type)) {
				return FieldType.SNUM;
			}
			else if (Inum.class.isAssignableFrom(type)) {
				return FieldType.INUM;
			}
			else if (Bnum.class.isAssignableFrom(type)) {
				return FieldType.BNUM;
			}
			else {
				throw new RuntimeException("未知枚举类型[" + type.getSimpleName() + "].");
			}
		}
		else if (Object.class.equals(type)) {
			throw new RuntimeException("实体类的字段类型不能使用Object.");
		}
		else {
			throw new IllegalArgumentException("未知类型[" + type.getName() + "].");
		}
	}

}
