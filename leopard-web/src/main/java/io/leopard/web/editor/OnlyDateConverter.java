package io.leopard.web.editor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

import io.leopard.lang.datatype.OnlyDate;

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
