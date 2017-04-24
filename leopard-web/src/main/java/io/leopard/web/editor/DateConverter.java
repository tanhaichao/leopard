package io.leopard.web.editor;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Date convert(String source) {
		// logger.info("MonthConverter source:" + source);
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		long time = Long.parseLong(source);
		if (time > 0) {
			return new Date(time);
		}
		else {
			throw new IllegalArgumentException("非法时间戳[" + time + "].");
		}
	}
}
