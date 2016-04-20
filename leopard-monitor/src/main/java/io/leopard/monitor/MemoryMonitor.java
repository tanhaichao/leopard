package io.leopard.monitor;

import io.leopard.monitor.alarm.AlarmModule;
import io.leopard.monitor.alarm.AlarmService;
import io.leopard.monitor.model.BaseInfo;
import io.leopard.monitor.model.MonitorConfig;

import org.springframework.beans.factory.annotation.Autowired;

public class MemoryMonitor implements IMonitor {
	// @Autowired
	private AlarmService alarmService = AlarmModule.getAlarmService();
	@Autowired
	private MonitorConfig monitorConfig;

	@Override
	public void monitor() {
		this.usedHeapMemory();
		this.freePhysicalMemory();
	}

	protected void freePhysicalMemory() {
		long freePhysicalMemory = JvmManagement.getFreePhysicalMemorySize();
		BaseInfo baseInfo = monitorConfig.getBaseInfo();
		long freePhysicalMemoryThreshold = baseInfo.parseFreePhysicalMemory();
		if (freePhysicalMemory < freePhysicalMemoryThreshold) {
			// alarmService.send("系统已使用内存异常，当前[" + freePhysicalMemory + "] 阀值[" + freePhysicalMemoryThreshold + "]");
		}
	}

	protected void usedHeapMemory() {
		long usedHeapMemory = JvmManagement.getUsedHeapMemorySize();

		BaseInfo baseInfo = monitorConfig.getBaseInfo();
		long usedHeapMemoryThreshold = baseInfo.parseUsedHeapMemory();
		if (usedHeapMemory > usedHeapMemoryThreshold) {
			alarmService.send("JVM堆已使用内存异常，当前[" + usedHeapMemory + "] 阀值[" + usedHeapMemoryThreshold + "]");
		}
	}
}
