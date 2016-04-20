package io.leopard.web.taglib.tags;

import org.junit.Assert;
import org.junit.Test;

public class UploadTagTest {

	@Test
	public void createRandomInputId() {
		Assert.assertNotNull(UploadTag.createRandomInputId());
	}

	@Test
	public void getContent() {
		UploadTag tag = new UploadTag();
		Assert.assertNotNull(tag.getContent());
		tag.setClassName("className");
		Assert.assertTrue(tag.getContent().indexOf("class=") != -1);
		tag.setCompress("100_100,200_200");

		Assert.assertTrue(tag.getContent().indexOf("weightAndHeight") != -1);
		tag.setCallback("callback2");
		Assert.assertTrue(tag.getContent().indexOf("callback2") != -1);
		tag.setType("type");
	}

}