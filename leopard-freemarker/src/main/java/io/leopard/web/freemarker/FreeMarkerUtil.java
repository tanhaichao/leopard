package io.leopard.web.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.TemplateException;
import io.leopard.web.freemarker.template.ReplaceParamTemplateMethod;
import io.leopard.web.freemarker.template.TimeAgoTemplateMethod;

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
	private static FreeMarkerConfigurer configurer;

	protected static FreeMarkerConfigurer getFreeMarkerConfigurer(ApplicationContext applicationContext, String templateLoaderPath) {
		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		freemarkerVariables.put("xml_escape", "fmXmlEscape");
		freemarkerVariables.put("replaceParam", new ReplaceParamTemplateMethod());
		freemarkerVariables.put("timeAgo", new TimeAgoTemplateMethod());
		freemarkerVariables.putAll(listTemplateMethod(applicationContext));

		Properties freemarkerSettings = new Properties();
		freemarkerSettings.put("template_update_delay", "1");
		freemarkerSettings.put("defaultEncoding", "UTF-8");

		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath(templateLoaderPath);
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

	public static Map<String, Object> listTemplateMethod(ListableBeanFactory applicationContext) {
		Map<String, TemplateVariable> map = applicationContext.getBeansOfType(TemplateVariable.class);

		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		for (Entry<String, TemplateVariable> entry : map.entrySet()) {
			TemplateVariable variable = entry.getValue();
			// System.err.println("key:" + method.getKey() + " method:" + method);
			freemarkerVariables.put(variable.getKey(), variable);
		}
		freemarkerVariables.put("timeAgo", new TimeAgoTemplateMethod());
		return freemarkerVariables;
	}

	public static FreeMarkerConfig getFreeMarkerConfig(ApplicationContext applicationContext, String templateLoaderPath) {
		if (configurer != null) {
			return configurer;
		}
		configurer = getFreeMarkerConfigurer(applicationContext, templateLoaderPath);
		return configurer;
	}
}
