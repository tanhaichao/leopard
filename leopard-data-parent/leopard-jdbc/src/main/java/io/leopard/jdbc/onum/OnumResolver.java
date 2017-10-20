package io.leopard.jdbc.onum;

import java.lang.reflect.Field;

/**
 * 枚举解析器
 * 
 * @author 谭海潮
 *
 */
public interface OnumResolver {
	<E extends Enum<E>> E toEnum(Object key, Class<E> clazz, Field field);
}
