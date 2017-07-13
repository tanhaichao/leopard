package io.leopard.data.env;

import java.util.Properties;

public class ResolvePlaceholderLeiImpl implements ResolvePlaceholderLei {

	// 配置文件里不存在的占位符时触发该接口.
	@Override
	public String resolvePlaceholder(String placeholder, Properties props) {
		String value;
		if (placeholder.endsWith(".redis")) {
			value = resolveRedisDsnPlaceholder(placeholder, props);
		}
		else {
			value = props.getProperty(placeholder);
		}
		return value;
	}

	/**
	 * 解析redis数据源.
	 */
	protected String resolveRedisDsnPlaceholder(String placeholder, Properties props) {
		String hostPlaceholder = placeholder + ".host";
		String portPlaceholder = placeholder + ".port";
		return props.getProperty(hostPlaceholder) + ":" + props.getProperty(portPlaceholder);
	}
}
