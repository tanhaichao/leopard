package io.leopard.web.passport;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FtlView implements View {

	private String folder;
	private String viewName;

	public FtlView() {
	}

	public FtlView(String viewName) {
		this("/", viewName);
	}

	public FtlView(String folder, String viewName) {
		this.viewName = viewName;
		this.folder = folder;
	}

	@Override
	public String getContentType() {
		return "text/html; charset=UTF-8";
	}

	protected Template getTemplate(String name) throws IOException {
		Configuration config = configurer.getConfiguration();
		config.setTemplateLoader(new ClassTemplateLoader(this.getClass(), folder));
		return config.getTemplate(name, "UTF-8");
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Template template = this.getTemplate(viewName + ".ftl");
		response.setContentType(getContentType());
		Writer out = response.getWriter();
		template.process(model, out);
	}

	private static FreeMarkerConfigurer configurer;

	static {
		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		freemarkerVariables.put("xml_escape", "fmXmlEscape");
		// freemarkerVariables.put("replaceParam", new ReplaceParamMethod());
		// freemarkerVariables.putAll(listTemplateMethod(applicationContext));

		Properties freemarkerSettings = new Properties();
		freemarkerSettings.put("template_update_delay", "1");
		freemarkerSettings.put("defaultEncoding", "UTF-8");

		configurer = new FreeMarkerConfigurer();
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
	}

}
