package io.leopard.web.frequency;

import javax.servlet.http.HttpServletRequest;

public class FrequencyChecker {
	private IStore store = Store.getInstance();

	public void check(HttpServletRequest request, int seconds, Object handler) throws FrequencyException {
		Object passport = this.store.getPassport(request, handler);
		if (passport == null) {
			return;
		}
		String requestUri = request.getRequestURI();// 包含ContextPath也没有问题
		String key = "ul:" + passport + ":" + requestUri;
		boolean success = store.set(key);
		if (!success) {
			throw new FrequencyException(passport, requestUri);
		}
		store.expire(key, seconds);
	}

}
