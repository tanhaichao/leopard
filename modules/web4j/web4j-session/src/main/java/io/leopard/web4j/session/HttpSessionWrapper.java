package io.leopard.web4j.session;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("deprecation")
public class HttpSessionWrapper implements HttpSession {
	protected static final Log LOGGER = LogFactory.getLog(HttpSessionWrapper.class);

	// private HttpSession session;

	private String sid;

	private Map<String, Object> map = null;

	private int expiry;

	private String FMT_CHARSET_KEY = "javax.servlet.jsp.jstl.fmt.request.charset";

	private SessionService sessionService;

	public HttpSessionWrapper(String sid, int expiry, SessionService sessionService) {
		if (StringUtils.isEmpty(sid)) {
			throw new IllegalArgumentException("sessionId怎么会为空?");
		}
		// this.session = session;
		this.sid = sid;
		this.expiry = expiry;
		this.sessionService = sessionService;
	}

	protected Map<String, Object> getMap() {
		if (this.map == null) {
			this.map = sessionService.getSession(sid);
			// System.err.println("getMap sid:" + sid);
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
		sessionService.saveSession(this.sid, this.getMap(), expiry);
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
		sessionService.removeSession(this.sid);
	}

	@Override
	public void removeAttribute(String key) {
		this.getMap().remove(key);
		sessionService.saveSession(this.sid, this.getMap(), expiry);
	}

	@Override
	public long getCreationTime() {
		// return session.getCreationTime();
		throw new NotImplementedException();
	}

	@Override
	public long getLastAccessedTime() {
		// return session.getLastAccessedTime();
		throw new NotImplementedException();
	}

	@Override
	public int getMaxInactiveInterval() {
		// return session.getMaxInactiveInterval();
		throw new NotImplementedException();
	}

	@Override
	public ServletContext getServletContext() {
		// return session.getServletContext();
		throw new NotImplementedException();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		throw new NotImplementedException();
		// return session.getSessionContext();
	}

	@Override
	public Object getValue(String name) {

		return this.getAttribute(name);
		// throw new NotImplementedException();
		// // return session.getValue(arg0);
	}

	@Override
	public String[] getValueNames() {
		throw new NotImplementedException();
		// return session.getValueNames();
	}

	// public void invalidate() {
	// this.session.invalidate();
	// }
	@Override
	public boolean isNew() {
		// return session.isNew();
		throw new NotImplementedException();
	}

	@Override
	public void putValue(String name, Object value) {
		// throw new NotImplementedException();
		// session.putValue(arg0, arg1);
		this.setAttribute(name, value);
	}

	@Override
	public void removeValue(String key) {
		this.removeAttribute(key);
		// throw new NotImplementedException();
		// session.removeValue(arg0);
	}

	//
	// public void setAttribute(String arg0, Object arg1) {
	// this.session.setAttribute(arg0, arg1);
	// }
	@Override
	public void setMaxInactiveInterval(int arg0) {
		// session.setMaxInactiveInterval(arg0);
		throw new NotImplementedException();
	}
}
