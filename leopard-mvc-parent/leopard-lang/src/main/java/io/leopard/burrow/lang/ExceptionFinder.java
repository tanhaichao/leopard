package io.leopard.burrow.lang;

import java.lang.reflect.Constructor;

import io.leopard.core.exception.InvalidException;

public class ExceptionFinder {

	public static InvalidException invalid(String className, String param) {
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		try {
			Constructor<?> constructor = clazz.getConstructor(String.class);
			return (InvalidException) constructor.newInstance(param);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}
}
