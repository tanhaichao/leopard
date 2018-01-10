package io.leopard.vhost;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 虚拟主机
 * 
 * @author 谭海潮
 *
 */
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
		String host = StringUtils.join(hosts, ", ");
		headers.put("Host", host);
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
