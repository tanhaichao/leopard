package io.leopard.web.taglib.tags;

import org.junit.Assert;
import org.junit.Test;

public class FootTagTest {

	@Test
	public void getContent() {
		FootTag tag = new FootTag();
		Assert.assertNotNull(tag.getContent());
	}

}