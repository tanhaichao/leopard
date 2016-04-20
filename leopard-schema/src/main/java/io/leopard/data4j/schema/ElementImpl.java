package io.leopard.data4j.schema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.DOMException;

public class ElementImpl extends ElementDefaultImpl {
	private final Map<String, String> data = new ConcurrentHashMap<String, String>();

	
	@Override
	public void setAttribute(String name, String value) throws DOMException {
		// System.err.println("setAttribute " + name + ":" + value);
		data.put(name, value);
	}

	@Override
	public String getAttribute(String name) {
		// System.err.println("getAttribute " + name);
		return data.get(name);
	}

	@Override
	public void removeAttribute(String name) throws DOMException {
		data.remove(name);
	}
}
