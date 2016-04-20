package io.leopard.aop;

import io.leopard.aop.matcher.BeanMatcher;
import io.leopard.aop.matcher.BeanMatcherImpl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

public class LeopardAopBeanPostProcessor implements BeanPostProcessor {

	private BeanMatcher beanMatcher = new BeanMatcherImpl();

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		boolean matche = beanMatcher.matche(bean, beanName, bean.getClass().getName());
		if (matche) {
			// 如果是代理的类
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(bean.getClass());
			enhancer.setCallback(new LeopardAopMethodInterceptor(bean));
			// return enhancer.create();
			return bean;
		}
		else {
			return bean;
		}

	}

	public Object postProcessBeforeInitialization(Object bean, String beanNames) throws BeansException {
		return bean;
	}
}
