package io.leopard.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;

public interface ResourceTransformer {

	Resource transform(HttpServletRequest request, Resource resource) throws IOException;

}
