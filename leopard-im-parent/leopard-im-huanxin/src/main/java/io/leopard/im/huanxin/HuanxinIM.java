package io.leopard.im.huanxin;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;  

public class HuanxinIM {

	
	private void start() {
		Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);  

	}
}
