package io.leopard.web.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.TemplateException;

public class FreeMarkerUtil {

	// <bean id="freemarkerConfigurer" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
	// <property name="templateLoaderPath" value="/WEB-INF/ftl/"></property>

	// <property name="freemarkerVariables">
	// <map>
	// <entry key="xml_escape" value-ref="fmXmlEscape" />
	// </map>
	// </property>

	// <property name="freemarkerSettings">
	// <props>
	// <prop key="template_update_delay">1</prop>
	// <prop key="defaultEncoding">UTF-8</prop>
	// </props>
	// </property>
	// </bean>
	private static final FreeMarkerConfigurer configurer = getFreeMarkerConfigurer();

	protected static FreeMarkerConfigurer getFreeMarkerConfigurer() {
		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		freemarkerVariables.put("xml_escape", "fmXmlEscape");
		freemarkerVariables.put("replaceParam", new ReplaceParamMethod());

		Properties freemarkerSettings = new Properties();
		freemarkerSettings.put("template_update_delay", "1");
		freemarkerSettings.put("defaultEncoding", "UTF-8");

		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
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
		return configurer;
	}

	public static FreeMarkerConfig getFreeMarkerConfig() {
		return configurer;
	}
}
