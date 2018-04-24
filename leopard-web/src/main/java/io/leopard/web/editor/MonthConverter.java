package io.leopard.web.editor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

import io.leopard.lang.datatype.Month;

public class MonthConverter implements Converter<String, Month> {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Month convert(String source) {
		// logger.info("MonthConverter source:" + source);
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		return new Month(source);
	}
}
