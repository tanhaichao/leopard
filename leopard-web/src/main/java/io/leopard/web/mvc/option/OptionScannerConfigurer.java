package io.leopard.web.mvc.option;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.leopard.data.env.LeopardPropertyPlaceholderConfigurer;

@Component
public class OptionScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

	protected Log logger = LogFactory.getLog(this.getClass());

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// logger.info("setApplicationContext");
		this.applicationContext = applicationContext;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// logger.info("postProcessBeanFactory");
		LeopardPropertyPlaceholderConfigurer configurer = beanFactory.getBean(LeopardPropertyPlaceholderConfigurer.class);
		String basePackage = configurer.getProperty("base.package");
		// logger.info("basePackage:" + basePackage);
		if (StringUtils.isEmpty(basePackage)) {
			String message = "app.properties没有配置base.package属性，Leopard无法扫描项目定义了哪些枚举类.";
			logger.warn(message);
			return;
		}
		// System.err.println("OptionScannerConfigurer postProcessBeanFactory");
		OptionScanner scanner = new OptionScanner((BeanDefinitionRegistry) beanFactory);
		scanner.setResourceLoader(this.applicationContext);
		scanner.scan(basePackage);
	}

}
