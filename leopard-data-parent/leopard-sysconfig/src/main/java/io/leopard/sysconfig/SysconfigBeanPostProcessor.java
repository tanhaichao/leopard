package io.leopard.sysconfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Primary;

public class SysconfigBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
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
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// System.err.println("beanName:" + beanName + " field:" + field.getName());
			Value annotation = field.getAnnotation(Value.class);
			if (annotation == null) {
				continue;
			}
			field.setAccessible(true);
			try {
				if (field.getType().equals(int.class)) {
					if (field.getName().equals("port")) {
						field.setInt(beanName, 9081);
					}
				}
			}
			catch (Exception e) {
				throw new BeanInstantiationException(clazz, e.getMessage(), e);
			}
		}
		// }
		return bean;
	}

}
