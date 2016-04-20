package io.leopard.web4j.view;

import org.junit.Assert;
import org.junit.Test;

public class JsonpUtilTest {

	@Test
	public void isValidCallback() {
		Assert.assertTrue(JsonpUtil.isValidCallback("callback"));
		Assert.assertTrue(JsonpUtil.isValidCallback("callback_1"));
		Assert.assertTrue(JsonpUtil.isValidCallback("Callback"));
		Assert.assertTrue(JsonpUtil.isValidCallback("callback.a"));
		Assert.assertFalse(JsonpUtil.isValidCallback("callback<a"));
	}

	@Test
	public void checkCallback() {
		JsonpUtil.checkCallback("callback");
		{
			try {
				JsonpUtil.checkCallback("");
				Assert.fail("怎么没有抛异常?");
			}
			catch (Exception e) {

			}
		}
		{
			try {
				JsonpUtil.checkCallback("callback<a");
				Assert.fail("怎么没有抛异常?");
			}
			catch (Exception e) {

			}
		}
	}

	@Test
	public void checkVar() {
		JsonpUtil.checkVar("var");
		{
			try {
				JsonpUtil.checkVar("");
				Assert.fail("怎么没有抛异常?");
			}
			catch (Exception e) {

			}
		}
		{
			try {
				JsonpUtil.checkVar("var<a");
				Assert.fail("怎么没有抛异常?");
			}
			catch (Exception e) {

			}
		}
	}

	@Test
	public void JsonpUtil() {
		new JsonpUtil();
	}
}