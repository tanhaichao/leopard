package io.leopard.data.env;

import java.io.IOException;

import org.junit.Test;

public class PropertyDecoderImplTest {

	
	@Test
	public void encode() throws IOException {
		String decode = PropertyDecoderImpl.encode("app.properties");
		System.out.println(decode);
	}

}