package io.leopard.monitor.log;

import io.leopard.burrow.io.FileUtil;

import java.io.IOException;

import org.junit.Test;

public class Log4jServiceIntegrationTest {

	@Test
	public void getLevel() throws IOException {
		String content = FileUtil.readByClassPath("log4j.properties");
		String level = Log4jService.getLevel("E1", content);
		System.out.println("level:" + level);
	}

	@Test
	public void update() throws IOException {
		String content = FileUtil.readByClassPath("log4j.properties");

		String content2 = Log4jService.update("DEBUG", "DEBUG", "DEBUG", "DEBUG", content);
		System.out.println("content2:" + content2);
	}

}