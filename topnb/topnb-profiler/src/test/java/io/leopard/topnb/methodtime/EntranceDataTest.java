package io.leopard.topnb.methodtime;

import org.junit.Assert;
import org.junit.Test;

public class EntranceDataTest {

	private final EntranceData data = new EntranceData();

	@Test
	public void add() {
		data.add("methodName", 1, 1);

		// Assert.assertEquals(1, data.getData().listAll().size());
	}

	@Test
	public void getDataByEntryName() {
		Assert.assertNotNull(data.getDataByEntryName("entry1"));
		Assert.assertNotNull(data.getDataByEntryName("entry1"));
	}

}