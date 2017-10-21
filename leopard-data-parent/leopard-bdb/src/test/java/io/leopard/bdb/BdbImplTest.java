package io.leopard.bdb;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.sleepycat.je.DatabaseException;

public class BdbImplTest {

	@Test
	public void add() throws DatabaseException {
		BdbImpl bdb = new BdbImpl();
		bdb.setDataDir(new File("/tmp/dbd/"));
		bdb.init();
		Assert.assertNull(bdb.getString("key"));
		bdb.add("key", "value");
		String value = bdb.getString("key");
		Assert.assertEquals(value, "value");
		System.out.println("value:" + value);
		bdb.delete("key");
		Assert.assertNull(bdb.getString("key"));
		bdb.destroy();
	}

}