package io.leopard.web.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
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
import io.leopard.web.mvc.condition.ExtensiveDomain;
import io.leopard.web.mvc.condition.ServerNameRequestCondition;
import io.leopard.web.session.StoreRedisImpl;

public class LeopardHandlerMapping extends RequestMappingHandlerMapping {

	@Value("${mvc.restful}")
	private String restful;

	@PostConstruct
	public void init() {
		isRestful = !"false".equals(restful);
	}

	private static boolean isRestful = false;

	public static boolean isRestful() {
		return isRestful;
	}

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
			info = createRequestMappingInfo2(methodAnnotation, method);
			RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
			if (typeAnnotation != null) {
				info = createRequestMappingInfo2(typeAnnotation, null).combine(info);
			}
		}
		return info;
	}

	/**
	 * Created a RequestMappingInfo from a RequestMapping annotation.
	 */
	protected RequestMappingInfo createRequestMappingInfo2(RequestMapping annotation, Method method) {
		String[] patterns;
		if (method != null && annotation.value().length == 0) {
			patterns = new String[] { this.createPattern(method.getName()) };
		}
		else {
			patterns = resolveEmbeddedValuesInPatterns(annotation.value());
		}
		Map<String, String> headerMap = new LinkedHashMap<String, String>();
		ExtensiveDomain extensiveDomain = new ExtensiveDomain();
		requestMappingInfoBuilder.getHeaders(annotation, method, extensiveDomain, headerMap);
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
		RequestCondition<?> customCondition = new ServerNameRequestCondition(extensiveDomain, headers);
		return new RequestMappingInfo(new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), false, this.useTrailingSlashMatch(), this.getFileExtensions()),
				new RequestMethodsRequestCondition(annotation.method()), new ParamsRequestCondition(annotation.params()), new HeadersRequestCondition(),
				new ConsumesRequestCondition(annotation.consumes(), headers), new ProducesRequestCondition(annotation.produces(), headers, getContentNegotiationManager()), customCondition);
	}

	protected String createPattern(String methodName) {
		if (isRestful) {
			return methodName;
		}
		return methodName + ".do";
	}
}
