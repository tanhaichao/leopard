package io.leopard.topnb.web.freemarker;

import java.util.ArrayList;
import java.util.List;

import io.leopard.web.freemarker.TemplateVariable;
import io.leopard.web.freemarker.template.AbstractFrameView;
import io.leopard.web.freemarker.template.BodyTemplateDirective;
import io.leopard.web.freemarker.template.ReplaceParamTemplateMethod;
import io.leopard.web.freemarker.template.TimeTemplateMethod;

public class TopnbView extends AbstractFrameView {

	public TopnbView(String templateName) {
		super("/topnb/ftl/", "topnb", templateName);
	}

	@Override
	public List<TemplateVariable> getVariables() {
		List<TemplateVariable> list = new ArrayList<TemplateVariable>();
		list.add(new TimeTemplateMethod());
		list.add(new AvgTimeTemplateMethod());
		list.add(new BodyTemplateDirective());

		list.add(new MenuTemplateDirective());
		list.add(new ServerInfoTemplateDirective());
		list.add(new ReplaceParamTemplateMethod());

		return list;
	}
}
