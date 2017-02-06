package io.leopard.web.xparam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import io.leopard.web.xparam.resolver.ModelHandlerMethodArgumentResolver;
import io.leopard.web.xparam.resolver.ParamListHandlerMethodArgumentResolver;
import io.leopard.web.xparam.resolver.PathRegexHandlerMethodArgumentResolver;
import io.leopard.web.xparam.resolver.PrimitiveMethodArgumentResolver;
import io.leopard.web.xparam.resolver.TimeRangeHandlerMethodArgumentResolver;
import io.leopard.web.xparam.resolver.UnderlineHandlerMethodArgumentResolver;
import io.leopard.web.xparam.resolver.XParamHandlerMethodArgumentResolver;

/**
 * 注册HandlerMethodArgumentResolver
 * 
 * @author 阿海
 *
 */
@Component
public class XParamBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof RequestMappingHandlerAdapter) {
			this.registerHandlerMethodArgumentResolver((RequestMappingHandlerAdapter) bean);
		}
		return bean;
	}

	/**
	 * 注册HandlerMethodArgumentResolver.
	 * 
	 * @param adapter
	 */
	private void registerHandlerMethodArgumentResolver(RequestMappingHandlerAdapter adapter) {
		List<HandlerMethodArgumentResolver> customArgumentResolvers = adapter.getCustomArgumentResolvers();
		if (customArgumentResolvers == null) {
			customArgumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
			adapter.setCustomArgumentResolvers(customArgumentResolvers);
		}

		{
			XParamHandlerMethodArgumentResolver argumentResolver = beanFactory.getBean(XParamHandlerMethodArgumentResolver.class);
			customArgumentResolvers.add(argumentResolver);
		}

		customArgumentResolvers.add(new PrimitiveMethodArgumentResolver());

		{
			PathRegexHandlerMethodArgumentResolver argumentResolver = beanFactory.getBean(PathRegexHandlerMethodArgumentResolver.class);
			customArgumentResolvers.add(argumentResolver);
		}

		{
			// 要比UnderlineHandlerMethodArgumentResolver先添加到list
			ParamListHandlerMethodArgumentResolver argumentResolver = beanFactory.getBean(ParamListHandlerMethodArgumentResolver.class);
			customArgumentResolvers.add(argumentResolver);
		}

		{
			TimeRangeHandlerMethodArgumentResolver argumentResolver = beanFactory.getBean(TimeRangeHandlerMethodArgumentResolver.class);
			customArgumentResolvers.add(argumentResolver);
		}

		if (UnderlineHandlerMethodArgumentResolver.isEnable()) {
			UnderlineHandlerMethodArgumentResolver argumentResolver = beanFactory.getBean(UnderlineHandlerMethodArgumentResolver.class);
			customArgumentResolvers.add(argumentResolver);
		}
		if (UnderlineHandlerMethodArgumentResolver.isEnable()) {
			ModelHandlerMethodArgumentResolver argumentResolver = beanFactory.getBean(ModelHandlerMethodArgumentResolver.class);
			customArgumentResolvers.add(argumentResolver);
		}
		customArgumentResolvers.add(new PrimitiveMethodArgumentResolver());
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
