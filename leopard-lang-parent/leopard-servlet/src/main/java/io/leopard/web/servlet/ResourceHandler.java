package io.leopard.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;

public interface ResourceHandler {

	Resource doHandler(String uri, HttpServletRequest request, HttpServletResponse response);
}
