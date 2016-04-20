package io.leopard.monitor.connection;

import java.util.Date;

import org.apache.commons.pool2.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;

/**
 * 连接信息.
 * 
 * @author 阿海
 * 
 */
public class ConnectionInfo {

	private String type;
	private int currentSize;// 当前连接数
	private int peakSize;// 峰值数量
	private Date peakTime = new Date();// 峰值时间
	private long connectionCount;// 连接次数
	private int brokenCount;// 出错次数
	private long totalTime;// 总耗时
	private double avgTime;// 平均耗时

	private long connectionTime;// 总建立连接耗时
	private double avgConnectionTime;// 平均建立连接耗时

	private int maxPoolSize;// 最大连接池数量
	private String host;// 服务器IP
	private int port;// 服务器端口
	private String content;// 备注

	private GenericObjectPool<Jedis> pool;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(double avgTime) {
		this.avgTime = avgTime;
	}

	public String getTotalTimeStr() {
		long ms = totalTime / 1000 / 1000;
		if (ms < 1000) {
			return ms + "ms";
		}
		else {
			long seconds = ms / 1000;
			return seconds + "s";
		}
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getConnectionCount() {
		return connectionCount;
	}

	public void setConnectionCount(long connectionCount) {
		this.connectionCount = connectionCount;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public int getPeakSize() {
		return peakSize;
	}

	public void setPeakSize(int peakSize) {
		this.peakSize = peakSize;
	}

	public int getBrokenCount() {
		return brokenCount;
	}

	public void setBrokenCount(int brokenCount) {
		this.brokenCount = brokenCount;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPeakTime() {
		return peakTime;
	}

	public void setPeakTime(Date peakTime) {
		this.peakTime = peakTime;
	}

	// //////
	public synchronized void incrTotalTime(long time) {
		this.totalTime += time;
	}

	public synchronized void incrConnectionCount(long count) {
		this.connectionCount += count;
	}

	public synchronized void incrBrokenCount(int count) {
		this.brokenCount += count;
	}

	public synchronized void incrCurrentSize(int count) {
		this.currentSize += count;
		if (currentSize > this.peakSize) {
			this.peakSize = this.currentSize;
			this.peakTime = new Date();
		}
	}

	public int getNumActive() {
		if (this.pool == null) {
			return this.getCurrentSize();
		}
		return this.pool.getNumActive();
	}

	public int getMaxActive() {
		if (this.pool == null) {
			return this.getPeakSize();
		}
		return this.pool.getNumActive() + pool.getNumIdle();
		// return this.pool.getMaxActive();
	}

	public GenericObjectPool<Jedis> getPool() {
		return pool;
	}

	public void setPool(GenericObjectPool<Jedis> pool) {
		this.pool = pool;
	}

	public long getConnectionTime() {
		return connectionTime;
	}

	public void setConnectionTime(long connectionTime) {
		this.connectionTime = connectionTime;
	}

	public double getAvgConnectionTime() {
		return avgConnectionTime;
	}

	public void setAvgConnectionTime(double avgConnectionTime) {
		this.avgConnectionTime = avgConnectionTime;
	}

	public String getConnectionTimeStr() {
		long ms = this.connectionTime / 1000 / 1000;
		if (ms < 1000) {
			return ms + "ms";
		}
		else {
			long seconds = ms / 1000;
			return seconds + "s";
		}
	}

}
