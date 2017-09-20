package io.leopard.jetty.handler;

import java.util.List;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public interface WebInfResolver {

	List<Resource> findClassDirs(WebAppContext context) throws Exception;

	List<Resource> findJars(WebAppContext context) throws Exception;

}
