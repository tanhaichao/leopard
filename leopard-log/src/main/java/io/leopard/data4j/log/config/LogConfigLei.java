package io.leopard.data4j.log.config;

import org.springframework.core.io.Resource;

/**
 * 日志配置.
 * 
 * @author 阿海
 *
 */
public interface LogConfigLei {

	void configure();

	Resource getResource(String env);
}
