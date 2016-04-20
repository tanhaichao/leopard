package io.leopard.web4j.editor;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class LeopardWebBindingInitializer extends ConfigurableWebBindingInitializer {

	private static boolean convert = true;

	public static void setConvert(boolean convert) {
		LeopardWebBindingInitializer.convert = convert;
	}

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		super.initBinder(binder, request);

		binder.registerCustomEditor(Date.class, new DefaultDateEditor());
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());

		if (convert) {
			binder.registerCustomEditor(Boolean.class, new BooleanEditor());
			binder.registerCustomEditor(Integer.class, new IntegerEditor());
			binder.registerCustomEditor(Long.class, new LongEditor());
		}

	}

}
