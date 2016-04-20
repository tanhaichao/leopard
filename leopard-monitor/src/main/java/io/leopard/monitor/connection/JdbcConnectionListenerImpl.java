package io.leopard.monitor.connection;

import io.leopard.burrow.lang.SynchronizedLRUMap;
import io.leopard.jdbc.JdbcConnectionListener;
import io.leopard.monitor.MonitorBeanFactory;

import java.sql.Connection;
import java.util.Map;

public class JdbcConnectionListenerImpl implements JdbcConnectionListener {
	private ConnectionMonitorService connectionMonitorService = MonitorBeanFactory.getConnectionMonitorService();
	protected String host;
	protected int port;

	private Map<Integer, Long> timeMap = new SynchronizedLRUMap<Integer, Long>(100, 500);

	protected ConnectionInfo connectionInfo;

	@Override
	public void setPoolConfig(String host, int port, int timeout, int maxPoolSize, String database) {
		this.host = host;
		this.port = port;

		// System.err.println("setPoolConfig host:" + host + " port:" + port + " maxPoolSize:" + maxPoolSize);
		connectionInfo = new ConnectionInfo();
		connectionInfo.setPort(port);
		connectionInfo.setHost(host);// 是否要解析成IP?
		connectionInfo.setMaxPoolSize(maxPoolSize);

		connectionInfo.setContent("");
		connectionInfo.setType("Jdbc");
		// connectionInfo.setContent("database:" + database);
		connectionMonitorService.add(connectionInfo);
	}

	@Override
	public void open(Connection connection, long startTime) {
		if (connection == null) {
			// 表示获取连接不成功
			long time = System.nanoTime() - startTime;
			connectionInfo.incrTotalTime(time);
		}
		else {
			timeMap.put(connection.hashCode(), startTime);
		}

		connectionInfo.incrConnectionCount(1);
		connectionInfo.incrCurrentSize(1);
	}

	@Override
	public void close(Connection connection) {
		// System.out.println("close connection:" + connection);
		Long startTime = timeMap.get(connection.hashCode());
		long time = System.nanoTime() - startTime;
		connectionInfo.incrTotalTime(time);
		connectionInfo.incrCurrentSize(-1);
	}

	@Override
	public void broken() {
		connectionInfo.incrBrokenCount(1);
	}

}
