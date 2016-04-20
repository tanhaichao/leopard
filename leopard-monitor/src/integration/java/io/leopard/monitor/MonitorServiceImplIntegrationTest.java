package io.leopard.monitor;

import io.leopard.commons.utility.StringUtil;

import org.junit.Test;

public class MonitorServiceImplIntegrationTest {

	public static String urlEncode(String url) {
		String encode = StringUtil.urlEncode(url);
		encode = encode.replace("%3A", ":");
		encode = encode.replace("%2F", "/");
		encode = encode.replace("%3F", "?");
		encode = encode.replace("%3D", "=");
		encode = encode.replace("%26", "&");
		// System.out.println("encode:" + encode);
		return encode;
	}

	@Test
	public void checkThreadCount() {
		String url = "http://mp3-2f.tan.cc/混沌修真决/1.mp3?xxx=ddd&xxx";
		String encode = urlEncode(url);
		System.out.println("encode:" + encode);
	}

}