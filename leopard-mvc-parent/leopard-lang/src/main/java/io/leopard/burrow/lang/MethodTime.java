package io.leopard.burrow.lang;

public interface MethodTime {

	boolean add(String methodName, long time);

	boolean addByStartTime(String methodName, long startTime);

	// void init();
}


