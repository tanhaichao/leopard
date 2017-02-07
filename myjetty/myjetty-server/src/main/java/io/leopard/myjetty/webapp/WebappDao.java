package io.leopard.myjetty.webapp;

import java.util.List;

import org.eclipse.jetty.webapp.WebAppContext;

public interface WebappDao {

	WebAppContext add(String war, List<String> hostList) throws Exception;

	WebAppContext get(String war);

	WebAppContext remove(String war);

}
