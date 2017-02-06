package io.leopard.web.freemarker.template;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractFrameView extends AbstractTemplateView {

	public AbstractFrameView(String folder, String templateName) {
		super(folder, templateName);
	}

	public AbstractFrameView(String folder, String frameName, String templateName) {
		super(folder, frameName);
		this.addObject("template_folder", folder);
		this.addObject("template_name", templateName);
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BodyTemplateDirective.setData(model);

		super.render(request, response);
	}
}
