package io.leopard.jetty;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import io.leopard.jetty.handler.HostResourceHandler;

public class ResourcesManager {

	private static List<Handler> handlerList = new ArrayList<Handler>();

	public static Handler getHandler() {
		if (handlerList.isEmpty()) {
			return null;
		}
		HandlerCollection handlers = new HandlerCollection();
		for (Handler handler : handlerList) {
			handlers.addHandler(handler);
		}
		return handlers;
	}

	public static void addResource(String host, String resourceBase) {
		HostResourceHandler handler = new HostResourceHandler(host, resourceBase);
		handlerList.add(handler);
	}

}
