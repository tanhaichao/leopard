package io.leopard.web4j.parameter;

import io.leopard.web4j.servlet.RequestUtil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

/**
 * 获取离服务器最近的机器IP.
 * 
 * @author 阿海
 * 
 */
@Service
public class ProxyIpPageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		return RequestUtil.getProxyIp(request);
	}

	@Override
	public String getKey() {
		return "proxyIp";
	}

}
