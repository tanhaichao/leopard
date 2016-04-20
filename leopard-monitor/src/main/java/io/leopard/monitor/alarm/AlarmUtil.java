package io.leopard.monitor.alarm;

public class AlarmUtil {
	public static String removeUseless(String message) {
		return message.replaceAll("\\[(.*?)\\]", ""); // 匹配"[]"的内容，并去掉
	}

	// public static String getStackTrace(String message, Throwable t) {
	// if (t == null) {
	// return message;
	// }
	// String className = t.getClass().getName();
	//
	// if (message == null) {
	// message = t.getMessage();
	// }
	// message = (message != null) ? (className + ": " + message) : className;
	//
	// StringBuilder sb = new StringBuilder();
	// sb.append(message).append("\n");
	// StackTraceElement[] trace = t.getStackTrace();
	// for (int i = 0; i < trace.length; i++) {
	// sb.append("\tat ").append(trace[i]).append("\n");
	// }
	// return sb.toString();
	// }
}
