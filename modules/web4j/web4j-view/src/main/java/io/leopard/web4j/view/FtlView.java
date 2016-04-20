package io.leopard.web4j.view;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.leopard.web.freemarker.FreeMarkerUtil;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class FtlView extends FreeMarkerView {

	private String folder;

	public FtlView() {
	}

	public FtlView(String viewName) {
		this("/", viewName);
	}

	public FtlView(String folder, String viewName) {
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

	public void addObject(String name, Object value) {
		this.addStaticAttribute(name, value);
	}

	@Override
	protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
		return FreeMarkerUtil.getFreeMarkerConfig();
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
