package io.leopard.test.mock;

import java.lang.reflect.Field;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

public class LeopardMockito {

	public static <T> T newInstance(Class<T> clazz, Object... mocks) {
		T service;
		try {
			service = clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		for (Object mock : mocks) {
			setProperty(service, mock);
		}
		return service;
	}

	protected static Class<?> getRealClass(Object mock) {
		Class<?> clazz = mock.getClass();
		// clazz:io.leopard.web.mvc.controller.LeopardStatusService$$EnhancerByMockitoWithCGLIB$$f6c7b7fd
		String className = clazz.getName();
		int index = className.indexOf("$$");
		if (index != -1) {
			className = className.substring(0, index);
		}

		if (className.startsWith("$")) {
			className = className.substring(1);
		}
		System.err.println("mock:" + mock + " className:" + className);
		return classForName(className);
	}

	public static Class<?> classForName(String className) {
		try {
			return Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected static String getRealClassName(Object mock) {
		Class<?> clazz = mock.getClass();
		// clazz:io.leopard.web.mvc.controller.LeopardStatusService$$EnhancerByMockitoWithCGLIB$$f6c7b7fd
		String className = clazz.getName();
		int index = className.indexOf("$$");
		if (index == -1) {
			return className;
		}
		else {
			return className.substring(0, index);
		}
		// return className;
	}

	public static void setProperty(Object service, Object mock, String fieldName) {
		try {
			Field field = getRealClass(service).getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(service, mock);
		}
		catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void setProperty(Object service, Object mock) {
		Class<?> serviceClazz = getRealClass(service);
		// Class<?> clazz = mock.getClass();
		setProperty(service, mock, serviceClazz);
	}

	public static void setProperty(Object service, Object mock, Class<?> serviceClazz) {
		// Class<?> clazz = mock.getClass();
		Class<?> mockClazz = getRealClass(mock);
		Field[] fields = serviceClazz.getDeclaredFields();
		for (Field field : fields) {
			// System.out.println("field:" + field.getType().getName());
			// System.out.println("mockClazz:" + mockClazz);
			if (field.getType().isAssignableFrom(mockClazz)) {
				field.setAccessible(true);
				try {
					field.set(service, mock);
				}
				catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	public static <T> T mock(Class<T> classToMock, Object testObject) {
		T mock = Mockito.mock(classToMock);
		return mock;
	}

	/**
	 * 
	 * @param times
	 *            次数
	 */
	public static synchronized void verifyStatic(int times) {
		PowerMockito.verifyStatic(Mockito.times(times));
	}

	public static <T> T verify(T mock, int times) {
		return Mockito.verify(mock, Mockito.times(times));
	}
}
