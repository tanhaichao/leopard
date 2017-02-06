package io.leopard.web.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.leopard.web.freemarker.template.BodyTemplateDirective;

public class ClassPathModelAndView extends ModelAndView {

	private FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();

	public ClassPathModelAndView(String viewName) {

		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		freemarkerVariables.put("xml_escape", "fmXmlEscape");
		freemarkerVariables.put("templateBody", new BodyTemplateDirective());

		this.init(freemarkerVariables);

		Properties freemarkerSettings = new Properties();
		freemarkerSettings.put("template_update_delay", "1");
		freemarkerSettings.put("defaultEncoding", "UTF-8");

		configurer.setTemplateLoaderPath("/WEB-INF/ftl/");
		configurer.setFreemarkerVariables(freemarkerVariables);
		configurer.setFreemarkerSettings(freemarkerSettings);

		try {
			configurer.afterPropertiesSet();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		FtlView view = new FtlView("htdocs", viewName, configurer);

		super.setView(view);
	}

	public void init(Map<String, Object> freemarkerVariables) {

	}

	public static class FtlView implements View {

		private String folder;
		private String viewName;

		private FreeMarkerConfigurer configurer;

		public FtlView(String folder, String viewName, FreeMarkerConfigurer configurer) {
			this.viewName = viewName;
			this.folder = folder;
			this.configurer = configurer;
		}

		@Override
		public String getContentType() {
			return "text/html; charset=UTF-8";
		}

		@Override
		public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			Configuration config = configurer.getConfiguration();
			// config.setTemplateLoader(new ClassTemplateLoader(this.getClass(), folder));
			config.setTemplateLoader(new SpringTemplateLoader(new DefaultResourceLoader(), folder));
			// return config.getTemplate(name, "UTF-8");

			Template template = config.getTemplate(viewName + ".ftl", "UTF-8");
			response.setContentType(getContentType());
			Writer out = response.getWriter();
			template.process(model, out);
		}

	}

}
