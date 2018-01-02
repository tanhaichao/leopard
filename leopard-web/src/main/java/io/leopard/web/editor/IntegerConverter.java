package io.leopard.web.editor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

public class IntegerConverter implements Converter<String, Integer> {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Integer convert(String source) {
		if (source == null) {
			return new Integer(0);
		}
		return Integer.parseInt(source);
	}
}
