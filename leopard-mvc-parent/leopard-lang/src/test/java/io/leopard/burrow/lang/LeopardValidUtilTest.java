package io.leopard.burrow.lang;

import org.junit.Assert;
import org.junit.Test;

public class LeopardValidUtilTest {

	@Test
	public void isValidUsername() {
		Assert.assertTrue(LeopardValidUtil.isValidUsername("username"));
		Assert.assertTrue(LeopardValidUtil.isValidUsername("username-123"));
		Assert.assertFalse(LeopardValidUtil.isValidUsername("-username"));
		Assert.assertFalse(LeopardValidUtil.isValidUsername("(null)"));
	}

	@Test
	public void isValidPassport() {
		Assert.assertTrue(LeopardValidUtil.isValidPassport("username"));
	}

	@Test
	public void isValidUid() {
		Assert.assertTrue(LeopardValidUtil.isValidUid(1));
		Assert.assertFalse(LeopardValidUtil.isValidUid(0));
	}

	@Test
	public void isValidImid() {
		Assert.assertTrue(LeopardValidUtil.isValidImid(1));
		Assert.assertFalse(LeopardValidUtil.isValidImid(0));
	}

	@Test
	public void isValidGameId() {
		Assert.assertTrue(LeopardValidUtil.isValidGameId("Ddt"));
		Assert.assertTrue(LeopardValidUtil.isValidGameId("ddt"));
		Assert.assertTrue(LeopardValidUtil.isValidGameId("dd_t"));
		Assert.assertFalse(LeopardValidUtil.isValidGameId(""));
	}

	@Test
	public void isValidServerId() {
		Assert.assertTrue(LeopardValidUtil.isValidServerId("S1"));
		Assert.assertTrue(LeopardValidUtil.isValidServerId("s1"));
		Assert.assertFalse(LeopardValidUtil.isValidServerId(""));
	}

	@Test
	public void isValidIp() {
		Assert.assertTrue(LeopardValidUtil.isValidIp("127.0.0.1"));
		Assert.assertFalse(LeopardValidUtil.isValidIp(""));
		Assert.assertFalse(LeopardValidUtil.isValidIp("127.0"));
	}

	@Test
	public void isValidDateTime() {
		Assert.assertTrue(LeopardValidUtil.isValidDateTime("2013-01-01 00:00:00"));
		Assert.assertFalse(LeopardValidUtil.isValidDateTime("2013-01-01"));
	}

	@Test
	public void isNull() {
		Assert.assertTrue(LeopardValidUtil.isNull(null));
		Assert.assertFalse(LeopardValidUtil.isNull(""));
	}

	@Test
	public void isNotNull() {
		Assert.assertTrue(LeopardValidUtil.isNotNull(""));
		Assert.assertFalse(LeopardValidUtil.isNotNull(null));
	}

	@Test
	public void LeopardValidUtil() {
		new LeopardValidUtil();
	}

	@Test
	public void isNotValidUsername() {
		Assert.assertTrue(LeopardValidUtil.isNotValidUsername(""));
		Assert.assertFalse(LeopardValidUtil.isNotValidUsername("hctan"));
	}

}