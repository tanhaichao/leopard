package io.leopard.web4j.session;

import java.util.Map;

public interface SessionService {
	/**
	 * 是否启用分布式session.
	 * 
	 * @return
	 */
	boolean isEnableDistributedSession();

	Map<String, Object> getSession(String sid);

	void saveSession(String sid, Map<String, Object> session, int seconds);

	void removeSession(String sid);

}
