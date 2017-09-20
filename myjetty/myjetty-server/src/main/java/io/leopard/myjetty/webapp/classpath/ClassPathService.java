package io.leopard.myjetty.webapp.classpath;

import java.util.List;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public interface ClassPathService {

	List<Resource> preConfigure(WebAppContext context, List<String> hostList, String war) throws Exception;
}
