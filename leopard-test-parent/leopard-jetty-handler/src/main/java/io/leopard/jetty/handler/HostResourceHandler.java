package io.leopard.jetty.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class HostResourceHandler extends ResourceHandler {

	private String host;

	public HostResourceHandler(String resourceBase) {
		this(null, resourceBase);
	}

	public HostResourceHandler(String host, String resourceBase) {
		this.host = host;
		super.setResourceBase(resourceBase);
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// System.out.println("HostResourceHandler handle serverName:" + request.getServerName() + " host:" + host + " resourceBase:" + this.getResourceBase());
		if (host != null && host.length() > 0) {
			String serverName = request.getServerName();
			// System.out.println("HostResourceHandler serverName:" + serverName + " host:" + host);
			if (!serverName.equals(host)) {
				return;
			}
		}
		super.handle(target, baseRequest, request, response);
		// System.out.println("HostResourceHandler isCommitted:" + response.isCommitted() + " isHandled:" + baseRequest.isHandled() + " target:" + target + " uri:" + request.getRequestURI());

	}

	protected Resource getResource(HttpServletRequest request) throws MalformedURLException {
		System.err.println("getResource uri:" + request.getRequestURI());
		// return super.getResource(request);
		// }
		//
		// @Override
		// public Resource getResource(String path) {
		// System.err.println("getResource path:" + path);

		Resource resource = super.getResource(request);
		if (resource == null || !resource.exists()) {
			return resource;
		}

		String path = request.getRequestURI();
		if ("/js/jquery.min.js".equals(path)) {
			try {
				resource = this.append(request, resource, path);
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return resource;
	}

	private static ResourceAppender resourceAppender = new ResourceAppenderImpl();

	protected Resource append(HttpServletRequest request, Resource resource, String path) throws IOException {
		InputStream input = resource.getInputStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int i = -1;
		while ((i = input.read()) != -1) {
			output.write(i);
		}
		input.close();
		String content = output.toString();
		StringBuffer sb = new StringBuffer(content);
		resourceAppender.append(request, path, sb);
		Resource resource2 = new StringResource(sb.toString());
		// /js/jquery.min.js
		// System.err.println("HostResourceHandler getResource:");
		return resource2;
	}

}
