package io.leopard.test.xarg;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class XargResolverControllerImpl implements XargResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	private Map<String, Xarg> paramMap = new HashMap<String, Xarg>();

	public XargResolverControllerImpl() {

	}

	public void setBeanFactory(BeanFactory beanFactory) {
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
		Map<String, Xarg> map = factory.getBeansOfType(Xarg.class);
		for (Entry<String, Xarg> entry : map.entrySet()) {
			Xarg xarg = entry.getValue();
			// System.err.println("xarg:" + xarg);
			paramMap.put(xarg.getKey(), xarg);
		}
	}

	@Override
	public XargResolver match(MethodInvocation invocation, Class<?> clazz) {
		try {
			// 忽略java8的语法
			Method method = invocation.getMethod();
			CtClassUtil.getParameterNames(method);
		}
		catch (RuntimeException e) {
			return null;
		}

		boolean isController = clazz.getName().endsWith("Controller");
		// logger.info("match:" + invocation.getMethod().toGenericString() + " isController:" + isController + " clazz:" + clazz.getName());
		if (isController) {
			return this;
		}
		return null;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Exception {
		Object bean = invocation.getThis();
		Object[] args = invocation.getArguments();
		Method method = invocation.getMethod();
		String[] names = CtClassUtil.getParameterNames(method);

		for (int i = 0; i < names.length; i++) {
			Xarg xarg = paramMap.get(names[i]);
			if (xarg == null) {
				continue;
			}
			args[i] = xarg.getValue(null, args[i]);
		}

		method.setAccessible(true);
		// logger.info("invoke:" + invocation.getMethod().toGenericString());
		return method.invoke(bean, args);
	}

}
