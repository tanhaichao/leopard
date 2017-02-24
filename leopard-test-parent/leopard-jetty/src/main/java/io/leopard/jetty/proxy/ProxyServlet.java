package io.leopard.jetty.proxy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpVersion;

public class ProxyServlet extends org.eclipse.jetty.proxy.ProxyServlet.Transparent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final int requestId = getRequestId(request);

		String rewrittenTarget = rewriteTarget(request);

		if (_log.isDebugEnabled()) {
			StringBuffer uri = request.getRequestURL();
			if (request.getQueryString() != null)
				uri.append("?").append(request.getQueryString());
			if (_log.isDebugEnabled())
				_log.debug("{} rewriting: {} -> {}", requestId, uri, rewrittenTarget);
		}

		if (rewrittenTarget == null) {
			onProxyRewriteFailed(request, response);
			return;
		}

		final Request proxyRequest = getHttpClient().newRequest(rewrittenTarget).method(request.getMethod()).version(HttpVersion.fromString(request.getProtocol()));

		copyRequestHeaders(request, proxyRequest);

		addProxyHeaders(request, proxyRequest);

		// final AsyncContext asyncContext = request.startAsync();
		// We do not timeout the continuation, but the proxy request
		// asyncContext.setTimeout(0);
		proxyRequest.timeout(getTimeout(), TimeUnit.MILLISECONDS);

		if (hasContent(request))
			proxyRequest.content(proxyRequestContent(request, response, proxyRequest));

		sendProxyRequest(request, response, proxyRequest);
	}

}
