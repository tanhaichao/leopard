package io.leopard.json;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * 禁用@JsonSerializer
 * 
 * @author 谭海潮
 *
 */
public class DisablingJsonSerializerIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	// @Override
	// public Boolean isIgnorableType(AnnotatedClass ac) {
	// System.out.println("isIgnorableType:" + ac.getAnnotated().getName());
	// return super.isIgnorableType(ac);
	// }

	@Override
	public Object findSerializer(Annotated am) {
		// System.err.println("am:" + am.getName());
		return null;
		// Object serializer = super.findSerializer(am);
		// if (serializer != null) {
		// Class<?> clazz = (Class<?>) serializer;
		// String className = clazz.getName();
		// if (className.endsWith("ProvinceJsonSerializer")) {
		// return null;
		// }
		// }
		// return serializer;
	}
}
