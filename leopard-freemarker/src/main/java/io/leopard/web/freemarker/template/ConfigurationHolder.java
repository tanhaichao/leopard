package io.leopard.web.freemarker.template;

import freemarker.template.Configuration;

public class ConfigurationHolder {
	private static ThreadLocal<Configuration> configurationHolder = new ThreadLocal<Configuration>();

	public static Configuration getConfiguration() {
		return configurationHolder.get();
	}

	public static void setConfiguration(Configuration configuration) {
		configurationHolder.set(configuration);
	}

}
