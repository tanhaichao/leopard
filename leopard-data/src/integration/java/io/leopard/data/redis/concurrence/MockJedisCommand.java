package io.leopard.data.redis.concurrence;

public class MockJedisCommand {
	protected String host;
	protected int port;
	protected int timeout;

	public MockJedisCommand(String host, int port, int timeout) {
		this.host = host;
		this.port = port;
		this.timeout = timeout;
	}

	public void connect() {
		// SystemUtil.sleep(1);
		// System.out.println("connect host:" + host + " time:" + DateTime.getTime());
	}

	public boolean isConnected() {
		// SystemUtil.sleep(1000);
		// System.out.println("isConnected host:" + host + " time:" + DateTime.getTime());
		return true;
	}

	public String ping() {
		// SystemUtil.sleep(10000);
		// System.out.println("ping host:" + host + " time:" + DateTime.getTime());
		return "PONG";
	}

	public String get(String key) {
		// SystemUtil.sleep(1);
		return null;
	}
}
