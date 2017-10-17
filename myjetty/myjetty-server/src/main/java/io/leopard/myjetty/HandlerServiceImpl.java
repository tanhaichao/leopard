package io.leopard.myjetty;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.eclipse.jetty.server.handler.HandlerCollection;

public class HandlerServiceImpl implements HandlerService {

	@Override
	public void execute(HandlerCollection handlers) {
		Iterator<HandlerService> iterator = ServiceLoader.load(HandlerService.class).iterator();
		while (iterator.hasNext()) {
			HandlerService service = iterator.next();
			service.execute(handlers);
		}
	}

}
