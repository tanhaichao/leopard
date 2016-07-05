package io.leopard.web.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import io.leopard.redis.Redis;
import io.leopard.web.session.StoreRedisImpl;

public class LeopardHandlerMapping extends RequestMappingHandlerMapping {

	private RequestMappingInfoBuilder requestMappingInfoBuilder;

	@Override
	protected void initApplicationContext() throws BeansException {
		// new Exception("initApplicationContext").printStackTrace();
		// 修改拦截器排序
		try {
			Field field = AbstractHandlerMapping.class.getDeclaredField("interceptors");
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			List<Object> interceptors = (List<Object>) field.get(this);
			AnnotationAwareOrderComparator.sort(interceptors);
			// System.out.println("interceptors:" + interceptors);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		super.initApplicationContext();
	}

	@Override
	protected void initApplicationContext(ApplicationContext context) throws BeansException {

		try {
			Redis redis = (Redis) context.getBean("sessionRedis");
			StoreRedisImpl.setRedis(redis);
		}
		catch (NoSuchBeanDefinitionException e) {
			logger.warn("没有配置sessionRedis，不启用分布式session.");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		super.initApplicationContext(context);
		requestMappingInfoBuilder = new RequestMappingInfoBuilderImpl(context);
	}

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = null;
		RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		if (methodAnnotation != null) {
			RequestCondition<?> methodCondition = getCustomMethodCondition(method);
			info = createRequestMappingInfo(methodAnnotation, methodCondition, method);
			RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
			if (typeAnnotation != null) {
				RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
				info = createRequestMappingInfo(typeAnnotation, typeCondition, null).combine(info);
			}
		}
		return info;
	}

	/**
	 * Created a RequestMappingInfo from a RequestMapping annotation.
	 */
	protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition, Method method) {
		String[] patterns;
		if (method != null && annotation.value().length == 0) {
			patterns = new String[] { this.createPattern(method.getName()) };
		}
		else {
			patterns = resolveEmbeddedValuesInPatterns(annotation.value());
		}
		Map<String, String> headerMap = new LinkedHashMap<String, String>();
		{
			for (String header : annotation.headers()) {
				int index = header.indexOf("=");
				String key = header.substring(0, index);
				String value = header.substring(index + 1);
				if (!headerMap.containsKey(key)) {
					headerMap.put(key, value);
				}
			}
		}
		requestMappingInfoBuilder.getHeaders(annotation, method, headerMap);
		// System.out.println("headerMap:" + headerMap);
		String[] headers = new String[headerMap.size()];
		{
			int i = 0;
			for (Entry<String, String> entry : headerMap.entrySet()) {
				String header = entry.getKey() + "=" + entry.getValue();
				headers[i] = header;
				i++;
			}
		}
		return new RequestMappingInfo(new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), false, this.useTrailingSlashMatch(), this.getFileExtensions()),
				new RequestMethodsRequestCondition(annotation.method()), new ParamsRequestCondition(annotation.params()), new HeadersRequestCondition(headers),
				new ConsumesRequestCondition(annotation.consumes(), headers), new ProducesRequestCondition(annotation.produces(), headers, getContentNegotiationManager()), customCondition);
	}

	protected String createPattern(String methodName) {
		return methodName + ".do";
	}
}
