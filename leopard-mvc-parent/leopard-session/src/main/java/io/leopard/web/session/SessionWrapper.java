package io.leopard.web.session;

import io.leopard.json.Json;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class SessionWrapper implements HttpSession {
	// protected static final Log LOGGER = LogFactory.getLog(HttpSessionWrapper.class);

	// private HttpSession session;

	private String sid;

	private Map<String, Object> map = null;

	private int expiry;

	private String FMT_CHARSET_KEY = "javax.servlet.jsp.jstl.fmt.request.charset";

	// private SessionService sessionService;

	public SessionWrapper(String sid, int expiry) {
		if (sid == null || sid.length() == 0) {
			throw new IllegalArgumentException("sessionId怎么会为空?");
		}
		this.sid = sid;
		this.expiry = expiry;
		// this.sessionService = sessionService;
	}

	/**
	 * 根据session id获取session key.
	 * 
	 * @param sid
	 * @return
	 */
	protected String getSessionKey(String sid) {
		// AssertUtil.assertNotEmpty(sid, "sessionId怎么会为空?");
		if (sid == null || sid.length() == 0) {
			throw new IllegalArgumentException("sessionId怎么会为空?");
		}
		return "sid:" + sid;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> getMap() {
		if (this.map == null) {
			String key = this.getSessionKey(sid);
			String json = Store.getInstance().get(key);
			this.map = Json.toObject(json, Map.class);
			// this.map = sessionService.getSession(sid);
			if (map == null) {
				map = new LinkedHashMap<String, Object>();
			}
		}
		return this.map;
	}

	@Override
	public String getId() {
		return this.sid;
	}

	@Override
	public void setAttribute(String key, Object value) {
		if (FMT_CHARSET_KEY.equals(key)) {
			return;
		}
		value = JsonSerializer.serialize(value);
		this.getMap().put(key, value);
		// sessionService.saveSession(this.sid, this.getMap(), expiry);

		String json = Json.toJson(this.getMap());
		Store.getInstance().set(getSessionKey(sid), json, expiry);
	}

	@Override
	public Object getAttribute(String name) {
		if (FMT_CHARSET_KEY.equals(name)) {
			return "UTF-8";
		}
		Object value = this.getMap().get(name);
		value = JsonSerializer.unserialize(value);
		return value;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return (new Enumerator(this.getMap().keySet(), true));
	}

	@Override
	public void invalidate() {
		this.getMap().clear();
		// sessionService.removeSession(this.sid);

		String key = this.getSessionKey(sid);
		Store.getInstance().del(key);
	}

	@Override
	public void removeAttribute(String key) {
		this.getMap().remove(key);

		String json = Json.toJson(this.getMap());
		Store.getInstance().set(getSessionKey(sid), json, expiry);
		// sessionService.saveSession(this.sid, this.getMap(), expiry);

	}

	@Override
	public long getCreationTime() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public long getLastAccessedTime() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public int getMaxInactiveInterval() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public ServletContext getServletContext() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public Object getValue(String name) {
		return this.getAttribute(name);
	}

	@Override
	public String[] getValueNames() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public boolean isNew() {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public void putValue(String name, Object value) {
		this.setAttribute(name, value);
	}

	@Override
	public void removeValue(String key) {
		this.removeAttribute(key);
	}

	@Override
	public void setMaxInactiveInterval(int arg0) {
		throw new UnsupportedOperationException("Not Impl.");
	}
}
