package io.leopard.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Onum;
import io.leopard.lang.inum.Snum;

/**
 * 禁用@JsonSerializer
 * 
 * @author 谭海潮
 *
 */
public class DisablingJsonSerializerIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	@Override
	public Object findDeserializer(Annotated a) {
		// System.err.println("Annotated:" + a.getClass().getName() + " " + a);

		if (a instanceof AnnotatedClass) {
			Class<?> clazz = ((AnnotatedClass) a).getAnnotated();
			// System.err.println("clazz:" + clazz.getName() + " " + a.getName());

			if (clazz.isEnum()) {
				if (Onum.class.isAssignableFrom(clazz)) {
					// System.err.println("OnumJsonSerializerIntrospector findDeserializer:" + clazz.getName() + " a:" + a);
					return new OnumJsonDeserializer(clazz);
				}
			}
		}

		Object deserializer = super.findDeserializer(a);
		return deserializer;
	}

	/**
	 * Onum Json反序列化
	 * 
	 * @author 谭海潮
	 *
	 */
	private static class OnumJsonDeserializer extends JsonDeserializer<Onum<?, ?>> {
		private Class<?> clazz;

		public OnumJsonDeserializer(Class<?> clazz) {
			this.clazz = clazz;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Onum<?, ?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonToken currentToken = jp.getCurrentToken();
			{
				// JsonNode node = jp.getCodec().readTree(jp);
				if (currentToken.equals(JsonToken.START_OBJECT)) {
					currentToken = jp.nextToken();
					if (!"key".equals(jp.getCurrentName())) {
						throw ctxt.mappingException("枚举的key字段必须要放在最前面.");
						// throw new RuntimeException("枚举的key字段必须要放在最前面.");
					}
					jp.nextValue();

					Object key;
					if (Inum.class.isAssignableFrom(clazz)) {
						key = jp.getIntValue();
					}
					else if (Snum.class.isAssignableFrom(clazz)) {
						key = jp.getValueAsString();
					}
					else {
						throw ctxt.mappingException("未知枚举类型[" + clazz.getName() + "].");
					}
					// System.err.println("key:" + key + " name:" + jp.getCurrentName() + " value:" + jp.getCurrentValue() + " text:" + jp.getText());
					this.nextToClose(jp, ctxt);
					if (key == null) {
						return null;
					}
					return (Onum<?, ?>) EnumUtil.toEnum(key, (Class<? extends Enum>) clazz);
					// System.err.println("currentToken name:" + jp.getCurrentName() + " token:" + currentToken.name());
				}
			}

			if (Inum.class.isAssignableFrom(clazz)) {
				int key = jp.getIntValue();
				return (Onum<?, ?>) EnumUtil.toEnum(key, (Class<? extends Enum>) clazz);

			}
			else if (Snum.class.isAssignableFrom(clazz)) {
				// System.err.println("key:"+jp.getValueAsString());
				String key = jp.getText();
				return (Onum<?, ?>) EnumUtil.toEnum(key, (Class<? extends Enum>) clazz);
			}
			throw ctxt.mappingException("未知枚举类型[" + clazz.getName() + "].");

			// node.getNodeType().
			// if (currentToken == JsonToken.VALUE_STRING) {
			// return new TextContainer(jp.getText().trim(), null);
			// }
			// else if (currentToken == JsonToken.START_OBJECT) {
			// JsonToken jsonToken = jp.nextToken();
			// if (jsonToken == JsonToken.FIELD_NAME) {
			// String operation = jp.getText().trim();
			// jp.nextToken();
			// String text = jp.getText().trim();
			// jp.nextToken();
			// return new TextContainer(text, operation);
			// }
			// }

		}

		protected void nextToClose(JsonParser jp, DeserializationContext ctxt) throws JsonMappingException, IOException {
			while (true) {
				JsonToken token = jp.nextToken();// }
				if (token == JsonToken.END_OBJECT) {
					break;
				}
			}
		}
	}

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
