package io.leopard.data4j.log;

import org.apache.commons.lang.SystemUtils;

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

		return false;
	}
}
