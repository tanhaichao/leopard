package io.leopard.monitor.model;

import io.leopard.monitor.CapacityUtil;

public class BaseInfo {

	private int threadCount;
	private float systemLoadAverage;

	private String usedHeapMemory;
	private String freeHeapMemory;
	private String freePhysicalMemory;

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public float getSystemLoadAverage() {
		return systemLoadAverage;
	}

	public void setSystemLoadAverage(float systemLoadAverage) {
		this.systemLoadAverage = systemLoadAverage;
	}

	public String getUsedHeapMemory() {
		return usedHeapMemory;
	}

	public void setUsedHeapMemory(String usedHeapMemory) {
		this.usedHeapMemory = usedHeapMemory;
	}

	public long parseUsedHeapMemory() {
		return CapacityUtil.parse(usedHeapMemory);
	}

	public String getFreePhysicalMemory() {
		return freePhysicalMemory;
	}

	public void setFreePhysicalMemory(String freePhysicalMemory) {
		this.freePhysicalMemory = freePhysicalMemory;
	}

	public long parseFreePhysicalMemory() {
		return CapacityUtil.parse(freePhysicalMemory);
	}

	public String getFreeHeapMemory() {
		return freeHeapMemory;
	}

	public void setFreeHeapMemory(String freeHeapMemory) {
		this.freeHeapMemory = freeHeapMemory;
	}
}
