package io.leopard.convert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;

public class ConverterContext implements BeanFactoryAware {

	@SuppressWarnings("rawtypes")
	private static List<Converter> converterList = new ArrayList<Converter>();

	@SuppressWarnings("rawtypes")
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		Map<String, Converter> beanMap = ((DefaultListableBeanFactory) beanFactory).getBeansOfType(Converter.class);
		for (Entry<String, Converter> entry : beanMap.entrySet()) {
			converterList.add(entry.getValue());
		}
		// new Exception("ConverterContext").printStackTrace();
	}

	public static void convert(Object bean, Object source) {
		Assert.notNull(bean, "参数bean不能为null.");
		Assert.notNull(source, "参数source不能为null.");
		try {
			// convert2(bean, source);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void convert2(Object bean, Object source) throws Exception {
		Class<?> clazz = bean.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(bean);
			if (value != null) {
				continue;
			}
			Class<?> type = field.getType();
			if (List.class.equals(type)) {
				// System.err.println("暂时不支持列表解析,clazz: " + clazz.getName() + " " + field.toGenericString());
				// continue;
				value = parse(field.getGenericType().getTypeName(), source);
			}
			else {
				value = parse(type.getName(), source);
			}
			if (value != null) {
				field.set(bean, value);
			}
		}
	}

	protected static Converter findConverter(String typeName, Object source) {
		for (Converter converter : converterList) {
			Class<?> api = converter.getClass().getInterfaces()[0];
			ParameterizedType type2 = (ParameterizedType) converter.getClass().getGenericInterfaces()[0];
			Type[] args = type2.getActualTypeArguments();
			Class<?> clazz1 = (Class<?>) args[0];
			String typeName2 = args[1].getTypeName();
			if (!typeName.equals(typeName2)) {
				continue;
			}
			if (clazz1.equals(source.getClass())) {
				return converter;
			}
			System.err.println("converter:" + converter + " type:" + typeName + " type1:" + clazz1.getName() + " type12:" + typeName2);
		}
		return null;
	}

	protected static Object parse(String typeName, Object source) throws Exception {
		Converter converter = findConverter(typeName, source);
		if (converter == null) {
			return null;
		}
		System.out.println("converter111:" + converter);
		return converter.get(source);
	}
}
