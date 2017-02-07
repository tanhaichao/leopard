package io.leopard.myjetty.web.freemarker;

import java.util.ArrayList;
import java.util.List;

import io.leopard.web.freemarker.TemplateVariable;
import io.leopard.web.freemarker.template.AbstractFrameView;
import io.leopard.web.freemarker.template.BodyTemplateDirective;
import io.leopard.web.freemarker.template.ReplaceParamTemplateMethod;
import io.leopard.web.freemarker.template.TimeTemplateMethod;
import io.leopard.web.freemarker.template.ajax.ButtonAjaxTemplateDirective;

public class MyJettyView extends AbstractFrameView {

	public MyJettyView(String templateName) {
		super("/myjetty/ftl/", "frame", templateName);
	}

	@Override
	public List<TemplateVariable> getVariables() {
		List<TemplateVariable> list = new ArrayList<TemplateVariable>();
		list.add(new TimeTemplateMethod());
		list.add(new BodyTemplateDirective());
		list.add(new ServerInfoTemplateDirective());

		list.add(new ReplaceParamTemplateMethod());

		list.add(new ButtonAjaxTemplateDirective());

		return list;
	}
}
