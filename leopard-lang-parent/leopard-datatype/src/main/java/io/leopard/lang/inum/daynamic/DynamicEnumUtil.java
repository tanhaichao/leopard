package io.leopard.lang.inum.daynamic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import io.leopard.lang.inum.EnumConstantInvalidException;

public class DynamicEnumUtil {

	protected static Constructor<?> findConstructor(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterTypes().length == 1) {
				return constructor;
			}
		}
		throw new RuntimeException("获取不到动态枚举默认构造函数.");
	}

	@SuppressWarnings("unchecked")
	public static <E extends DynamicEnum<?>> List<E> values(Class<E> clazz) {
		Constructor<?> constructor = findConstructor(clazz);
		// System.err.println("constructor:" + constructor.toGenericString());
		List<EnumConstant> list = DynamicEnum.allOf(clazz.getName());
		List<E> result = new ArrayList<>();
		for (EnumConstant constant : list) {
			E instance;
			try {
				// instance = (E) clazz.newInstance();
				Object[] initargs = new Object[constructor.getParameterTypes().length];
				if (initargs.length > 0) {
					initargs[0] = constant.getKey();
				}
				instance = (E) constructor.newInstance(initargs);
			}
			catch (InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			result.add(instance);
		}
		return result;
	}

	/**
	 * 根据ID转换为枚举(元素不存在会抛异常).
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E extends DynamicEnum<?>> E toEnum(Object key, Class<E> clazz) {
		EnumConstant constant = DynamicEnum.getConstant(clazz.getName(), key);
		if (constant == null) {
			throw new EnumConstantInvalidException("枚举元素[" + key + "]不存在[" + clazz.getName() + "].");
		}
		Constructor<?> constructor = findConstructor(clazz);

		E instance;
		try {
			// instance = (E) clazz.newInstance();
			Object[] initargs = new Object[constructor.getParameterTypes().length];
			if (initargs.length > 0) {
				initargs[0] = constant.getKey();
			}
			instance = (E) constructor.newInstance(initargs);
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return instance;
	}
}
