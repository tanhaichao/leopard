package io.leopard.burrow.util;

import org.junit.Assert;
import org.junit.Test;

public class AgeUtilTest {

	@Test
	public void getAge() {
		Assert.assertEquals(0, AgeUtil.getAge("1970-01-01"));
		Assert.assertEquals(1, AgeUtil.getAge("2015-02-15"));
		Assert.assertEquals(1, AgeUtil.getAge("2015-02-16"));
		Assert.assertEquals(1, AgeUtil.getAge("2015-03-16"));
		Assert.assertEquals(2, AgeUtil.getAge("2015-01-16"));
		Assert.assertEquals(3, AgeUtil.getAge("2014-01-16"));
		Assert.assertEquals(3, AgeUtil.getAge("2014-02-14"));
		Assert.assertEquals(2, AgeUtil.getAge("2014-02-15"));
		Assert.assertEquals(3, AgeUtil.getAge("2013-02-15"));
	}

}