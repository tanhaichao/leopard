package io.leopard.data;

import io.leopard.burrow.AutoResource;
import io.leopard.burrow.DefaultBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Primary;

public class AutoResourceBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	protected ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		Class<?> clazz = bean.getClass();
		// if (clazz.getName().endsWith("PassportInterceptor")) {
		// System.err.println("PassportInterceptor:" + bean);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// System.err.println("beanName:" + beanName + " field:" + field.getName());
			AutoResource annotation = field.getAnnotation(AutoResource.class);
			if (annotation == null) {
				continue;
			}
			// System.err.println("beanName:" + beanName + " annotation:" + annotation);
			field.setAccessible(true);
			try {
				Object value = field.get(bean);
				if (value == null) {
					value = this.getBean(field.getType());
					field.set(bean, value);
				}
			}
			catch (IllegalArgumentException e) {
				throw new BeanInstantiationException(clazz, e.getMessage(), e);
			}
			catch (IllegalAccessException e) {
				throw new BeanInstantiationException(clazz, e.getMessage(), e);
			}
		}
		// }
		return bean;
	}

	protected <T> T getBean(Class<T> type) throws BeansException {
		// System.err.println("getBean:" + type);
		Map<String, T> map = beanFactory.getBeansOfType(type);
		if (map.isEmpty()) {
			return null;
		}
		if (map.size() == 1) {
			return map.entrySet().iterator().next().getValue();
		}
		List<T> list = new ArrayList<T>(map.size());
		// 优先注入@Primary
		int otherNum = 0;
		for (Entry<String, T> entry : map.entrySet()) {
			T bean = entry.getValue();
			Class<?> clazz = bean.getClass();
			if (clazz.getAnnotation(Primary.class) != null) {
				return bean;
			}
			if (clazz.getAnnotation(DefaultBean.class) != null) {
				list.add(bean);
			}
			else {
				list.add(0, bean);
				otherNum++;
			}
		}
		if (otherNum > 1) {

		}
		return list.get(0);
	}
}
