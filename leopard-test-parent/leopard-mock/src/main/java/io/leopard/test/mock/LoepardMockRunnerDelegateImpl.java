package io.leopard.test.mock;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.internal.runners.InitializationError;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.spi.PowerMockTestListener;
import org.powermock.modules.junit4.internal.impl.PowerMockJUnit47RunnerDelegateImpl;

@SuppressWarnings("deprecation")
public class LoepardMockRunnerDelegateImpl extends PowerMockJUnit47RunnerDelegateImpl {

	public LoepardMockRunnerDelegateImpl(Class<?> klass) throws InitializationError {
		super(klass);
	}

	public LoepardMockRunnerDelegateImpl(Class<?> klass, String[] methodsToRun, PowerMockTestListener[] listeners) throws InitializationError {
		super(klass, methodsToRun, listeners);
	}

	// public LoepardMockRunnerDelegateImpl(Class<?> klass, String[]
	// methodsToRun) throws InitializationError {
	// super(klass, methodsToRun);
	// }

	@Override
	protected void invokeTestMethod(final Method method, RunNotifier notifier) {
		// System.out.println("method:" + method);
		this.autoMockStatic();// 自动mock静态类.
		super.invokeTestMethod(method, notifier);
	}

	/**
	 * 自动mock静态类.
	 */
	protected void autoMockStatic() {
		Description description = getDescription();
		PrepareForTest prepareForTest = description.getAnnotation(PrepareForTest.class);
		// System.out.println("prepareForTest:" + prepareForTest);
		if (prepareForTest == null) {
			return;
		}
		Class<?>[] classes = prepareForTest.value();
		mockStatic(classes);
	}

	protected void mockStatic(Class<?>[] classes) {
		if (classes != null) {
			Map<String, String> mapping = new HashMap<String, String>();
			for (Class<?> clazz : classes) {
				mapping.put(clazz.getSimpleName(), clazz.getName());
				// System.err.println("clazz:" + clazz);
				PowerMockito.mockStatic(clazz);
			}
			MockStatic.setStaticClassName(mapping);
		}
	}
}
