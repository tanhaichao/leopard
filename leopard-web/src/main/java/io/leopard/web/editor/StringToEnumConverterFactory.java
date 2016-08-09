package io.leopard.web.editor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import io.leopard.burrow.lang.inum.EnumUtil;
import io.leopard.burrow.lang.inum.Inum;

/**
 * Converts from a String to a {@link java.lang.Enum} by calling {@link Enum#valueOf(Class, String)}.
 *
 * @author Keith Donald
 * @since 3.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
final class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

	@Override
	public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
		Class<?> enumType = targetType;
		while (enumType != null && !enumType.isEnum()) {
			enumType = enumType.getSuperclass();
		}
		if (enumType == null) {
			throw new IllegalArgumentException("The target type " + targetType.getName() + " does not refer to an enum");
		}
		return new StringToEnum(enumType);
	}

	private class StringToEnum<T extends Enum> implements Converter<String, T> {

		private final Class<T> enumType;

		public StringToEnum(Class<T> enumType) {
			this.enumType = enumType;
			// new Exception("StringToEnum:" + enumType.getName()).printStackTrace();
		}

		@Override
		public T convert(String source) {
			if (source == null) {
				source = "";
			}
			// System.err.println("enumType:" + enumType.getName());
			Object key;
			if (Inum.class.isAssignableFrom(enumType)) {
				key = Integer.parseInt(source);
				// System.err.println("enumType:" + enumType.getName() + " key:" + key);
			}
			else {
				key = source;
			}

			try {
				return (T) EnumUtil.toEnum(key, enumType);
			}
			catch (IllegalArgumentException e) {
				if (source.length() == 0) {
					return null;
				}
				throw e;
			}
			// return (T) Enum.valueOf(this.enumType, source.trim());
		}
	}

}