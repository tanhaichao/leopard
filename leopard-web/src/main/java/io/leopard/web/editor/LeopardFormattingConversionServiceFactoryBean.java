package io.leopard.web.editor;

import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

public class LeopardFormattingConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	public FormattingConversionService getObject() {
		// new Exception("o").printStackTrace();
		return super.getObject();
	}
}
