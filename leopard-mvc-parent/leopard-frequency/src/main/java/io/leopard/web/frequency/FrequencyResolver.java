package io.leopard.web.frequency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.method.HandlerMethod;

public class FrequencyResolver {

	private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();

	public Integer getSeconds(Object handler) {
		int hashCode = handler.hashCode();
		Integer seconds = data.get(hashCode);
		if (seconds != null) {
			return seconds;
		}
		seconds = this.parseSeconds(handler);
		data.put(hashCode, seconds);
		return seconds;
	}

	private int parseSeconds(Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return 0;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Frequency frequency = handlerMethod.getMethodAnnotation(Frequency.class);
		if (frequency == null) {
			return 0;
		}
		return frequency.seconds();
	}
}
