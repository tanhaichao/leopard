package io.leopard.monitor.connection;

import io.leopard.burrow.util.ListUtil;
import io.leopard.jdbc.JdbcConnectionListener;
import io.leopard.redis.RedisConnectionListener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionMonitorServiceImpl implements ConnectionMonitorService {

	@Autowired
	private ConnectionMonitorDao connectionMonitorDao;

	public ConnectionMonitorServiceImpl() {
		System.setProperty(RedisConnectionListener.class.getName(), RedisConnectionListenerImpl.class.getName());
		System.setProperty(JdbcConnectionListener.class.getName(), JdbcConnectionListenerImpl.class.getName());
	}

	@Override
	public boolean add(ConnectionInfo connectionInfo) {
		return connectionMonitorDao.add(connectionInfo);
	}

	@Override
	public List<ConnectionInfo> listAll() {
		List<ConnectionInfo> list = connectionMonitorDao.listAll();
		list = ListUtil.defaultList(list);
		for (ConnectionInfo connectionInfo : list) {
			double avgTime = avgTime(connectionInfo.getTotalTime(), connectionInfo.getConnectionCount());
			double avgConnectionTime = avgTime(connectionInfo.getConnectionTime(), connectionInfo.getConnectionCount());
			connectionInfo.setAvgTime(avgTime);
			connectionInfo.setAvgConnectionTime(avgConnectionTime);
		}
		return list;
	}

	protected double avgTime(long totalTime, long count) {
		if (count == 0) {
			return -1;
		}
		return totalTime / count / 1000 / 1000;
	}
}
