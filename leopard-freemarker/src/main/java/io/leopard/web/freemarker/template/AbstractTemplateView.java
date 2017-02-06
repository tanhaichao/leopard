package io.leopard.web.freemarker.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.leopard.web.freemarker.TemplateVariable;

public abstract class AbstractTemplateView {

	private String folder;
	private String templateName;
	protected Map<String, Object> model = new LinkedHashMap<String, Object>();

	public AbstractTemplateView(String folder, String templateName) {
		this.folder = folder;
		this.templateName = templateName;
	}

	public AbstractTemplateView addObject(String attributeName, Object attributeValue) {
		model.put(attributeName, attributeValue);
		return this;
	}

	public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
		RequestHolder.setRequest(request);
		Configuration config = new Configuration();

		ConfigurationHolder.setConfiguration(config);
		// 设置要解析的模板所在的目录，并加载模板文件
		// config.setDirectoryForTemplateLoading(templateFile);
		config.setTemplateLoader(new ClassTemplateLoader(this.getClass(), folder));

		// 设置包装器，并将对象包装为数据模型
		config.setObjectWrapper(new DefaultObjectWrapper());
		config.setDefaultEncoding("UTF-8");

		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		freemarkerVariables.put("xml_escape", "fmXmlEscape");

		List<TemplateVariable> variables = this.getVariables();

		if (variables != null) {
			for (TemplateVariable variable : variables) {
				freemarkerVariables.put(variable.getKey(), variable);
			}
		}

		// freemarkerVariables.put("time", new TimeTemplateMethod());
		// freemarkerVariables.put("url", new UrlTemplateMethod());
		// freemarkerVariables.put("underline", new UnderlineTemplateMethod());

		Properties freemarkerSettings = new Properties();
		freemarkerSettings.put("template_update_delay", "1");
		freemarkerSettings.put("defaultEncoding", "UTF-8");

		try {
			config.setSettings(freemarkerSettings);
			config.setAllSharedVariables(new SimpleHash(freemarkerVariables, config.getObjectWrapper()));

		}
		catch (TemplateException e) {
			throw new IOException(e);
		}

		// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
		// 否则会出现乱码
		Template template = config.getTemplate(templateName + ".ftl", Locale.CHINA, "UTF-8");
		template.setCustomAttribute("request", request);
		model.put("request", request);

		StringWriter writer = new StringWriter();
		// Writer out = response.getWriter();
		try {
			template.process(model, writer);
		}
		catch (TemplateException e) {
			throw new IOException(e);
		}

		output(writer, response);
		// Template tmp = (getEncoding() != null ? conf.getTemplate(name,
		// locale, getEncoding()) : conf.getTemplate(name, locale));

	}

	protected void output(StringWriter writer, HttpServletResponse response) throws IOException {
		String html = writer.toString();
		html = html.replace("# {{", "#{{");// 兼容AngularJS

		response.setContentType("text/html; charset=UTF-8");
		Writer out = response.getWriter();
		out.write(html);
		out.flush();
		out.close();
	}

	public abstract List<TemplateVariable> getVariables();
}
