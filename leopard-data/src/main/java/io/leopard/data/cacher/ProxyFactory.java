package io.leopard.data.cacher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ProxyFactory {

	private final Map<Long, Object> beansCache = new ConcurrentHashMap<Long, Object>();

	protected <T> long getKey(final T... beans) {
		beans[0].getClass().getName();
		long num = 0;
		for (T bean : beans) {
			num += bean.hashCode();
		}
		return num;
	}

	@SuppressWarnings("unchecked")
	public <T> T getInstance(final T... beans) {
		Long key = getKey(beans);
		T proxy = (T) beansCache.get(key);
		if (proxy == null) {
			synchronized (key) {
				if (beansCache.containsKey(key)) {
					proxy = (T) beansCache.get(key);
				}
				else {
					proxy = this.proxy(beans[0]);
					beansCache.put(key, proxy);
				}
			}
		}
		return proxy;
	}

	@SuppressWarnings("unchecked")
	private <T> Class<T> getRealClass(T bean) {
		Class<T> clazz = (Class<T>) bean.getClass();
		if (clazz.getName().indexOf("$$EnhancerByMockito") == -1) {
			return clazz;
		}
		else {
			return (Class<T>) clazz.getSuperclass();
		}
		// bean.getClass().getSuperclass()
	}

	private <T> T proxy(final T bean) {
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object result = method.invoke(bean, args);
				if (result == null) {
					result = ProxyFactory.this.invoke(bean, args);
				}
				return result;
			}
		};
		Class<T> clazz = this.getRealClass(bean);// 兼容mock
		@SuppressWarnings("unchecked")
		T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), invocationHandler);
		return proxy;
		// return bean;
	}

	public abstract <T> Object invoke(T bean, Object[] args);

}
