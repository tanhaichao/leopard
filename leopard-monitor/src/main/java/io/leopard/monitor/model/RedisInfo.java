package io.leopard.monitor.model;

import io.leopard.monitor.CapacityUtil;

public class RedisInfo {
	private String server;
	private String maxMemory;
	private String redisRef;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(String maxMemory) {
		this.maxMemory = maxMemory;
	}

	public long parseMaxMemory() {
		return CapacityUtil.parse(this.maxMemory);
	}

	public String getRedisRef() {
		return redisRef;
	}

	public void setRedisRef(String redisRef) {
		this.redisRef = redisRef;
	}

}
