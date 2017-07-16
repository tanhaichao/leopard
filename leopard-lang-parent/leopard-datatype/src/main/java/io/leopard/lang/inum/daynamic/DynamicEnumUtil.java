package io.leopard.lang.inum.daynamic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DynamicEnumUtil {

	@SuppressWarnings("unchecked")
	public static <E extends DynamicEnum<?>> List<E> values(Class<E> clazz) {
		Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
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

}
