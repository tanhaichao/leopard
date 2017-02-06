package io.leopard.tomcat;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

public class TomcatServer {

	public static void start() throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setHostname("localhost");
		tomcat.setPort(80);
		// tomcat.setBaseDir("D:\\work\\zhongcao\\zhongcao\\zhongcao-web");
		// tomcat.addWebapp("WebRoot", "D:\\work\\zhongcao\\zhongcao\\zhongcao-web\\target\\zhongcao-web");
		StandardContext ctx = (StandardContext) tomcat.addWebapp("/", "D:\\work\\zhongcao\\zhongcao\\zhongcao-web\\target\\zhongcao-web");
		StandardRoot resources = new StandardRoot(ctx);
		resources.setCachingAllowed(false);
		ctx.setResources(resources);
		tomcat.start();
	}
}
