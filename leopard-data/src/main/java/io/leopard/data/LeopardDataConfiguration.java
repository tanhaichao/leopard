package io.leopard.data;

import io.leopard.data.alldb.Alldb;
import io.leopard.data.alldb.AlldbImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeopardDataConfiguration {

	@Bean
	Alldb createAlldb() {
		Alldb alldb = new AlldbImpl();
		System.err.println("alldb:" + alldb);
		return null;
	}

}
