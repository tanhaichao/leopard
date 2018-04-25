package io.leopard.data.env;

import java.io.IOException;

import org.springframework.core.io.Resource;

/**
 * 属性文件解析器
 * 
 * @author 阿海
 *
 */
public interface PropertyFileResolver {

	Resource[] getResources(String env) throws IOException;
}
