package io.leopard.sysconfig.dynamicenum;

import java.lang.reflect.Constructor;
import java.util.EnumSet;

import org.apache.commons.lang.enums.EnumUtils;

public class DynamicEnumModifier {
	// private static final String MODIFIERS_FIELD = "modifiers";
	// private static final ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();

	public static void addConstant(Class<?> clazz, String key, String desc) throws Exception {
		System.out.println("clazz:" + clazz.getDeclaredConstructors()[0].toGenericString());
		Constructor cstr = clazz.getDeclaredConstructor(String.class, int.class);
		cstr.setAccessible(true);
		for (Object constant : clazz.getEnumConstants()) {
			System.out.println("constant:" + constant);
		}
		// clazz.gete
		Object constant = cstr.newInstance("key1", 0);
		System.out.println("constant:" + constant);
		
		
		

	}
}
