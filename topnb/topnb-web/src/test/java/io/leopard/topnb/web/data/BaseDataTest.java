package io.leopard.topnb.web.data;

import org.junit.Assert;
import org.junit.Test;

public class BaseDataTest {

	private final BaseData data = new BaseData();

	@Test
	public void add() {
		Assert.assertTrue(data.add("service.methodName", 1, 1));
		Assert.assertTrue(data.add("service.methodName", 1, 1));

		Assert.assertEquals(1, data.listAll().size());
		data.clear();
		Assert.assertEquals(0, data.listAll().size());

		for (int i = 0; i < 1000; i++) {
			Assert.assertTrue(data.add("service.methodName" + i, 1, 1));
		}
		System.out.println("size:" + data.DATA.size());
		Assert.assertFalse(data.add("service.methodName-x", 1, 1));
	}

}