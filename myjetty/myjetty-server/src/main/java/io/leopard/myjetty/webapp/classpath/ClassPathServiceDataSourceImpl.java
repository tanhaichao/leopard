package io.leopard.myjetty.webapp.classpath;

import java.util.List;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class ClassPathServiceDataSourceImpl extends AbstractClassPathService {

	@Override
	public List<Resource> preConfigure(WebAppContext context, List<String> hostList, String war) {
		return null;
	}
}
