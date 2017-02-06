package io.leopard.web.freemarker.template.ajax;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import io.leopard.web.freemarker.TemplateVariable;

/**
 * 按钮
 * 
 * @author 阿海
 *
 */

public class ButtonAjaxTemplateDirective implements TemplateDirectiveModel, TemplateVariable {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String value = ((SimpleScalar) params.get("value")).getAsString();
		// String processing = ((SimpleScalar) params.get("processing")).getAsString();
		String url = ((SimpleScalar) params.get("url")).getAsString();
		StringBuilder sb = new StringBuilder();
		sb.append("<input type='button' value='" + value + "' onclick=\"request('" + url + "')\"/>");
		env.getOut().write(sb.toString());
	}

	@Override
	public String getKey() {
		return "ajax_button";
	}

}
