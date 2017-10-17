package io.leopard.myjetty;

import org.eclipse.jetty.server.handler.HandlerCollection;

public interface HandlerService {
	void execute(HandlerCollection handlers);
}
