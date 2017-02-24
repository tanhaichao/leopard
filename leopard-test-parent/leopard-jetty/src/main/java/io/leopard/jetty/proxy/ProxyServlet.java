package io.leopard.jetty.proxy;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.util.Callback;

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

	protected void onProxyResponseSuccess(HttpServletRequest clientRequest, HttpServletResponse proxyResponse, Response serverResponse) {
		if (_log.isDebugEnabled())
			_log.debug("{} proxying successful", getRequestId(clientRequest));

		// AsyncContext asyncContext = clientRequest.getAsyncContext();
		// asyncContext.complete();
	}

	protected void onResponseContent(HttpServletRequest request, HttpServletResponse response, Response proxyResponse, byte[] buffer, int offset, int length, Callback callback) {
		try {
			// if (_log.isDebugEnabled())
			_log.info("{} proxying content to downstream: {} bytes", getRequestId(request), length);
			// System.err.println("buffer:" + new String(buffer));
			// response.setHeader("version", "1.1");
			// org.eclipse.jetty.server.Request req = (org.eclipse.jetty.server.Request) request;

			// req.setHttpVersion(HttpVersion.HTTP_1_1);
			// req.getHttpVersion().

			// HttpVersion dd;
			// org.eclipse.jetty.server.Response res = (org.eclipse.jetty.server.Response) response;
			// // res.
			// org.eclipse.jetty.server.HttpOutput output = (HttpOutput) response.getOutputStream();
			//
			// HttpConnection conn = (HttpConnection) output.getInterceptor();
			// System.out.println("response.getOutputStream():" + response.getOutputStream().getClass().getName());
			System.err.println("response:" + response.getClass().getName());

			// response.getWriter().write(new String(buffer));
			// response.flushBuffer();
//			response.getOutputStream().write(buffer, offset, length);
			OutputStream out = response.getOutputStream();
			out.write(buffer);
			out.flush();
			callback.succeeded();
		}
		catch (Throwable x) {
			callback.failed(x);
		}
	}

	protected void onProxyResponseFailure(HttpServletRequest clientRequest, HttpServletResponse proxyResponse, Response serverResponse, Throwable failure) {
		// if (_log.isDebugEnabled())
		_log.info(getRequestId(clientRequest) + " proxying failed", failure);

		if (proxyResponse.isCommitted()) {
			try {
				// Use Jetty specific behavior to close connection.
				proxyResponse.sendError(-1);
				// AsyncContext asyncContext = clientRequest.getAsyncContext();
				// asyncContext.complete();
			}
			catch (IOException x) {
				if (_log.isDebugEnabled())
					_log.debug(getRequestId(clientRequest) + " could not close the connection", failure);
			}
		}
		else {
			proxyResponse.resetBuffer();
			int status = failure instanceof TimeoutException ? HttpStatus.GATEWAY_TIMEOUT_504 : HttpStatus.BAD_GATEWAY_502;
			sendProxyResponseError(clientRequest, proxyResponse, status);
		}
	}
}
