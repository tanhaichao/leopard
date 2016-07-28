package io.leopard.web.mvc;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonSerializer;

import io.leopard.jdbc.LeopardBeanFactoryAware;

/**
 * 实现依赖注入
 * 
 * @author 谭海潮
 *
 * @param <T>
 */
public abstract class AbstractJsonSerializer<T> extends JsonSerializer<T> {

	public AbstractJsonSerializer() {

	}

	protected void inject() {
		for (Field field : this.getClass().getDeclaredFields()) {
			Autowired autowired = field.getAnnotation(Autowired.class);
			if (autowired == null) {
				continue;
			}
			Object bean = this.findBean(field.getType());
			try {
				field.set(this, bean);
			}
			catch (IllegalArgumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	protected Object findBean(Class<?> type) {
		return LeopardBeanFactoryAware.getBeanFactory().getBean(type);
	}

}
