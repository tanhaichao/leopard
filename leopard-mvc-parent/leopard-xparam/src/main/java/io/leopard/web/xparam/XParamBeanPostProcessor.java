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
import io.leopard.web.xparam.resolver.OnumMethodArgumentResolver;
import io.leopard.web.xparam.resolver.ParamListHandlerMethodArgumentResolver;
import io.leopard.web.xparam.resolver.PathRegexHandlerMethodArgumentResolver;
import io.leopard.web.xparam.resolver.PrimitiveMethodArgumentResolver;
import io.leopard.web.xparam.resolver.TimeRangeHandlerMethodArgumentResolver;
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
		// new Exception("registerHandlerMethodArgumentResolver customArgumentResolvers:" + customArgumentResolvers).printStackTrace();
		if (customArgumentResolvers == null) {
			customArgumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();

			// Annotation-based argument resolution
			// customArgumentResolvers.add(new RequestParamMethodArgumentResolver(getBeanFactory(), false));
			// customArgumentResolvers.add(new RequestParamMapMethodArgumentResolver());
			// customArgumentResolvers.add(new PathVariableMethodArgumentResolver());
			// customArgumentResolvers.add(new PathVariableMapMethodArgumentResolver());
			// customArgumentResolvers.add(new MatrixVariableMethodArgumentResolver());
			// customArgumentResolvers.add(new MatrixVariableMapMethodArgumentResolver());
			// customArgumentResolvers.add(new ServletModelAttributeMethodProcessor(false));
			// // customArgumentResolvers.add(new RequestResponseBodyMethodProcessor(getMessageConverters(), this.requestResponseBodyAdvice));
			// // customArgumentResolvers.add(new RequestPartMethodArgumentResolver(getMessageConverters(), this.requestResponseBodyAdvice));
			// // customArgumentResolvers.add(new RequestHeaderMethodArgumentResolver(getBeanFactory()));
			// customArgumentResolvers.add(new RequestHeaderMapMethodArgumentResolver());
			// // customArgumentResolvers.add(new ServletCookieValueMethodArgumentResolver(getBeanFactory()));
			// // customArgumentResolvers.add(new ExpressionValueMethodArgumentResolver(getBeanFactory()));
			// customArgumentResolvers.add(new SessionAttributeMethodArgumentResolver());
			// customArgumentResolvers.add(new RequestAttributeMethodArgumentResolver());
			//
			// // Type-based argument resolution
			// customArgumentResolvers.add(new ServletRequestMethodArgumentResolver());
			// customArgumentResolvers.add(new ServletResponseMethodArgumentResolver());
			// // customArgumentResolvers.add(new HttpEntityMethodProcessor(getMessageConverters(), this.requestResponseBodyAdvice));
			// customArgumentResolvers.add(new RedirectAttributesMethodArgumentResolver());
			// customArgumentResolvers.add(new ModelMethodProcessor());
			// customArgumentResolvers.add(new MapMethodProcessor());
			// customArgumentResolvers.add(new ErrorsMethodArgumentResolver());
			// customArgumentResolvers.add(new SessionStatusMethodArgumentResolver());
			// customArgumentResolvers.add(new UriComponentsBuilderMethodArgumentResolver());

			adapter.setCustomArgumentResolvers(customArgumentResolvers);
			// adapter.setArgumentResolvers(customArgumentResolvers);
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
			OnumMethodArgumentResolver argumentResolver = beanFactory.getBean(OnumMethodArgumentResolver.class);
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

		// {
		// UnderlineHandlerMethodArgumentResolver argumentResolver = beanFactory.getBean(UnderlineHandlerMethodArgumentResolver.class);
		// customArgumentResolvers.add(argumentResolver);
		// }
		{
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
