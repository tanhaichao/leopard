package io.leopard.data.env;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.leopard.commons.utility.AESUtil;

public class PropertyDecoderImpl implements PropertyDecoder {

	public static String PUBLIC_KEY = "12345678901234567890123456789012";

	// private PropertyDecoder decoder = null;
	//
	// public PropertyDecoderImpl() {
	// for (PropertyDecoder decoder : ServiceLoader.load(PropertyDecoder.class)) {
	// this.decoder = decoder;
	// }
	// }

	@Override
	public String decode(String encode) {
		// if (decoder != null) {
		// return decoder.decode(encode);
		// }
		return AESUtil.decrypt(encode, PUBLIC_KEY);
	}

	public static String encode(String path) throws IOException {
		Resource resource = new ClassPathResource(path);
		InputStream input = resource.getInputStream();
		String content = IOUtils.toString(input);
		// System.out.println("content:" + content);
		String decode = AESUtil.encrypt(content, PUBLIC_KEY);
		// System.out.println(decode);
		return decode;
	}

}
