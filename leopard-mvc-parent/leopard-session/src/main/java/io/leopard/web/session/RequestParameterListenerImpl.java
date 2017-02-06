package io.leopard.web.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

public class RequestParameterListenerImpl implements RequestParameterListener {

	private static final List<RequestParameterListener> list = new ArrayList<RequestParameterListener>();

	static {
		Iterator<RequestParameterListener> iterator = ServiceLoader.load(RequestParameterListener.class).iterator();
		while (iterator.hasNext()) {
			RequestParameterListener listener = iterator.next();
			list.add(listener);
		}
	}

	@Override
	public void parameterGet(HttpServletRequest request, String name, String[] values) {
		for (RequestParameterListener listener : list) {
			listener.parameterGet(request, name, values);
		}
	}

}
