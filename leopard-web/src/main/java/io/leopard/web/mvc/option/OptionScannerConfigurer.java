package io.leopard.web.mvc.option;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class OptionScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

	@Value("${base.package}")
	private String basePackage;

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (StringUtils.isEmpty(basePackage)) {
			throw new RuntimeException("app.properties没有配置base.package属性.");
		}
		// System.err.println("OptionScannerConfigurer postProcessBeanFactory");
		OptionScanner scanner = new OptionScanner((BeanDefinitionRegistry) beanFactory);
		scanner.setResourceLoader(this.applicationContext);
		scanner.scan(basePackage);
	}

}
