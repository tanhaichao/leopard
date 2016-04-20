package io.leopard.monitor;

import io.leopard.monitor.model.MonitorConfig;

public class MonitorDaoImpl implements MonitorDao {

	private MonitorDao monitorDao = new MonitorDaoXmlImpl();

	private MonitorConfig config;

	@Override
	public MonitorConfig getMonitorConfig() {
		if (config != null) {
			return config;
		}
		config = this.monitorDao.getMonitorConfig();
		return config;
	}

}
