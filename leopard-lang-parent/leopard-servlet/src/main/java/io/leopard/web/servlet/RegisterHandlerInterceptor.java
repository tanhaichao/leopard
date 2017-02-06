package io.leopard.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.Mergeable;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

//TODO ahai 在site项目必须实现BeanPostProcessor接口才能成功配置拦截器.
public abstract class RegisterHandlerInterceptor implements HandlerInterceptor, BeanFactoryAware, BeanPostProcessor, Mergeable {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	protected boolean isHandlerMapping(BeanDefinition beanDefinition) {
		String beanClassName = beanDefinition.getBeanClassName();
		if (beanClassName == null) {
			// throw new NullPointerException("beanDefinition:" + beanDefinition);
			return false;
		}
		Class<?> clazz;
		try {
			clazz = Class.forName(beanClassName);
		}
		catch (NoClassDefFoundError e) {
			e.printStackTrace();
			return false;
		}
		catch (ClassNotFoundException e) {
			return false;
		}
		if (RequestMappingHandlerMapping.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否注册.
	 * 
	 * @return
	 */
	protected boolean isRegister() {
		return true;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (!this.isRegister()) {
			return;
		}
		ConfigurableListableBeanFactory factory = ((ConfigurableListableBeanFactory) beanFactory);
		for (String beanName : factory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
			boolean isHandlerMapping = isHandlerMapping(beanDefinition);
			if (isHandlerMapping) {
				// System.out.println("setBeanFactory postProcessBeanFactory BeanClassName:" + beanDefinition.getBeanClassName() + " class:" + this.getClass().getName());
				MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
				propertyValues.addPropertyValue("interceptors", this);
			}
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public boolean isMergeEnabled() {
		return true;
	}

	@Override
	public Object merge(Object parent) {
		if (parent instanceof Object[]) {
			Object[] interceptors = (Object[]) parent;
			Object[] args = new Object[interceptors.length + 1];
			System.arraycopy(interceptors, 0, args, 1, interceptors.length);
			args[0] = this;
			return args;
		}
		else {
			return new Object[] { this, parent };
		}
	}
}
