package io.leopard.data.env;

import java.util.ServiceLoader;

import io.leopard.commons.utility.AESUtil;

public class PropertyDecoderImpl implements PropertyDecoder {

	public static String PUBLIC_KEY = "12345678901234567890123456789012";

	private PropertyDecoder decoder = null;

	public PropertyDecoderImpl() {
		for (PropertyDecoder decoder : ServiceLoader.load(PropertyDecoder.class)) {
			this.decoder = decoder;
		}
	}

	@Override
	public String decode(String encode) {
		if (decoder != null) {
			return decoder.decode(encode);
		}
		return AESUtil.decrypt(encode, PUBLIC_KEY);
	}

}
