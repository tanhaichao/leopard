package io.leopard.web.editor;

import io.leopard.burrow.lang.datatype.OnlyDate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

public class OnlyDateConverter implements Converter<String, OnlyDate> {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public OnlyDate convert(String source) {
		// logger.info("OnlyDateConverter source:" + source);
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		return new OnlyDate(source);
	}
}
