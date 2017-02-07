package io.leopard.topnb;

import org.junit.Ignore;

import io.leopard.jetty.JettyServer;

@Ignore
public class JettyTest {
	public static void main(String[] args) throws Exception {
		JettyServer.start("src/test/webapp");
	}
}
