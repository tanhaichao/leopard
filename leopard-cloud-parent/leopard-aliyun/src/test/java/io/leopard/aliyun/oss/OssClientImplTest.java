package io.leopard.aliyun.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class OssClientImplTest {

	private OssClientImpl ossClient = new OssClientImpl();

	public OssClientImplTest() {

	}

	@Test
	public void add() throws IOException {
		File file = new File("/tmp/1.jpg");
		InputStream input = new FileInputStream(file);
		String url = ossClient.add(input, "item", file.getName(), file.length());
		System.out.println("url:" + url);
	}

}