package io.leopard.data.env;

import org.springframework.core.io.Resource;

/**
 * 占位符
 * 
 * @author 阿海
 *
 */
public interface PropertyPlaceholderLei {

	Resource[] getResources(String env);
}
