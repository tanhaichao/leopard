package io.leopard.data4j.log;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import io.leopard.data4j.log.config.LogConfigLeiImpl;

public class PropertyConfigurator {

	public PropertyConfigurator() {
		if (!isDisable()) {
			new LogConfigLeiImpl().configure();
		}
	}

	protected boolean isDisable() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return false;
		}
		if (SystemUtils.IS_OS_LINUX) {
			String env = System.getenv("ENV");
			String jettyHome = System.getenv("JETTY_HOME");
			if (StringUtils.isNotEmpty(env) && StringUtils.isNotEmpty(jettyHome)) {
				return true;
			}
		}
		return false;
	}
}
