package io.leopard.web.mvc.json;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.leopard.json.As;
import io.leopard.lang.util.BeanUtil;
import io.leopard.web.mvc.AbstractJsonSerializer;

/**
 * 支持单个和列表数据输出
 * 
 * @author 谭海潮
 *
 * @param <T>
 */
public abstract class AsJsonSerializer<T> extends AbstractJsonSerializer<Object> {

	protected Log logger = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public void out(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// System.err.println("BaseJsonSerializer value:" + value);
		String fieldName = gen.getOutputContext().getCurrentName();
		Field field = getCurrentField(gen);
		field.setAccessible(true);
		As as = field.getAnnotation(As.class);
		if (as == null) {
			if (value instanceof List) {
				throw new RuntimeException("属性[" + fieldName + "]没有设置@As");
			}
			else {
				Object data = this.get((T) value, gen, field);
				Object currentValue = gen.getOutputContext().getCurrentValue();
				if (data != null) {
					copyProperties(data, currentValue);
				}
				gen.writeObject(currentValue);
			}
			return;
		}
		Class<?> asClazz = as.value();
		// System.err.println("field:" + field.toGenericString());
		gen.writeObject(value);
		Object data;
		if (value instanceof List) {
			List<Object> list = new ArrayList<Object>();
			for (T key : (List<T>) value) {
				Object element = this.get(key, gen, field);
				list.add(BeanUtil.convert(element, asClazz));
			}
			data = list;
		}
		else {
			Object element = this.get((T) value, gen, field);
			data = BeanUtil.convert(element, asClazz);
		}
		String newFieldName = this.getFieldName(fieldName);
		gen.writeFieldName(newFieldName);
		gen.writeObject(data);
	}

	// TODO 这里需要忽略有值的属性
	protected void copyProperties(Object source, Object target) {
		// BeanUtils.copyProperties(source, target);
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod == null) {
				continue;
			}

			PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
			if (sourcePd == null) {
				continue;
			}

			{
				Method targetReadMethod = targetPd.getReadMethod();
				Object value;
				try {
					value = targetReadMethod.invoke(target);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				boolean isDefaultValue = isDefaultValue(value);
				if (!isDefaultValue) {
					// logger.info("fieldName:" + targetPd.getName() + " isDefaultValue:" + isDefaultValue + " value:" + value + " targetReadMethod:" + targetReadMethod.toGenericString());
					continue;
				}
			}

			Method readMethod = sourcePd.getReadMethod();
			// logger.info("write fieldName:" + targetPd.getName() + " readMethod:" + readMethod.toGenericString());
			if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
				try {
					if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(source);
					if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
						writeMethod.setAccessible(true);
					}
					writeMethod.invoke(target, value);
				}
				catch (Throwable ex) {
					throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
				}
			}
		}
	}

	protected boolean isDefaultValue(Object value) {
		if (value == null) {
			return true;
		}

		if (value instanceof Integer) {
			int number = (int) value;
			return number <= 0;
		}
		else if (value instanceof Long) {
			long number = (long) value;
			return number <= 0;
		}
		else if (value instanceof Float) {
			float number = (float) value;
			return number <= 0;
		}
		else if (value instanceof Double) {
			double number = (double) value;
			return number <= 0;
		}
		return true;
	}

	/**
	 * 获取VO的属性
	 * 
	 * @param gen
	 * @param fieldName
	 * @return
	 */
	protected Field getCurrentField(JsonGenerator gen) {
		Object currentValue = gen.getOutputContext().getCurrentValue();
		if (currentValue == null) {
			return null;
		}
		String fieldName = gen.getOutputContext().getCurrentName();
		Class<?> clazz = currentValue.getClass();
		try {
			Field field = clazz.getDeclaredField(fieldName);
			// field.setAccessible(true);
			return field;
		}
		catch (NoSuchFieldException e) {
			return null;
		}
		catch (SecurityException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
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
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (SecurityException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected String getFieldName(String fieldName) {
		if ("uid".equals(fieldName)) {
			return "user";
		}
		if ("uidList".equals(fieldName)) {
			return "userList";
		}
		return fieldName.replace("Id", "");
	}

	public abstract Object get(T value, JsonGenerator gen, Field field);
}
