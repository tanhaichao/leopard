package io.leopard.web4j.editor;

import io.leopard.burrow.lang.datatype.OnlyDate;

import org.springframework.core.convert.converter.Converter;

public class OnlyDateConverter implements Converter<String, OnlyDate> {
	@Override
	public OnlyDate convert(String source) {
		return new OnlyDate(source);
	}
}
