package io.leopard.myjetty.workbench;

import java.io.IOException;

import org.junit.Test;

public class WebappControllerTest {

	@Test
	public void compile() throws IOException {
		new WebappController().compile("/work/olla/zhongcao", null);
	}

	@Test
	public void packaging() throws IOException {
		new WebappController().packaging("/work/olla/zhongcao", null);
	}

	@Test
	public void start() throws IOException {
		new WebappController().start("/work/olla/zhongcao/zhongcao-web");
	}

}