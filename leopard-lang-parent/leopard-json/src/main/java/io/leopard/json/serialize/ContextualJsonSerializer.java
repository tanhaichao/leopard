package io.leopard.json.serialize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

/**
 * 上下文Json序列化
 * 
 * @author 谭海潮
 *
 */
public abstract class ContextualJsonSerializer<A extends Annotation> extends JsonSerializer<Object> implements ContextualSerializer {

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
		if (beanProperty != null) {
			System.err.println("beanProperty:" + beanProperty + " name:" + beanProperty.getName() + " type:" + beanProperty.getMember().getGenericType());

			Class<A> clazz = annotation();
			A anno = beanProperty.getAnnotation(clazz);
			if (anno == null) {
				anno = beanProperty.getContextAnnotation(clazz);
			}
			if (anno != null) {
				try {
					return this.create(clazz, anno, beanProperty);
				}
				catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(beanProperty);
	}

	public JsonSerializer<?> create(Class<A> annoClazz, A anno, BeanProperty beanProperty) throws Exception {
		Class<?> clazz = this.getClass();
		// System.err.println("anno:" + annoClazz.getName());
		Constructor<?> constructor = clazz.getConstructor(BeanProperty.class, annoClazz);
		return (JsonSerializer<?>) constructor.newInstance(beanProperty, anno);
	}

	@SuppressWarnings("unchecked")
	public Class<A> annotation() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<A> clazz = (Class<A>) type.getActualTypeArguments()[0];
		// System.err.println("className:" + clazz.getName());
		return clazz;
	}

}
