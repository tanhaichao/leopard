package io.leopard.web.frequency;

import javax.servlet.http.HttpServletRequest;

public interface IStore {

	Object getPassport(HttpServletRequest request, Object handler);

	boolean set(String key);

	void expire(String key, int seconds);

}
