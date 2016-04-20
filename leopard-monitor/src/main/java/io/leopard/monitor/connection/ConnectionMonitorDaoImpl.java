package io.leopard.monitor.connection;

import java.util.ArrayList;
import java.util.List;

public class ConnectionMonitorDaoImpl implements ConnectionMonitorDao {

	private List<ConnectionInfo> data = new ArrayList<ConnectionInfo>();

	@Override
	public boolean add(ConnectionInfo connectionInfo) {

		this.data.add(connectionInfo);
		return true;
	}

	@Override
	public List<ConnectionInfo> listAll() {

		return data;
	}
}
