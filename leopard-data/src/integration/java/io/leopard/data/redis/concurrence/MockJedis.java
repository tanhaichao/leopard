package io.leopard.data.redis.concurrence;

import redis.clients.jedis.Jedis;

public class MockJedis extends Jedis {

	MockJedisCommand command;

	public MockJedis(String host, int port, int timeout) {
		super(host, port, timeout);
		this.command = new MockJedisCommand(host, port, timeout);
	}

	public void setCommand(MockJedisCommand command) {
		this.command = command;
	}

	@Override
	public void connect() {
		command.connect();
	}

	@Override
	public boolean isConnected() {
		return command.isConnected();
	}

	@Override
	public String auth(String password) {
		// return super.auth(password);
		return null;
	}

	@Override
	public String select(int index) {
		// return super.select(index);
		return null;
	}

	@Override
	public String quit() {
		// return super.quit();
		return null;
	}

	@Override
	public void disconnect() {
		// super.disconnect();

	}

	@Override
	public String ping() {
		// return super.ping();
		return command.ping();
	}

	@Override
	public String get(String key) {
		return command.get(key);
	}

}
