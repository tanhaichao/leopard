package io.leopard.web4j.editor;

import io.leopard.burrow.lang.datatype.Month;

import org.springframework.core.convert.converter.Converter;

public class MonthConverter implements Converter<String, Month> {
	@Override
	public Month convert(String source) {
		return new Month(source);
	}
}
