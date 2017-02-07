package io.leopard.web.mvc.option;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class OptionFactoryBean<T> implements InitializingBean, FactoryBean<T> {
	private String id;

	private String innerClassName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInnerClassName() {
		return innerClassName;
	}

	public void setInnerClassName(String innerClassName) {
		this.innerClassName = innerClassName;
	}

	public T getObject() throws Exception {
		// Class innerClass = Class.forName(innerClassName);
		// if (innerClass.isInterface()) {
		// return (T) InterfaceProxy.newInstance(innerClass);
		// }
		// else {
		// Enhancer enhancer = new Enhancer();
		// enhancer.setSuperclass(innerClass);
		// enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
		// enhancer.setCallback(new MethodInterceptorImpl());
		// return (T) enhancer.create();
		// }
		return null;
	}

	public Class<?> getObjectType() {
		// try {
		// return Class.forName(innerClassName);
		// }
		// catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// }
		return null;
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
	}

}
