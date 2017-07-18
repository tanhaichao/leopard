package io.leopard.sysconfig.dynamicenum;

import org.apache.commons.lang.StringUtils;
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
public class DynamicEnumScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

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
		logger.info("basePackage:" + basePackage);
		if (StringUtils.isEmpty(basePackage)) {
			throw new RuntimeException("app.properties没有配置base.package属性.");
		}
		System.err.println("DynamicEnumScannerConfigurer postProcessBeanFactory");
		DynamicEnumScanner scanner = new DynamicEnumScanner((BeanDefinitionRegistry) beanFactory);
		scanner.setResourceLoader(this.applicationContext);
		scanner.scan(basePackage);
	}

}
