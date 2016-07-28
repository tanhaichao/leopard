package io.leopard.web.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.servlet.ModelAndView;

@Deprecated
public class FreeModelAndView extends ModelAndView {

	private final static Set<String> simpleClassNameSet = new HashSet<String>();

	static {
		simpleClassNameSet.add(String.class.getName());
		simpleClassNameSet.add(boolean.class.getName());
		simpleClassNameSet.add(Boolean.class.getName());
		simpleClassNameSet.add(int.class.getName());
		simpleClassNameSet.add(Integer.class.getName());
		simpleClassNameSet.add(long.class.getName());
		simpleClassNameSet.add(Long.class.getName());
		simpleClassNameSet.add(float.class.getName());
		simpleClassNameSet.add(Float.class.getName());
		simpleClassNameSet.add(double.class.getName());
		simpleClassNameSet.add(Double.class.getName());
		simpleClassNameSet.add(Date.class.getName());
		// simpleClassNameSet.add(List.class.getName());

	}

	public FreeModelAndView(String viewName) {
		super(viewName);
	}

	@Override
	public ModelAndView addObject(String attributeName, Object attributeValue) {
		// if (attributeValue != null) {
		// String className = attributeValue.getClass().getName();
		// if (!simpleClassNameSet.contains(className)) {
		// try {
		// this.serialize(attributeValue);
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		return super.addObject(attributeName, attributeValue);
	}

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// protected void serialize(Object attributeValue) throws Exception {
	// if (attributeValue == null) {
	// return;
	// }
	// // System.err.println("serialize:" + attributeValue.getClass().getName());
	//
	// Class<?> clazz = attributeValue.getClass();
	// while (true) {
	// for (Field field : clazz.getDeclaredFields()) {
	// field.setAccessible(true);
	// JsonSerialize anno = field.getAnnotation(JsonSerialize.class);
	// if (anno == null) {
	// if (field.getType().equals(List.class)) {
	// this.list(field, attributeValue);
	// }
	// else if (!simpleClassNameSet.contains(field.getType().getName())) {
	// serialize(field.get(attributeValue));
	// }
	// continue;
	// }
	// Class<?> jsonClazz = anno.using();
	// if (!AbstractJsonSerializer.class.isAssignableFrom(jsonClazz)) {
	// continue;
	// }
	// Object value = field.get(attributeValue);
	// AbstractJsonSerializer jsonSerializer = (AbstractJsonSerializer) jsonClazz.newInstance();
	// Object newValue = jsonSerializer.serialize(value);
	// field.set(attributeValue, newValue);
	//
	// }
	// if (clazz.getSuperclass() == null) {
	// break;
	// }
	// clazz = clazz.getSuperclass();
	// }
	// }

	@SuppressWarnings("rawtypes")
	protected void list(Field field, Object attributeValue) throws Exception {
		// String typeName = this.getTypeName(field);
		// if (simpleClassNameSet.contains(typeName)) {
		// return;
		// }
		// // System.err.println("list:" + field.getName());
		// List list = (List) field.get(attributeValue);
		// if (list == null) {
		// return;
		// }
		// for (Object element : list) {
		// this.serialize(element);
		// }
		
	}

	protected String getTypeName(Field field) {
		Type genericType = field.getGenericType();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType type2 = (ParameterizedType) genericType;
			return type2.getActualTypeArguments()[0].getTypeName();
		}
		return null;
	}
}
