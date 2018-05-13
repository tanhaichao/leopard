package io.leopard.vhost;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 虚拟主机
 * 
 * @author 谭海潮
 *
 */
@Order(1)
public class VhostRequestMappingInfoBuilder implements RequestMappingInfoBuilder {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void getHeaders(RequestMapping annotation, Method method, ExtensiveDomain extensiveDomain, Map<String, String> headers) {
		if (method == null) {
			return;
		}

		String[] hosts = this.getVhosts(method);
		if (hosts == null || hosts.length == 0) {
			return;
		}

		List<String> hostList = new ArrayList<>();
		for (String host : hosts) {
			logger.warn("host:" + host);
			if (host.startsWith("*.")) {// 泛域名
				extensiveDomain.addExtensiveDomain(host);
			}
			else {
				hostList.add(host);
			}
		}

		if (!hostList.isEmpty()) {
			String host = StringUtils.join(hostList, ", ");
			headers.put("Host", host);
		}

	}

	/**
	 * 获取vhost配置
	 * 
	 * @param method
	 * @return
	 */
	protected String[] getVhosts(Method method) {
		Vhost vhost = method.getAnnotation(Vhost.class);
		if (vhost != null && vhost.value().length > 0) {
			return vhost.value();
		}
		Class<?> clazz = method.getDeclaringClass();
		vhost = AnnotationUtils.findAnnotation(clazz, Vhost.class);
		if (vhost == null) {
			return null;
		}
		return vhost.value();
	}
}
