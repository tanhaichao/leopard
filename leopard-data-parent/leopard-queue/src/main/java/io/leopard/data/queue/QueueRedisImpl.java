package io.leopard.data.queue;

import io.leopard.redis.RedisImpl;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class QueueRedisImpl implements Queue, InitializingBean, DisposableBean {

	private Log logger = LogFactory.getLog(this.getClass());

	private RedisImpl redis;

	protected String server;
	protected String password;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		redis = new RedisImpl(server, 16, 1000 * 10);
		redis.init();
	}

	@Override
	public void destroy() {
		redis.destroy();
	}

	@Override
	public void publish(String routingKey, String message) {
		redis.rpush(routingKey, message);
	}

	@Override
	public void subscribe(final String queue, final IConsumer callback) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					subscribe2(queue, callback);
				}
				catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}, 0, 1000);

	}

	private void subscribe2(final String queue, final IConsumer callback) {
		while (true) {
			String message = redis.lpop(queue);
			if (message == null) {
				break;
			}
			try {
				callback.consume(message);
			}
			catch (Exception e) {
				logger.error("message:" + message);
				logger.error(e.getMessage(), e);
			}
		}
	}

}
