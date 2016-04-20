package io.leopard.web.taglib.tags;

import org.junit.Assert;
import org.junit.Test;

public class HeadTagTest {

	@Test
	public void getContent() {
		HeadTag tag = new HeadTag();
		tag.setId("id");
		tag.setHref("href");
		tag.setUsername("username");
		tag.setTitle("title");
		tag.setRole("role");
		Assert.assertNotNull(tag.getContent());
	}

}