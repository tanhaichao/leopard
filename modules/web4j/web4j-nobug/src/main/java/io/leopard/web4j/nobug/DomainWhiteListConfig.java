package io.leopard.web4j.nobug;

import java.util.Set;

/**
 * 域名白名单配置接口.
 * 
 * @author 阿海
 * 
 */
public interface DomainWhiteListConfig {
	
	
	/**
	 * CSRF、JSON劫持漏洞域名白名单
	 * 
	 * @return
	 */
	Set<String> getRefererDomainWhiteSet();
	
}
