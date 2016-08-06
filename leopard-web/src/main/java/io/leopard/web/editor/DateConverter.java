package io.leopard.web.editor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

import io.leopard.burrow.lang.datatype.Month;

public class DateConverter implements Converter<String, Month> {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Month convert(String source) {
		logger.info("DateConverter source:" + source);
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		// return new Date(source);
		return null;
	}
}
