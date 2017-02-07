package io.leopard.topnb;

import io.leopard.topnb.methodtime.PerformanceUtilTest;
import io.leopard.topnb.methodtime.MethodTimeInterceptor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Assert;
import org.junit.Test;

public class TopnbInterceptorTest {

	@Test
	public void PerformanceMonitorInterceptor() {
		new MethodTimeInterceptor();
		// Assert.assertTrue(TopnbInterceptor.isEnableMonitor());
	}

	// @Test
	// public void getContext() {
	// Assert.assertNotNull(TopnbInterceptor.getContext("applicationContext.xml"));
	// Assert.assertNotNull(TopnbInterceptor.getContext("applicationContext.xml", false));
	// Assert.assertNotNull(TopnbInterceptor.getContext("applicationContext.xml", true));
	// }

	@Test
	public void invoke() throws Throwable {
		MethodInvocation invocation = new MethodInvocation() {

			@Override
			public Object[] getArguments() {
				return null;
			}

			@Override
			public AccessibleObject getStaticPart() {
				return null;
			}

			@Override
			public Object getThis() {
				return TopnbInterceptorTest.this;
			}

			@Override
			public Object proceed() throws Throwable {
				return "ok";
			}

			@Override
			public Method getMethod() {
				try {
					return TopnbInterceptorTest.class.getMethod("invoke");
				}
				catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}

		};

		Object result = new MethodTimeInterceptor().invoke(invocation);
		Assert.assertEquals("ok", result);

	}

	@Test
	public void getLongClassName() {
		Assert.assertEquals("io.leopard.monitor.performance.util.PerformanceUtilTest", MethodTimeInterceptor.getLongClassName(new PerformanceUtilTest()));
	}

	@Test
	public void getLongMethodName() {
		String methodName = MethodTimeInterceptor.getLongMethodName("PerformanceUtilTest", "getLongMethodName");
		System.out.println("methodName:" + methodName);
		Assert.assertEquals("PerformanceUtilTest.getLongMethodName", methodName);
	}

}