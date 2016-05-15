package io.leopard.data.env;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.core.io.Resource;

import io.leopard.commons.utility.AESUtil;

public class PropertyPlaceholderLeiImplTest {

	private PropertyPlaceholderLeiImpl impl = new PropertyPlaceholderLeiImpl();

	@Test
	public void getResources() throws IOException {
		Resource[] resources = impl.getResources("dev");
		InputStream input = resources[0].getInputStream();
		String content = IOUtils.toString(input);
		System.out.println("content:" + content);
	}

	@Test
	public void test() {
		String publickey = "12345678901234567890123456789012";
		String encode = AESUtil.encrypt("haha.key=value1", publickey);
		System.out.println("encode:" + encode);
		String decode = AESUtil.decrypt(encode, publickey);
		System.out.println("decode:" + decode);
	}

}
