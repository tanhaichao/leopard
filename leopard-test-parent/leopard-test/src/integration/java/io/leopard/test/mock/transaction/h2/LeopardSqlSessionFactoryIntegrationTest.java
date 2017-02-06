package io.leopard.test.mock.transaction.h2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class LeopardSqlSessionFactoryIntegrationTest {

	@Test
	public void replaceResource() throws IOException {
		String content = "ok";
		Resource resource = new ByteArrayResource(content.getBytes());

		InputStream is = resource.getInputStream();
		String str = IOUtils.toString(is);
		System.err.println("str:" + str +" resource:"+resource.toString());
	}

}