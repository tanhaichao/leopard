package io.leopard.data4j.memdb;

import org.junit.Assert;
import org.junit.Test;

public class MemdbImplTest {

	@Test
	public void get() {
		MemdbImpl memdb = new MemdbImpl();
		memdb.set("key", "value");
		Assert.assertEquals("value", memdb.get("key"));
	}

	@Test
	public void get2() {
		MemdbImpl memdb = new MemdbImpl();
		memdb.set("key", SerializeImpl.getInstance().serialize("value"));
		Assert.assertEquals("value", memdb.get("key", String.class));
	}

	@Test
	public void remove() {
		MemdbImpl memdb = new MemdbImpl();
		memdb.set("key", "value");
		Assert.assertTrue(memdb.remove("key"));
		Assert.assertFalse(memdb.remove("key"));

		Assert.assertNull(memdb.get("key"));
	}

	@Test
	public void dbSize() {
		MemdbImpl memdb = new MemdbImpl();
		memdb.set("key", "value");
		Assert.assertEquals(1L, memdb.dbSize());
	}

	@Test
	public void set() {
		MemdbImpl memdb = new MemdbImpl();
		memdb.set("key", "value");
		Assert.assertEquals("value", memdb.get("key"));
	}

}