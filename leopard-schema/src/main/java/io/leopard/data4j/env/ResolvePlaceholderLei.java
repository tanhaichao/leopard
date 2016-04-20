package io.leopard.data4j.env;

import java.util.Properties;

/**
 * 占位符解析.
 * 
 * @author 阿海
 *
 */
public interface ResolvePlaceholderLei {
	/**
	 * 配置文件里不存在的占位符时触发该接口.
	 * 
	 * @param placeholder
	 * @param props
	 * @return
	 */
	String resolvePlaceholder(String placeholder, Properties props);
}
