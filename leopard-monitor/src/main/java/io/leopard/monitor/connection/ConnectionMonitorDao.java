package io.leopard.monitor.connection;

import java.util.List;

public interface ConnectionMonitorDao {

	boolean add(ConnectionInfo connectionInfo);

	List<ConnectionInfo> listAll();

}
