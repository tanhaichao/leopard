package io.leopard.test.internal;

import org.junit.Assert;
import org.junit.Test;

public class ModuleParserIntegrationTest {

	private ModuleParser moduleParser = new ModuleParser();

	@Test
	public void setPath() {
		moduleParser.setPath("/work/news/news/news-dao");
		System.out.println("moduleParser:" + moduleParser.getModuleName());
		Assert.assertEquals("dao", moduleParser.getModuleName());
	}

}