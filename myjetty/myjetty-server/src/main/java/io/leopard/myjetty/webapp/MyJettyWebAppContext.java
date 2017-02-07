package io.leopard.myjetty.webapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class MyJettyWebAppContext extends WebAppContext {

	private List<String> hostList;
	private String war;

	public MyJettyWebAppContext(List<String> hostList, String war) {
		this(null, hostList, war);
	}

	public MyJettyWebAppContext(HandlerContainer parent, List<String> hostList, String war) {
		this.hostList = hostList;
		this.war = war;
		this.init();
	}

	private void init() {
		System.out.println("host:" + hostList + " war:" + war);
		this.setContextPath("/");
		this.setWar(war);
		if (hostList != null && !hostList.isEmpty()) {
			if (!"localhost".equals(hostList.get(0))) {
				String[] hosts = new String[hostList.size()];
				hostList.toArray(hosts);
				super.setVirtualHosts(hosts);
			}
		}

		this.setConfigurations(new Configuration[] { //
				new io.leopard.myjetty.webapp.EmbedWebInfConfiguration(hostList, war), //
				new MetaInfConfiguration(), //
				new AnnotationConfiguration(), //
				new WebXmlConfiguration(), //
				new FragmentConfiguration() //
				// new TagLibConfiguration() //
		});
	}

	@Override
	public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// System.out.println("doHandle target:" + target + " baseRequest:" + baseRequest);
		super.doHandle(target, baseRequest, request, response);
	}

	@Override
	public void doScope(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// System.out.println("doScope target:" + target + " baseRequest:" + baseRequest);
		super.doScope(target, baseRequest, request, response);
	}

	@Override
	public boolean isAvailable() {
		boolean isAvailable = super.isAvailable();
		// System.out.println("isAvailable:" + isAvailable);
		return isAvailable;
	}

}
