package io.leopard.monitor.model;

import io.leopard.burrow.util.ListUtil;

import java.util.List;

public class MonitorConfig {
	private boolean enable;
	private List<Notifier> notifierList;
	private List<RedisInfo> redisInfoList;

	private AlarmInfo alarmInfo;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	private BaseInfo baseInfo;

	public List<Notifier> getNotifierList() {
		return notifierList;
	}

	public void setNotifierList(List<Notifier> notifierList) {
		this.notifierList = notifierList;
	}

	public List<RedisInfo> getRedisInfoList() {
		return redisInfoList;
	}

	public void setRedisInfoList(List<RedisInfo> redisInfoList) {
		this.redisInfoList = redisInfoList;
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public void addNotifier(Notifier notifier) {
		this.notifierList = ListUtil.defaultList(this.notifierList);
		this.notifierList.add(notifier);
	}

	public void addRedisInfo(RedisInfo redisInfo) {
		this.redisInfoList = ListUtil.defaultList(this.redisInfoList);
		this.redisInfoList.add(redisInfo);
	}

	public AlarmInfo getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(AlarmInfo alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

}
