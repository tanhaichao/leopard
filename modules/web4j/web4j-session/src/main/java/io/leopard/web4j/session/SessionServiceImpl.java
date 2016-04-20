package io.leopard.web4j.session;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.Json;
import io.leopard.burrow.lang.MethodTime;
import io.leopard.burrow.util.ObjectUtil;
import io.leopard.redis.Redis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionServiceImpl implements SessionService {

	protected static final Log logger = LogFactory.getLog(SessionServiceImpl.class);

	private MethodTime METHOD_TIME;

	private Redis redis;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	@Override
	public boolean isEnableDistributedSession() {
		return ObjectUtil.isNotNull(redis);
	}

	/**
	 * 根据session id获取session key.
	 * 
	 * @param sid
	 * @return
	 */
	protected String getSessionKey(String sid) {
		AssertUtil.assertNotEmpty(sid, "sessionId怎么会为空?");
		return "sid:" + sid;
	}

	/**
	 * 根据session id获取session对象.
	 * 
	 * @param sid
	 * @return
	 */
	@Override
	public Map<String, Object> getSession(String sid) {
		long startTime = System.nanoTime();
		try {
			String key = this.getSessionKey(sid);
			String json = this.redis.get(key);
			Map<String, Object> session = Json.toNotNullMap(json);
			return session;
		}
		finally {
			if (METHOD_TIME != null) {
				METHOD_TIME.addByStartTime("io.leopard.web.session.SessionServiceImpl.getSession", startTime);
			}
		}
	}

	/**
	 * 保存Session对象.
	 * 
	 * @param sid
	 *            id
	 * @param session
	 *            map对象
	 * @param seconds
	 *            保存时间
	 */

	@Override
	public void saveSession(String sid, Map<String, Object> session, int seconds) {
		long startTime = System.nanoTime();
		try {
			String key = this.getSessionKey(sid);
			String json = Json.toJson(session);
			this.redis.set(key, json, seconds);
		}
		finally {
			if (METHOD_TIME != null) {
				METHOD_TIME.addByStartTime("io.leopard.web.session.SessionServiceImpl.saveSession", startTime);
			}
		}
	}

	/**
	 * 移除session对象.
	 * 
	 * @param sid
	 */
	@Override
	public void removeSession(String sid) {
		long startTime = System.nanoTime();
		try {
			String key = this.getSessionKey(sid);
			this.redis.del(key);
		}
		finally {
			if (METHOD_TIME != null) {
				METHOD_TIME.addByStartTime("io.leopard.web.session.SessionServiceImpl.removeSession", startTime);
			}
		}
	}

}
