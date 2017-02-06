package io.leopard.web.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

public class RequestAttributeListenerImpl implements RequestAttributeListener {
	private static final List<RequestAttributeListener> list = new ArrayList<RequestAttributeListener>();

	static {
		Iterator<RequestAttributeListener> iterator = ServiceLoader.load(RequestAttributeListener.class).iterator();
		while (iterator.hasNext()) {
			RequestAttributeListener listener = iterator.next();
			list.add(listener);
		}
	}

	@Override
	public void attributeGet(HttpServletRequest request, String name, Object value) {
		for (RequestAttributeListener listener : list) {
			listener.attributeGet(request, name, value);
		}
	}

}
