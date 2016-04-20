package io.leopard.data4j.log;

import io.leopard.data4j.log.config.LogConfigLeiImpl;

public class PropertyConfigurator {

	public PropertyConfigurator() {
		// new Exception("new PropertyConfigurator").printStackTrace();
		new LogConfigLeiImpl().configure();
	}
}
