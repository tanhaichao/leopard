package io.leopard.monitor;

import io.leopard.monitor.alarm.AlarmModule;
import io.leopard.monitor.alarm.AlarmService;
import io.leopard.monitor.model.BaseInfo;
import io.leopard.monitor.model.MonitorConfig;

import org.springframework.beans.factory.annotation.Autowired;

public class CpuMonitor implements IMonitor {

	// @Autowired
	private AlarmService alarmService = AlarmModule.getAlarmService();

	@Autowired
	private MonitorConfig monitorConfig;

	@Override
	public void monitor() {
		double systemLoadAverage = JvmManagement.getSystemLoadAverage();

		BaseInfo baseInfo = monitorConfig.getBaseInfo();
		float systemLoadAverageThreshold = baseInfo.getSystemLoadAverage();
		// System.err.println("checkThreadCount systemLoadAverageThreshold:" + systemLoadAverageThreshold + " systemLoadAverage:" + systemLoadAverage);
		if (systemLoadAverage > systemLoadAverageThreshold) {
			// 报警.
			alarmService.send("CPU异常，当前[" + systemLoadAverage + "] 阀值[" + systemLoadAverageThreshold + "]");
		}
	}
}
