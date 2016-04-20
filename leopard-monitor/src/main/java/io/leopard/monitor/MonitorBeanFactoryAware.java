package io.leopard.monitor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class MonitorBeanFactoryAware implements BeanFactoryAware{

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// if (ForecastUtil.getForecast()) {
		// // BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "forecastDao", ForecastDaoRedisImpl.class, false);
		// // BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "forecastService", ForecastServiceImpl.class, false);
		// }
	}

}
