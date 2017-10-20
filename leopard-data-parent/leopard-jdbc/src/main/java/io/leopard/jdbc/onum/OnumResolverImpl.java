package io.leopard.jdbc.onum;

import java.lang.reflect.Field;

import io.leopard.lang.inum.EnumConstantInvalidException;
import io.leopard.lang.inum.EnumUtil;

/**
 * 默认枚举解析器
 * 
 * @author 谭海潮
 *
 */
public class OnumResolverImpl implements OnumResolver {

	/**
	 * 找不到元素时，使用的枚举解析器
	 */
	private static OnumResolver constantNotFoundOnumResolver = null;

	public static void setConstantNotFoundOnumResolver(OnumResolver constantNotFoundOnumResolver) {
		OnumResolverImpl.constantNotFoundOnumResolver = constantNotFoundOnumResolver;
	}

	@Override
	public <E extends Enum<E>> E toEnum(Object key, Class<E> clazz, Field field) {
		// return EnumUtil.toEnum(key, clazz);
		E onum = EnumUtil.get(key, clazz);
		// System.err.println("constantNotFoundOnumResolver:" + constantNotFoundOnumResolver);
		if (onum == null && constantNotFoundOnumResolver != null) {
			onum = constantNotFoundOnumResolver.toEnum(key, clazz, field);
		}
		if (onum == null) {
			// System.err.println("key:" + key.getClass().getName());
			throw new EnumConstantInvalidException("枚举元素[" + key + "]不存在[" + clazz.getName() + "].");
		}
		return onum;
	}

}
