package io.leopard.data4j.env;

import java.util.Properties;

public class ResolvePlaceholderLeiImpl implements ResolvePlaceholderLei {

	// 配置文件里不存在的占位符时触发该接口.
	@Override
	public String resolvePlaceholder(String placeholder, Properties props) {
		boolean isDomain = isDomain(placeholder);
		String value;
		if (isDomain) {
			value = placeholder;
		}
		else if (placeholder.endsWith(".redis")) {
			value = resolveRedisDsnPlaceholder(placeholder, props);
		}
		else {
			value = props.getProperty(placeholder);
		}
		return value;
	}

	// TODO ahai 这个换成默认域名?
	private static final String[] DOMAINS = new String[] { "duowan.com", "yy.com", "kuaikuai.cn", "laopao.com", "leopard.io" };

	protected static boolean isDomain(String placeholder) {
		for (String domain : DOMAINS) {
			if (placeholder.endsWith(domain)) {
				return true;
			}
		}
		return false;
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
