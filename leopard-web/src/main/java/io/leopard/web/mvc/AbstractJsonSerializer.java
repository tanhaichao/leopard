package io.leopard.web.mvc;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
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
		this.inject();
	}

	protected void inject() {
		for (Field field : this.getClass().getDeclaredFields()) {
			Autowired autowired = field.getAnnotation(Autowired.class);
			if (autowired == null) {
				continue;
			}
			Object bean = this.findBean(field.getType());
			field.setAccessible(true);
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

	/**
	 * 获取VO的属性值
	 * 
	 * @param gen
	 * @param fieldName
	 * @return
	 */
	protected Object getFieldValue(JsonGenerator gen, String fieldName) {
		Object currentValue = gen.getOutputContext().getCurrentValue();
		if (currentValue == null) {
			return null;
		}
		Class<?> clazz = currentValue.getClass();
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(currentValue);
		}
		catch (NoSuchFieldException e) {
			return null;
		}
		catch (SecurityException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
