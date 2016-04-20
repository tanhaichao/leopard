package io.leopard.data;

import io.leopard.burrow.lang.MethodTime;

public class MethodTimeImpl implements MethodTime {

	private static MethodTime instance = new MethodTimeImpl();

	public static MethodTime getInstance() {
		return instance;
	}

	private MethodTime methodTime;

	public void setMethodTime(MethodTime methodTime) {
		this.methodTime = methodTime;
	}

	@Override
	public boolean add(String methodName, long time) {
		if (methodTime == null) {
			return false;
		}
		return methodTime.add(methodName, time);
	}

	@Override
	public boolean addByStartTime(String methodName, long startTime) {
		if (methodTime == null) {
			return false;
		}
		return this.methodTime.addByStartTime(methodName, startTime);
	}

}
