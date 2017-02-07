package io.leopard.myjetty.workbench;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WorkbenchHandler extends ServletContextHandler {

	public WorkbenchHandler(Server server) {
		super(server, "/workbench");
		this.addServlet(new ServletHolder(new WelcomeServlet()), "/");
		this.addServlet(new ServletHolder(new WorkbenchServlet()), "/manager");

		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/start");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/stop");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/restart");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/deploy");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/update");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/packaging");
		this.addServlet(new ServletHolder(new WebappServlet()), "/webapp/compile");

		this.addServlet(new ServletHolder(new ApiServlet()), "/api");
		this.addServlet(new ServletHolder(new FlushServlet()), "/flush");

		try {
			this.addServlet(new ServletHolder(new FileServlet()), "/file.leo");
		}
		catch (Error e) {

		}
		// context.addServlet(DefaultServlet.class, "/*");
	}

}
