package io.leopard.jetty.handler;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;

/**
 * 临时实现
 * 
 * @author 谭海潮
 *
 */
public class ResourceAppenderApitestImpl implements ResourceAppender {

	private static Set<String> HOST_SET = new HashSet<String>();
	static {
		HOST_SET.add("fshop.leopard.io");
		HOST_SET.add("ftrade.leopard.io");

		HOST_SET.add("fshop.test.leopard.io");
		HOST_SET.add("ftrade.test.leopard.io");
	}

	@Override
	public void append(HttpServletRequest request, String path, StringBuffer sb) {
		if (!"/js/jquery.min.js".equals(path)) {
			return;
		}
		String serverName = request.getServerName();
		if (!HOST_SET.contains(serverName)) {
			return;
		}

		String filename = "/home/workspace/apitest/apitest-web/src/main/resources/";
		sb.append("\n\nHello APITest...");
	}

}
