package io.leopard.monitor.connection;

import io.leopard.burrow.lang.SynchronizedLRUMap;
import io.leopard.monitor.MonitorBeanFactory;
import io.leopard.redis.RedisConnectionListener;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;

public class RedisConnectionListenerImpl implements RedisConnectionListener {

	protected Log logger = LogFactory.getLog(this.getClass());

	private final ConnectionMonitorService connectionMonitorService = MonitorBeanFactory.getConnectionMonitorService();
	protected String host;
	protected int port;

	private final Map<Integer, Long> timeMap = new SynchronizedLRUMap<Integer, Long>(100, 500);

	protected ConnectionInfo connectionInfo;

	@Override
	public void setPoolConfig(String host, int port, int timeout, int maxPoolSize, GenericObjectPool<Jedis> pool) {
		this.host = host;
		this.port = port;

		// System.err.println("setPoolConfig host:" + host + " port:" + port + " maxPoolSize:" + maxPoolSize);
		connectionInfo = new ConnectionInfo();
		connectionInfo.setPort(port);
		connectionInfo.setHost(host);// 是否要解析成IP?
		connectionInfo.setMaxPoolSize(maxPoolSize);

		connectionInfo.setContent("");
		connectionInfo.setType("Redis");

		connectionInfo.setPool(pool);

		connectionMonitorService.add(connectionInfo);
	}

	@Override
	public void open(Jedis resource, long startTime) {
		long currentTime = System.nanoTime();
		long time = currentTime - startTime;

		if (resource == null) {
			// 表示获取连接不成功
			connectionInfo.incrTotalTime(time);
		}
		else {
			timeMap.put(resource.hashCode(), startTime);
		}
		connectionInfo.incrConnectionCount(1);
		connectionInfo.incrCurrentSize(1);
	}

	@Override
	public void close(Jedis resource) {
		// System.out.println("close resource:" + resource);
		Long startTime = timeMap.get(resource.hashCode());
		long time = System.nanoTime() - startTime;
		connectionInfo.incrTotalTime(time);
		connectionInfo.incrCurrentSize(-1);
	}

	@Override
	public void broken() {
		connectionInfo.incrBrokenCount(1);
	}

}
