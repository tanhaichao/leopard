package io.leopard.monitor;

import io.leopard.monitor.alarm.AlarmModule;
import io.leopard.monitor.alarm.AlarmService;
import io.leopard.monitor.model.BaseInfo;
import io.leopard.monitor.model.MonitorConfig;

import org.springframework.beans.factory.annotation.Autowired;

public class ThreadCountMonitor implements IMonitor {
	// @Autowired
	private AlarmService alarmService = AlarmModule.getAlarmService();

	@Autowired
	private MonitorConfig monitorConfig;

	@Override
	public void monitor() {

		int threadCount = JvmManagement.getThreadCount();
		BaseInfo threadCountInfo = this.monitorConfig.getBaseInfo();

		int threadCountThreshold = threadCountInfo.getThreadCount();
		// System.err.println("checkThreadCount activeCount:" + activeCount + " thresholdNum:" + thresholdNum);
		if (threadCount > threadCountThreshold) {
			// 报警.
			alarmService.send("线程数量异常，当前[" + threadCount + "] 阀值[" + threadCountThreshold + "]");
		}
	}

}
