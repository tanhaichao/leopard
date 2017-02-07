package io.leopard.myjetty.webapp.classpath;

import java.util.List;

import org.eclipse.jetty.webapp.WebAppContext;

public class ClassPathServiceDataSourceImpl extends AbstractClassPathService {

	@Override
	public void preConfigure(WebAppContext context, List<String> hostList, String war) {
		// String path;
		// if (SystemUtil.isWindows()) {
		// path = "/work/ytest/ytest/ytest-data/target/classes";
		// }
		// else {
		// path = "/data/src/ytest/ytest-data/target/classes";
		// }
		// this.addClassPath(context, newResource(path));
	}
}
