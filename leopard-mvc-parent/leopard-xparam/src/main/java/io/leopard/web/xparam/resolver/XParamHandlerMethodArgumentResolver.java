package io.leopard.web.xparam.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import io.leopard.web.xparam.XParam;
import io.leopard.web.xparam.api.UserinfoResolverImpl;

/**
 * 页面特殊参数.
 * 
 * @author 阿海
 *
 */
@Component
public class XParamHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver implements BeanFactoryAware {
	// TODO ahai 这里有必要使用线程安全的Map吗？
	private static final Map<String, List<XParam>> data = new HashMap<String, List<XParam>>();

	protected Log logger = LogFactory.getLog(this.getClass());

	// public XParamHandlerMethodArgumentResolver() {
	// Iterator<XParam> iterator = ServiceLoader.load(XParam.class).iterator();
	// while (iterator.hasNext()) {
	// XParam xparam = iterator.next();
	// data.put(xparam.getKey(), xparam);
	// }
	// }

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		UserinfoResolverImpl.setBeanFactory(beanFactory);

		ListableBeanFactory factory = (ListableBeanFactory) beanFactory;
		Map<String, XParam> map = factory.getBeansOfType(XParam.class);
		for (Entry<String, XParam> entry : map.entrySet()) {
			XParam xparam = entry.getValue();
			List<XParam> xparamList = data.get(xparam.getKey());
			if (xparamList == null) {
				xparamList = new ArrayList<XParam>();
				data.put(xparam.getKey(), xparamList);
			}
			xparamList.add(xparam);
		}
		for (Entry<String, List<XParam>> entry : data.entrySet()) {
			List<XParam> xparamList = entry.getValue();
			if (xparamList.size() > 1) {
				AnnotationAwareOrderComparator.sort(xparamList);
			}
		}
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		String name = parameter.getParameterName();
		boolean isSpecialName = data.containsKey(name);
		// logger.info("supportsParameter name:" + name + " isSpecialName:" + isSpecialName);
		return isSpecialName;
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		// System.err.println("resolveName name:" + name);
		List<XParam> xparamList = data.get(name);
		if (xparamList == null) {
			throw new IllegalArgumentException("未知参数名称[" + name + "].");
		}
		Object value = null;
		for (XParam xparam : xparamList) {
			value = xparam.getValue((HttpServletRequest) request.getNativeRequest(), parameter);
			// logger.info("resolveName key:" + xparam.getKey() + " value:" + value + " xparam:" + xparam.getClass().getName());
			if (value != null) {
				break;
			}
		}
		return value;
	}

}
