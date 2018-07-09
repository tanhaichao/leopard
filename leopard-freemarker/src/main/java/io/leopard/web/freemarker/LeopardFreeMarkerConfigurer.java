package io.leopard.web.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class LeopardFreeMarkerConfigurer extends FreeMarkerConfigurer implements BeanFactoryAware {

	protected ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	private Map<String, Object> freemarkerVariables;

	@Override
	public void setFreemarkerVariables(Map<String, Object> variables) {
		this.freemarkerVariables = variables;
	}

	public Map<String, Object> getFreemarkerVariables() {
		return freemarkerVariables;
	}

	public Properties getFreemarkerSettings() {
		return freemarkerSettings;
	}

	private Properties freemarkerSettings;

	@Override
	public void setFreemarkerSettings(Properties settings) {
		this.freemarkerSettings = settings;
	};

	@Override
	public Configuration createConfiguration() throws IOException, TemplateException {
		{
			if (freemarkerSettings == null) {
				freemarkerSettings = new Properties();
			}

			{
				String objectWrapper = System.getProperty("freemarker.object_wrapper");
				if (objectWrapper != null && objectWrapper.length() > 0) {
					freemarkerSettings.put("object_wrapper", objectWrapper);
				}
			}

			{
				try {
					this.beanFactory.getBean(FreeMarkerConfiguration.class).configguration(freemarkerSettings);
				}
				catch (NoSuchBeanDefinitionException e) {

				}
			}

			super.setFreemarkerSettings(freemarkerSettings);
		}
		{
			if (freemarkerVariables == null) {
				freemarkerVariables = new HashMap<String, Object>();
			}
			freemarkerVariables.putAll(FreeMarkerUtil.listTemplateMethod(beanFactory));
			super.setFreemarkerVariables(freemarkerVariables);
		}

		return super.createConfiguration();
	}
}
