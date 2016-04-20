package io.leopard.web;

import org.junit.Assert;
import org.junit.Test;

public class FieldNameUtilTest {

	@Test
	public void camelToUnderline() {
		Assert.assertEquals("user_id", FieldNameUtil.camelToUnderline("userId"));
		Assert.assertEquals("share_id", FieldNameUtil.camelToUnderline("shareId"));
	}

	@Test
	public void underlineToCamel() {
		Assert.assertEquals("userId", FieldNameUtil.underlineToCamel("user_id"));
		Assert.assertEquals("shareId", FieldNameUtil.underlineToCamel("share_id"));
	}

}