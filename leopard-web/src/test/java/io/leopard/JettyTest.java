package io.leopard;

import io.leopard.jetty.JettyServer;

import org.junit.Ignore;

@Ignore
public class JettyTest {

	public static void main(String[] args) throws Exception {
		JettyServer.start();
		// JettyServer.start("/leopard", 80);
	}

}
