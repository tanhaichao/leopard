package io.leopard.topnb.methodtime;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;

import io.leopard.topnb.TopnbBeanFactory;

/**
 * 方法耗时监控.
 * 
 * @author 阿海
 *
 */
public class MethodTimeInterceptor extends BeanNameAutoProxyCreator implements MethodInterceptor {

	private static final long serialVersionUID = 1L;

	private final MethodTimeService methodTimeService = TopnbBeanFactory.getMethodTimeService();

	private static boolean isCreated = false;

	public MethodTimeInterceptor() {
		if (isCreated) {
			throw new IllegalArgumentException("方法耗时监控系统已经启动.");
		}

		boolean isJunit = isJunit();
		if (!isJunit) {
			String[] beanNames = new String[] { "*Dao"//
					, "*DaoMysqlImpl"//
					, "*DaoMemcachedImpl"//
					, "*DaoCacheImpl"//
					, "*DaoRedisImpl"//
					, "*DaoHttpImpl"//
					, "*DaoOutsideImpl"//
					, "*DaoOtherImpl"//
					, "*DaoThriftImpl"//
					, "*DaoFileImpl"//
					, "*DaoImpl"//
					, "*DaoMemoryImpl"//
					, "*Service"//
					, "*Controller"//
			};
			this.setBeanNames(beanNames);
		}
		this.setProxyTargetClass(true);
		this.setInterceptorNames(new String[] { "topnbInterceptor" });
	}

	protected boolean isJunit() {
		// 改成判断是否web容器启动更好?RequestContextHolder.getRequestAttributes() == null?

		// new Exception().printStackTrace();
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : elements) {
			if (element.getClassName().equals("org.junit.runners.ParentRunner")) {
				return true;
			}
		}
		return false;
	}

	private static Set<String> IGNORED_BEAN_NAME_SET = new HashSet<String>();

	static {
		IGNORED_BEAN_NAME_SET.add("conversionService");
	}

	@Override
	protected boolean isMatch(String beanName, String mappedName) {

		if (IGNORED_BEAN_NAME_SET.contains(beanName)) {
			return false;
		}

		return super.isMatch(beanName, mappedName);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		long startTime = System.nanoTime();
		Object result = invocation.proceed();
		long endTime = System.nanoTime();
		long time = (endTime - startTime);
		Object target = invocation.getThis();

		String className = getLongClassName(target);
		String methodName = getLongMethodName(className, method.getName());

		// logger.info("invoke methodName:" + method.getName() + " longMethodName:" + methodName);

		methodTimeService.add(methodName, time);
		return result;
	}

	public static String getLongClassName(Object target) {
		return target.getClass().getName();
	}

	public static String getLongMethodName(String className, String methodName) {
		// String className = target.getClass().getSimpleName();
		StringBuilder sb = new StringBuilder(className.length() + methodName.length() + 1);
		// return className + "." + methodName;
		sb.append(className).append('.').append(methodName);
		return sb.toString();
	}

}
