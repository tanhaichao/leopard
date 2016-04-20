package io.leopard.schema;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import io.leopard.data.signature.SignatureServiceImpl;

public class SignatureBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	protected Class<?> getBeanClass(Element element) {
		return SignatureServiceImpl.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		// <leopard:signature id="newsSignatureService" redis-ref="signatureRedisLog4jImpl" redisKey="news_signature" publicKey="sha1.newsxxxxxxx232232"/>

		String server = element.getAttribute("server");
		String redisRef = element.getAttribute("redis-ref");

		String redisKey = element.getAttribute("redisKey");
		String publicKey = element.getAttribute("publicKey");

		String useBase16 = element.getAttribute("useBase16");
		String checkUsed = element.getAttribute("checkUsed");

		String maxActive = element.getAttribute("maxActive");
		String initialPoolSize = element.getAttribute("initialPoolSize");
		String timeout = element.getAttribute("timeout");

		if (StringUtils.isNotEmpty(server)) {
			builder.addPropertyValue("server", server);
		}
		else if (StringUtils.isNotEmpty(redisRef)) {
			builder.addPropertyReference("redis", redisRef);
		}
		else {
			throw new IllegalArgumentException("<leopard:signature/>必须设置server或redis-ref属性.");
		}

		if (StringUtils.isNotEmpty(redisKey)) {
			builder.addPropertyValue("redisKey", redisKey);
		}
		if (StringUtils.isNotEmpty(publicKey)) {
			builder.addPropertyValue("publicKey", publicKey);
		}
		if (StringUtils.isNotEmpty(useBase16)) {
			builder.addPropertyValue("useBase16", useBase16);
		}
		if (StringUtils.isNotEmpty(checkUsed)) {
			builder.addPropertyValue("checkUsed", checkUsed);
		}

		if (StringUtils.isNotEmpty(maxActive)) {
			builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
		}
		if (StringUtils.isNotEmpty(initialPoolSize)) {
			builder.addPropertyValue("initialPoolSize", Integer.valueOf(initialPoolSize));
		}

		if (StringUtils.isNotEmpty(timeout)) {
			builder.addPropertyValue("timeout", Integer.valueOf(timeout));
		}

		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

	}
}