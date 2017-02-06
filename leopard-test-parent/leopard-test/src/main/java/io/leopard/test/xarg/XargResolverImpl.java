package io.leopard.test.xarg;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.BeanFactory;

public class XargResolverImpl implements XargResolver {

	private List<XargResolver> list = new ArrayList<XargResolver>();

	public XargResolverImpl() {
		list.add(new XargResolverControllerImpl());
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		for (XargResolver resolver : list) {
			resolver.setBeanFactory(beanFactory);
		}
	}

	@Override
	public XargResolver match(MethodInvocation invocation, Class<?> clazz) {
		for (XargResolver resolver : list) {
			XargResolver resolver2 = resolver.match(invocation, clazz);
			if (resolver2 != null) {
				return resolver2;
			}
		}
		return null;
	}

	@Override
	public Object invoke(MethodInvocation invocation) {
		throw new NotImplementedException();
	}

}
