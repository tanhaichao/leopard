package io.leopard.data.env;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class PropertyPlaceholderLeiImplTest {

	private PropertyPlaceholderLeiImpl impl = new PropertyPlaceholderLeiImpl();

	@Test
	public void getResources() throws IOException {
		Resource[] resources = impl.getResources("dev");
		InputStream input = resources[0].getInputStream();
		String content = IOUtils.toString(input);
		System.out.println("content:" + content);
	}

}
