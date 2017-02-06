package io.leopard.mvc.trynb;

import io.leopard.mvc.trynb.model.TrynbInfo;

import javax.servlet.http.HttpServletRequest;

public interface TrynbService {

	// ErrorConfig find(String url);

	TrynbInfo parse(HttpServletRequest request, String uri, Exception exception);

}
