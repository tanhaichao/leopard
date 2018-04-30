package io.leopard.web.freemarker;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerView extends org.springframework.web.servlet.view.freemarker.FreeMarkerView {

	protected String folder;

	protected ModelMap model;

	public FreeMarkerView() {
	}

	public FreeMarkerView(String viewName) {
		this("/", viewName);
	}

	public FreeMarkerView(String folder, String viewName) {
		try {
			ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
			this.setApplicationContext(applicationContext);
		}
		catch (Exception e) {
			// 兼容普通java环境
		}
		this.setUrl(viewName + ".ftl");
		this.folder = folder;
	}

	public ModelMap getModelMap() {
		if (this.model == null) {
			this.model = new ModelMap();
		}
		return this.model;
	}

	public Map<String, Object> getModel() {
		return getModelMap();
	}

	public FreeMarkerView addObject(String attributeName, Object attributeValue) {
		getModelMap().addAttribute(attributeName, attributeValue);
		return this;
	}

	@Override
	protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
		logger.info("autodetectConfiguration:");
		String templateLoaderPath = "";
		return FreeMarkerUtil.getFreeMarkerConfig(super.getApplicationContext(), templateLoaderPath);
	}

	public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
		render(null, request, response);
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	// at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1286)
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 忽略DispatcherServlet.render传进来的model
		super.render(this.getModel(), request, response);
	}

	@Override
	public String getContentType() {
		return "text/html; charset=UTF-8";
	}

	@Override
	protected Template getTemplate(String name, Locale locale) throws IOException {
		// TemplateLoader tl = getConfiguration().getTemplateLoader();
		Configuration conf = getConfiguration();
		conf.setTemplateLoader(new ClassTemplateLoader(this.getClass(), folder));
		Template tmp = (getEncoding() != null ? conf.getTemplate(name, locale, getEncoding()) : conf.getTemplate(name, locale));
		// conf.setTemplateLoader(tl);

		return tmp;
		// return super.getTemplate(name, locale);
	}
}
